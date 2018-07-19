package gq.genprog.simpletweaker.loader

import com.google.gson.Gson
import gq.genprog.simpletweaker.tweaks.ITweak
import java.io.File
import java.net.URLClassLoader

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class ModWrapper(val file: File) {
    val gson = Gson()
    val classLoader = URLClassLoader(arrayOf(file.toURI().toURL()), javaClass.classLoader)

    fun loadModClass(name: String): Class<*> {
        return classLoader.loadClass(name)
    }

    fun getModManifest(): ModManifest {
        val manifestFile = classLoader.getResource("tweak.json")

        return gson.fromJson(manifestFile.readText(), ModManifest::class.java)
    }

    fun loadTweaks(): List<LoadedTweak<ITweak>> {
        val manifest = this.getModManifest()
        val tweaks = manifest.tweaks

        return tweaks.mapNotNull { tweakClassName ->
            try {
                val inst = this.loadModClass(tweakClassName).newInstance() as ITweak
                LoadedTweak(inst, manifest)
            } catch (err: ReflectiveOperationException) {
                println("Mod ${manifest.id} threw an exception while instansiating tweak class $tweakClassName!")
                err.printStackTrace()
                null
            }
        }
    }
}