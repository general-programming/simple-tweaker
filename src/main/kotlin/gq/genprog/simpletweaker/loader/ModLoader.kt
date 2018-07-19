package gq.genprog.simpletweaker.loader

import gq.genprog.simpletweaker.hooks.MinecraftHooks
import gq.genprog.simpletweaker.tweaks.ITweak
import java.io.File

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class ModLoader {
    fun discover(folder: File) {
        if (!folder.exists()) return
        if (!folder.isDirectory) return

        println("Searching ${folder.absolutePath} for tweaks...")
        val allTweaks: ArrayList<LoadedTweak<ITweak>> = arrayListOf()

        folder.listFiles { it ->
            it.extension == "jar"
        }.forEach {
            val wrapper = ModWrapper(it)
            val manifest = wrapper.getModManifest()

            println("Loading tweak holder ${manifest.id} (${manifest.name})")

            allTweaks.addAll(wrapper.loadTweaks())
        }

        println("Loaded ${allTweaks.size} tweaks.")

        MinecraftHooks.tweaker.injectTweaks(allTweaks)
    }
}