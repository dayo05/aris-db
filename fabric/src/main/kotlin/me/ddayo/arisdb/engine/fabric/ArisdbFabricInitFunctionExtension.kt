package me.ddayo.arisdb.engine.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.InitEngine
import me.ddayo.arisdb.lua.glue.ArisdbInitProviderGenerated

class ArisdbFabricInitFunctionExtension : EngineInitializer<InitEngine> {
    override fun initLua(engine: InitEngine) {
        ArisdbInitProviderGenerated.initEngine(engine)
    }
}