package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.api.IServer
import gq.genprog.simpletweaker.hooks.MinecraftHooks
import gq.genprog.simpletweaker.nms.wrappers.ServerWrapper

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object Helper {
    fun getNmsServer(): Any? {
        return MinecraftHooks.mcServer
    }

    fun getMinecraftServer(): IServer {
        return ServerWrapper(this.getNmsServer()!!)
    }
}