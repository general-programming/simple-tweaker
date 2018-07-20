package gq.genprog.simpletweaker.events

import gq.genprog.simpletweaker.Helper
import gq.genprog.simpletweaker.api.ICommand
import java.io.File

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class TweakRunEvent(val serverDir: File) {
    val configDir get() = serverDir.resolve("config/").also {
        if (!it.exists())
            it.mkdir()
    }

    fun registerCommand(command: ICommand) {
        Helper.getMinecraftServer().registerCommand(command)
    }
}