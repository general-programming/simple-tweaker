package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.events.EventBus
import gq.genprog.simpletweaker.events.TweakRunEvent
import gq.genprog.simpletweaker.loader.LoadedTweak
import gq.genprog.simpletweaker.loader.ModManifest
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage
import gq.genprog.simpletweaker.tweaks.builtin.InternalTweak
import java.io.File

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class SimpleTweaker {
    var tweaks: ArrayList<LoadedTweak<ITweak>> = arrayListOf()
    val eventBus = EventBus()

    init {
        // inject our own tweaks
        val manifest = ModManifest("gq.genprog.simpletweaker", "SimpleTweaker", arrayOf())

        tweaks.add(LoadedTweak(InternalTweak(this), manifest))
    }

    fun injectTweaks(all: List<LoadedTweak<ITweak>>) {
        tweaks.addAll(all)
    }

    fun initTweaks() {
        tweaks.forEach {
            eventBus.register(it.instance)
        }
    }

    fun runTweaks(stage: TweakStage) {
        val ev = TweakRunEvent(File("."))

        tweaks.forEach {
            if (it.instance.getTweakStage() == stage) {
                try {
                    it.instance.runTweak(ev)
                } catch (ex: Throwable) {
                    println("Mod ${it.id} threw an exception while running tweak ${it.instance} (tweak stage $stage)")
                    ex.printStackTrace()
                }
            }
        }
    }
}