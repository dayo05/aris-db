package me.ddayo.arisdb.fabric

import me.ddayo.arisdb.Arisdb
import net.fabricmc.api.ModInitializer

class ArisdbFabric: ModInitializer {
    override fun onInitialize() {
        Arisdb.init()
    }
}