package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.hooks.MinecraftHooks

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object Helper {
    fun getMinecraftServer(): Any? {
        return MinecraftHooks.mcServer
    }
}