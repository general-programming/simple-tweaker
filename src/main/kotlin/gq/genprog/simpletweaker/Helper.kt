package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.api.IServer
import gq.genprog.simpletweaker.hooks.MinecraftHooks

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object Helper {
    fun getNmsServer(): Any? {
        return MinecraftHooks.mcServer
    }

    fun getMinecraftServer(): IServer {
        return MinecraftHooks.wrappedServer
    }
}