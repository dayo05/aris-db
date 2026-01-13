package me.ddayo.arisdb.engine

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider

@LuaProvider(ArisdbInitFunction.PROVIDER)
object ArisdbInitFunction {
    const val PROVIDER = "ArisdbInitProviderGenerated"
    @LuaFunction
    fun dummy() {

    }
}