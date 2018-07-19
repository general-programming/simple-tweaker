package gq.genprog.simpletweaker.config

import com.google.gson.GsonBuilder
import java.io.File
import kotlin.text.Charsets.UTF_8

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class JsonConfigLoader: IConfigLoader {
    private val gson = GsonBuilder().apply {
        setPrettyPrinting()
    }.create()

    override fun <T: Any> loadConfig(file: File, typeClass: Class<T>): T {
        return gson.fromJson<T>(file.reader(UTF_8), typeClass) as T
    }

    override fun <T: Any> writeConfig(file: File, instance: T) {
        val content = gson.toJson(instance)

        file.writeText(content, UTF_8)
    }
}