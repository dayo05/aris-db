package me.ddayo.arisdb.engine.client.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.client.ClientInitEngine
import me.ddayo.arisdb.lua.glue.ArisdbClientInitProviderGenerated

class ArisdbFabricClientInitFunctionExtension : EngineInitializer<ClientInitEngine> {
    override fun initLua(engine: ClientInitEngine) {
        ArisdbClientInitProviderGenerated.initEngine(engine)
    }
}