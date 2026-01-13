package me.ddayo.arisdb.engine.client.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.client.ClientMainEngine
import me.ddayo.arisdb.lua.glue.ArisdbClientMainProviderGenerated

class ArisdbFabricClientMainFunctionExtension : EngineInitializer<ClientMainEngine> {
    override fun initLua(engine: ClientMainEngine) {
        ArisdbClientMainProviderGenerated.initEngine(engine)
    }
}