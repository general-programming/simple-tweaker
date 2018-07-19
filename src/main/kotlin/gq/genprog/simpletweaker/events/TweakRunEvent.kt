package gq.genprog.simpletweaker.events

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
}