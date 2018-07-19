package gq.genprog.simpletweaker.tweaks

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ITweak {
    fun getTweakStage(): TweakStage
    fun runTweak()
}