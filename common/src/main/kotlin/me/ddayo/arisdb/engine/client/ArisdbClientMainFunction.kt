package me.ddayo.arisdb.engine.client

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider

@LuaProvider(ArisdbClientMainFunction.PROVIDER)
object ArisdbClientMainFunction {
    const val PROVIDER = "ArisdbClientMainProviderGenerated"
    @LuaFunction
    fun dummy() {

    }
}