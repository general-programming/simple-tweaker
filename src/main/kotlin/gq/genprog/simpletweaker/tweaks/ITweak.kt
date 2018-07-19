package gq.genprog.simpletweaker.tweaks

import gq.genprog.simpletweaker.events.TweakRunEvent

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ITweak {
    fun getTweakStage(): TweakStage
    fun runTweak(ev: TweakRunEvent)
}