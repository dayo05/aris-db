package me.ddayo.arisdb.fabric

import me.ddayo.arisdb.ArisDB
import net.fabricmc.api.ModInitializer

class ArisdbFabric: ModInitializer {
    override fun onInitialize() {
        ArisDB.init()
    }
}