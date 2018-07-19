package gq.genprog.simpletweaker.config

import java.io.File

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface IConfigLoader {
    fun <T: Any> loadConfig(file: File, typeClass: Class<T>): T
    fun <T: Any> writeConfig(file: File, instance: T)
}