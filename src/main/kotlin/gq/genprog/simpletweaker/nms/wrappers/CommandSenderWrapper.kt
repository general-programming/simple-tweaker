package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.ICommandSender
import gq.genprog.simpletweaker.nms.ClassDemystifier

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
open class CommandSenderWrapper(private val sender: Any): ICommandSender {
    override fun sendMessage(text: String) {
        val sendMethod = sender.javaClass.getDeclaredMethod("a", ClassDemystifier.getITextComponentClass())

        sendMethod.invoke(sender, ClassDemystifier.newTextComponentString(text))
    }

    override fun asNMS() = sender
}