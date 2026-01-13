package me.ddayo.arisdb.forge

import me.ddayo.aris.forge.ArisForge
import me.ddayo.arisdb.Arisdb
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent
import thedarkcolour.kotlinforforge.forge.MOD_BUS
import me.ddayo.arisdb.lua.glue.ArisdbInitProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbInGameProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbClientInitProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbClientMainProviderGenerated
import me.ddayo.arisdb.lua.glue.ArisdbClientInGameProviderGenerated

@Mod(Arisdb.MOD_ID)
class ArisdbForge {
    init {
        MOD_BUS.addListener { it: FMLConstructModEvent ->
            ArisForge.initExtensions.add {
                ArisdbInitProviderGenerated.initEngine(it)
            }
        }
        MOD_BUS.addListener { it: FMLConstructModEvent ->
            ArisForge.inGameExtensions.add {
                ArisdbInGameProviderGenerated.initEngine(it)
            }
        }
        MOD_BUS.addListener { it: FMLConstructModEvent ->
            ArisForge.clientInitExtensions.add {
                ArisdbClientInitProviderGenerated.initEngine(it)
            }
        }
        MOD_BUS.addListener { it: FMLConstructModEvent ->
            ArisForge.clientMainExtensions.add {
                ArisdbClientMainProviderGenerated.initEngine(it)
            }
        }
        MOD_BUS.addListener { it: FMLConstructModEvent ->
            ArisForge.clientInGameExtensions.add {
                ArisdbClientInGameProviderGenerated.initEngine(it)
            }
        }
        Arisdb.init()
    }
}