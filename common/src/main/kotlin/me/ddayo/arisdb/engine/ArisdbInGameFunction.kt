package me.ddayo.arisdb.engine

import me.ddayo.aris.luagen.LuaFunc
import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider
import redis.clients.jedis.JedisPubSub

@LuaProvider(ArisdbInGameFunction.PROVIDER)
object ArisdbInGameFunction {
    const val PROVIDER = "ArisdbInGameProviderGenerated"
}

@LuaProvider(ArisdbInGameFunction.PROVIDER, library = "aris.game.redis")
object ArisDBRedisInGameFunction {
    val redis get() = ArisDBRedisInitFunction.jedis!!
    @LuaFunction
    fun set(key: String, value: String) = redis.set(key, value)

    @LuaFunction
    fun get(key: String) = redis.get(key)

    @LuaFunction
    fun lpush(key: String, value: String) = redis.lpush(key, value)
    @LuaFunction
    fun rpush(key: String, value: String) = redis.rpush(key, value)
    @LuaFunction
    fun lpop(key: String) = redis.lpop(key)
    @LuaFunction
    fun rpop(key: String) = redis.rpop(key)

    @LuaFunction
    fun sadd(key: String, value: String) = redis.sadd(key, value)
    @LuaFunction
    fun smembers(key: String) = redis.smembers(key)

    /**
     * @param func (channel, message) -> void
     */
    @LuaFunction
    fun subscribe(key: String, func: LuaFunc) {
        redis.subscribe(object: JedisPubSub() {
            override fun onMessage(channel: String?, message: String?) {
                super.onMessage(channel, message)
                func.callAsTask(channel, message)
            }
        }, key)
    }

    @LuaFunction
    fun publish(key: String, value: String) = redis.publish(key, value)
}