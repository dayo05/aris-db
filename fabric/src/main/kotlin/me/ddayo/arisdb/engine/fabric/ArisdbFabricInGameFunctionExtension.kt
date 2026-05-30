package me.ddayo.arisdb.engine.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.InGameEngine
import me.ddayo.arisdb.engine.ArisDBRedisInGameFunction
import me.ddayo.arisdb.lua.glue.ArisdbInGameProviderGenerated

class ArisdbFabricInGameFunctionExtension : EngineInitializer<InGameEngine> {
    override fun initLua(engine: InGameEngine) {
        ArisDBRedisInGameFunction.stopSubscribers()
        ArisdbInGameProviderGenerated.initEngine(engine)
        engine.createTask("aris.game.redis.drain_subscriptions(256)", "arisdb_redis_subscription_drain", true)
    }
}
