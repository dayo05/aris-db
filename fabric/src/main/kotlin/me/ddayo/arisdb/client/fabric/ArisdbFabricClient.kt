package me.ddayo.arisdb.client.fabric

import me.ddayo.arisdb.client.ArisdbClient
import net.fabricmc.api.ClientModInitializer

class ArisdbFabricClient: ClientModInitializer {
    override fun onInitializeClient() {
        ArisdbClient.init()
    }
}