package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.events.EventBus
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage
import gq.genprog.simpletweaker.tweaks.builtin.ChatTweak

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class SimpleTweaker {
    val tweaks: ArrayList<ITweak> = arrayListOf(
            ChatTweak()
    )
    val eventBus = EventBus()

    fun initTweaks() {
        tweaks.forEach {
            eventBus.register(it)
        }
    }

    fun runTweaks(stage: TweakStage) {
        tweaks.forEach {
            if (it.getTweakStage() == stage)
                it.runTweak()
        }
    }
}