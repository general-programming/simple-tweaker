package gq.genprog.simpletweaker.tweaks.builtin

import gq.genprog.simpletweaker.SimpleTweaker
import gq.genprog.simpletweaker.api.ICommand
import gq.genprog.simpletweaker.api.ICommandSender
import gq.genprog.simpletweaker.events.EventHandler
import gq.genprog.simpletweaker.events.PlayerChatEvent
import gq.genprog.simpletweaker.events.TweakRunEvent
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class TestTweak(val tweaker: SimpleTweaker) : ITweak {
    override fun getTweakStage() = TweakStage.POST_WORLD

    override fun runTweak(ev: TweakRunEvent) {
        println("TestTweak registered")

        ev.registerCommand(LoadedTweaksCmd(tweaker))
    }

    @EventHandler fun onPlayerChat(event: PlayerChatEvent) {
        println("${event.player.getUsername()} wrote: ${event.message}")
    }

    class LoadedTweaksCmd(val tweaker: SimpleTweaker) : ICommand {
        override fun getAliases() = arrayOf("tweaks")
        override fun getDescription() = "List loaded tweak holders."

        override fun execute(sender: ICommandSender, array: Array<String>) {
            val text = tweaker.tweaks.joinToString {
                "\u00A7a${it.name} (${it.id})"
            }

            sender.sendMessage("\u00A77Plugins: $text\u00A7r")
        }
    }
}