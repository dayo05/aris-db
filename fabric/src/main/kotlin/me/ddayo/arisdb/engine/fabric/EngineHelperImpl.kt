package me.ddayo.arisdb.engine.fabric

import net.fabricmc.api.EnvType
import net.fabricmc.loader.api.FabricLoader

object EngineHelperImpl {
    @JvmStatic
    fun isDedicatedServer() = FabricLoader.getInstance().environmentType == EnvType.SERVER
}