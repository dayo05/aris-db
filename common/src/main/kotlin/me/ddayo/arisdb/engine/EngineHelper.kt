package me.ddayo.arisdb.engine

import dev.architectury.injectables.annotations.ExpectPlatform

object EngineHelper {
    @ExpectPlatform
    @JvmStatic
    fun isDedicatedServer(): Boolean {
        throw NotImplementedError()
    }
}