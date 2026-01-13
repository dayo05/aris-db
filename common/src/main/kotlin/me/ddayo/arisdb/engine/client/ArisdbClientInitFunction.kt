package me.ddayo.arisdb.engine.client

import me.ddayo.aris.luagen.LuaFunction
import me.ddayo.aris.luagen.LuaProvider

@LuaProvider(ArisdbClientInitFunction.PROVIDER)
object ArisdbClientInitFunction {
    const val PROVIDER = "ArisdbClientInitProviderGenerated"
    @LuaFunction
    fun dummy() {

    }
}