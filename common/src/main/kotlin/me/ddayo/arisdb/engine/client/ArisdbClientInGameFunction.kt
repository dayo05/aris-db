package me.ddayo.arisdb.engine.client

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider

@LuaProvider(ArisdbClientInGameFunction.PROVIDER)
object ArisdbClientInGameFunction {
    const val PROVIDER = "ArisdbClientInGameProviderGenerated"
    @LuaFunction
    fun dummy() {

    }
}