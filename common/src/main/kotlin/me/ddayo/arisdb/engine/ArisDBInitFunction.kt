package me.ddayo.arisdb.engine

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import com.mongodb.client.MongoDatabase
import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider
import redis.clients.jedis.RedisClient

object ArisDBInitFunction {
    const val PROVIDER = "ArisdbInitProviderGenerated"
}

@LuaProvider(ArisDBInitFunction.PROVIDER, library = "aris.init.redis")
object ArisDBRedisInitFunction {
    var jedis: RedisClient? = null
        private set

    private var config: RedisConfig? = null

    private data class RedisConfig(
        val server: String,
        val port: Int,
        val user: String,
        val password: String
    )

    @LuaFunction("redis_init")
    fun redisInit(server: String, port: Int, onlyDedicatedServer: Boolean) {
        redisInitWithAuth(server, port, "", "", onlyDedicatedServer)
    }

    @LuaFunction("redis_init_auth")
    fun redisInitWithAuth(server: String, port: Int, user: String, password: String, onlyDedicatedServer: Boolean) {
        if(onlyDedicatedServer && !EngineHelper.isDedicatedServer()) return
        ArisDBRedisInGameFunction.stopSubscribers()
        jedis?.close()
        config = RedisConfig(server, port, user, password)
        jedis = createClient()
    }

    fun createClient(): RedisClient {
        val c = config ?: throw IllegalStateException("Redis is not initialized")
        return if (c.password.isEmpty()) {
            RedisClient.create(c.server, c.port)
        } else {
            RedisClient.create(c.server, c.port, c.user.ifEmpty { null }, c.password)
        }
    }
}

@LuaProvider(ArisDBInitFunction.PROVIDER, library = "aris.init.mongo")
object ArisDBMongoInitFunction {
    var client: MongoClient? = null
    var database: MongoDatabase? = null

    @LuaFunction("mongo_init")
    fun mongoInit(connectionString: String, databaseName: String, onlyDedicatedServer: Boolean) {
        if(!onlyDedicatedServer || EngineHelper.isDedicatedServer()) {
            val c = MongoClients.create(connectionString)
            client = c
            database = c.getDatabase(databaseName)
        }
    }
}
