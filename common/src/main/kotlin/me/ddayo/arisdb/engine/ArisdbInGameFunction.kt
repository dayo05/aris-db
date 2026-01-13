package me.ddayo.arisdb.engine

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider

@LuaProvider(ArisdbInGameFunction.PROVIDER)
object ArisdbInGameFunction {
    const val PROVIDER = "ArisdbInGameProviderGenerated"
    @LuaFunction
    fun dummy() {

    }
}