package me.ddayo.arisdb.engine

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider
import redis.clients.jedis.RedisClient

object ArisDBInitFunction {
    const val PROVIDER = "ArisdbInitProviderGenerated"
}

@LuaProvider(ArisDBInitFunction.PROVIDER, library = "aris.init.redis")
object ArisDBRedisInitFunction {
    var jedis: RedisClient? = null

    @LuaFunction("redis_init")
    fun redisInit(server: String, port: Int, onlyDedicatedServer: Boolean) {
        if(!onlyDedicatedServer || EngineHelper.isDedicatedServer())
            jedis = RedisClient.create(server, port)
    }
}