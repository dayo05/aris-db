package me.ddayo.arisdb.neoforge

import me.ddayo.aris.neoforge.ArisNeoForge
import me.ddayo.arisdb.ArisDB
import me.ddayo.arisdb.lua.glue.ArisdbClientInGameProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbClientInitProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbClientMainProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbInGameProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbInitProviderGenerated
import net.neoforged.bus.api.IEventBus
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLConstructModEvent

@Mod(ArisDB.MOD_ID)
class ArisdbNeoForge(modBus: IEventBus) {
    init {
        modBus.addListener { it: FMLConstructModEvent ->
            ArisNeoForge.initExtensions.add {
                ArisdbInitProviderGenerated.initEngine(it)
            }
            ArisNeoForge.inGameExtensions.add {
                ArisdbInGameProviderGenerated.initEngine(it)
            }
            ArisNeoForge.clientInitExtensions.add {
                ArisdbClientInitProviderGenerated.initEngine(it)
            }
            ArisNeoForge.clientMainExtensions.add {
                ArisdbClientMainProviderGenerated.initEngine(it)
            }
            ArisNeoForge.clientInGameExtensions.add {
                ArisdbClientInGameProviderGenerated.initEngine(it)
            }
        }
        ArisDB.init()
    }
}
