package me.ddayo.arisdb.engine

import com.mongodb.client.MongoCollection
import me.ddayo.aris.luagen.ILuaStaticDecl
import me.ddayo.aris.luagen.LuaFunc
import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider
import me.ddayo.arisdb.lua.glue.ArisdbInGameProviderGenerated
import org.bson.Document
import redis.clients.jedis.JedisPubSub
import java.util.concurrent.ConcurrentLinkedQueue
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicBoolean

@LuaProvider(ArisdbInGameFunction.PROVIDER)
object ArisdbInGameFunction {
    const val PROVIDER = "ArisdbInGameProviderGenerated"
}

@LuaProvider(ArisdbInGameFunction.PROVIDER, library = "aris.game.redis")
object ArisDBRedisInGameFunction {
    val redis get() = ArisDBRedisInitFunction.jedis!!

    private data class PendingMessage(
        val func: LuaFunc,
        val channel: String?,
        val message: String?
    )

    private class RedisSubscription(
        val channel: String,
        val pubSub: JedisPubSub,
        val active: AtomicBoolean,
        val thread: Thread
    )

    private val pendingMessages = ConcurrentLinkedQueue<PendingMessage>()
    private val subscriptions = CopyOnWriteArrayList<RedisSubscription>()

    @LuaFunction
    fun set(key: String, value: String) = redis.set(key, value)

    @LuaFunction
    fun get(key: String): String? = redis.get(key)

    @LuaFunction
    fun lpush(key: String, value: String) = redis.lpush(key, value)
    @LuaFunction
    fun rpush(key: String, value: String) = redis.rpush(key, value)
    @LuaFunction
    fun lpop(key: String): String? = redis.lpop(key)
    @LuaFunction
    fun rpop(key: String): String? = redis.rpop(key)

    @LuaFunction
    fun sadd(key: String, value: String) = redis.sadd(key, value)
    @LuaFunction
    fun smembers(key: String) = LuaStringSet(redis.smembers(key))

    /**
     * @param func (channel, message) -> void
     */
    @LuaFunction
    fun subscribe(key: String, func: LuaFunc) {
        val active = AtomicBoolean(true)
        val pubSub = object: JedisPubSub() {
            override fun onMessage(channel: String?, message: String?) {
                super.onMessage(channel, message)
                if (active.get()) {
                    pendingMessages.add(PendingMessage(func, channel, message))
                }
            }
        }
        val thread = Thread {
            ArisDBRedisInitFunction.createClient().use { subscriber ->
                try {
                    subscriber.subscribe(pubSub, key)
                } catch (e: Exception) {
                    if (active.get()) {
                        e.printStackTrace()
                    }
                } finally {
                    active.set(false)
                }
            }
        }
        val subscription = RedisSubscription(key, pubSub, active, thread)
        subscriptions.add(subscription)
        thread.name = "arisdb-redis-subscribe-$key"
        thread.isDaemon = true
        thread.start()
    }

    @LuaFunction
    fun publish(key: String, value: String) = redis.publish(key, value)

    @LuaFunction("drain_subscriptions")
    fun drainSubscriptions(maxMessages: Int): Int {
        val limit = maxMessages.coerceAtLeast(0)
        var drained = 0
        while (drained < limit) {
            val message = pendingMessages.poll() ?: break
            message.func.callAsTask(message.channel, message.message)
            drained++
        }
        return drained
    }

    fun stopSubscribers() {
        subscriptions.forEach {
            it.active.set(false)
            try {
                it.pubSub.unsubscribe()
            } catch (_: Exception) {
            }
        }
        subscriptions.clear()
        pendingMessages.clear()
    }
}

@LuaProvider(ArisdbInGameFunction.PROVIDER, library = "aris.game.mongo")
object ArisDBMongoInGameFunction {
    private val database get() = ArisDBMongoInitFunction.database!!

    @LuaFunction
    fun collection(name: String) = LuaMongoCollection(database.getCollection(name))
}

@LuaProvider(ArisdbInGameFunction.PROVIDER)
class LuaStringSet(val inner: Set<String>) : ILuaStaticDecl by ArisdbInGameProviderGenerated.LuaStringSet_LuaGenerated {
    @LuaFunction
    fun size() = inner.size

    @LuaFunction
    fun contains(value: String) = inner.contains(value)

    @LuaFunction
    fun get(index: Int): String? = inner.elementAtOrNull(index - 1)

    @LuaFunction("to_table")
    fun toTable(): Map<Int, String> = inner.withIndex().associate { (i, v) -> (i + 1) to v }
}

@LuaProvider(ArisdbInGameFunction.PROVIDER)
class LuaMongoCollection(val inner: MongoCollection<Document>) : ILuaStaticDecl by ArisdbInGameProviderGenerated.LuaMongoCollection_LuaGenerated {
    @LuaFunction
    fun insert(documentJson: String) {
        inner.insertOne(Document.parse(documentJson))
    }

    @LuaFunction("find_one")
    fun findOne(queryJson: String): String? = inner.find(Document.parse(queryJson)).first()?.toJson()

    @LuaFunction
    fun find(queryJson: String) = LuaDocumentList(inner.find(Document.parse(queryJson)).into(mutableListOf()))

    @LuaFunction
    fun update(queryJson: String, updateJson: String): Long =
        inner.updateMany(Document.parse(queryJson), Document.parse(updateJson)).modifiedCount

    @LuaFunction
    fun delete(queryJson: String): Long =
        inner.deleteMany(Document.parse(queryJson)).deletedCount

    @LuaFunction
    fun count(queryJson: String): Long =
        inner.countDocuments(Document.parse(queryJson))

    @LuaFunction
    fun drop() {
        inner.drop()
    }
}

@LuaProvider(ArisdbInGameFunction.PROVIDER)
class LuaDocumentList(val inner: List<Document>) : ILuaStaticDecl by ArisdbInGameProviderGenerated.LuaDocumentList_LuaGenerated {
    @LuaFunction
    fun size() = inner.size

    @LuaFunction
    fun get(index: Int): String? = inner.getOrNull(index - 1)?.toJson()

    @LuaFunction("to_table")
    fun toTable(): Map<Int, String> = inner.withIndex().associate { (i, doc) -> (i + 1) to doc.toJson() }
}
