package me.ddayo.arisdb.engine.neoforge

import net.neoforged.api.distmarker.Dist
import net.neoforged.fml.loading.FMLEnvironment

object EngineHelperImpl {
    @JvmStatic
    fun isDedicatedServer() = FMLEnvironment.dist == Dist.DEDICATED_SERVER
}
