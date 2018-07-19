package gq.genprog.simpletweaker.tweaks.builtin

import gq.genprog.simpletweaker.events.EventHandler
import gq.genprog.simpletweaker.events.PlayerChatEvent
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class ChatTweak: ITweak {
    override fun getTweakStage() = TweakStage.POST_WORLD

    override fun runTweak() {
        println("ChatTweak registered")
    }

    @EventHandler fun onPlayerChat(event: PlayerChatEvent) {
        println("A player wrote: ${event.getMessage()}")
    }
}