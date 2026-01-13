package me.ddayo.arisdb.engine.fabric

import me.ddayo.aris.engine.EngineInitializer
import me.ddayo.aris.engine.InGameEngine
import me.ddayo.arisdb.lua.glue.ArisdbInGameProviderGenerated

class ArisdbFabricInGameFunctionExtension : EngineInitializer<InGameEngine> {
    override fun initLua(engine: InGameEngine) {
        ArisdbInGameProviderGenerated.initEngine(engine)
    }
}