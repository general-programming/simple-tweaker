package gq.genprog.simpletweaker.tweaks.builtin

import gq.genprog.simpletweaker.SimpleTweaker
import gq.genprog.simpletweaker.api.ICommand
import gq.genprog.simpletweaker.api.ICommandSender
import gq.genprog.simpletweaker.events.TweakRunEvent
import gq.genprog.simpletweaker.tweaks.ITweak
import gq.genprog.simpletweaker.tweaks.TweakStage

/**
 * Internal tweak used by SimpleTweaker
 *
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class InternalTweak(val tweaker: SimpleTweaker) : ITweak {
    override fun getTweakStage() = TweakStage.POST_WORLD

    override fun runTweak(ev: TweakRunEvent) {
        ev.registerCommand(LoadedTweaksCmd(tweaker))
    }

    class LoadedTweaksCmd(val tweaker: SimpleTweaker) : ICommand {
        override fun getAliases() = arrayOf("tweaks")
        override fun getDescription() = "List loaded tweak holders."

        override fun execute(sender: ICommandSender, array: Array<String>) {
            val text = tweaker.tweaks.joinToString {
                "\u00A7a${it.name} (${it.id})"
            }

            sender.sendMessage("\u00A77Tweaks: $text\u00A7r")
        }
    }
}