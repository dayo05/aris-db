package me.ddayo.arisdb.engine.client.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.client.ClientInGameEngine
import me.ddayo.arisdb.lua.glue.ArisdbClientInGameProviderGenerated

class ArisdbFabricClientInGameFunctionExtension : EngineInitializer<ClientInGameEngine> {
    override fun initLua(engine: ClientInGameEngine) {
        ArisdbClientInGameProviderGenerated.initEngine(engine)
    }
}