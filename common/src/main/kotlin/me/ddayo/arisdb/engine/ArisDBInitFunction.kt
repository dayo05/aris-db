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

    @LuaFunction("redis_init")
    fun redisInit(server: String, port: Int, onlyDedicatedServer: Boolean) {
        redisInitWithAuth(server, port, "", "", onlyDedicatedServer)
    }

    @LuaFunction("redis_init_auth")
    fun redisInitWithAuth(server: String, port: Int, user: String, password: String, onlyDedicatedServer: Boolean) {
        if(onlyDedicatedServer && !EngineHelper.isDedicatedServer()) return
        jedis = if (password.isEmpty()) {
            RedisClient.create(server, port)
        } else {
            RedisClient.create(server, port, user.ifEmpty { null }, password)
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