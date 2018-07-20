package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.ICommandHandler
import gq.genprog.simpletweaker.api.ICommandSender
import gq.genprog.simpletweaker.nms.ClassDemystifier

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class CommandHandlerWrapper(private val cmdHandler: Any): ICommandHandler {
    override fun getCommandSender(): ICommandSender {
        val field = ClassDemystifier.getCommandHandlerClass().getDeclaredField("c").apply { isAccessible = true }
        val nmsCmdSender = field.get(cmdHandler) as Any
        val nmsCmdSenderClass = nmsCmdSender.javaClass

        if (ClassDemystifier.getPlayerClass().isAssignableFrom(nmsCmdSenderClass)) {
            return PlayerWrapper(nmsCmdSender)
        }

        return CommandSenderWrapper(nmsCmdSender)
    }

    override fun asNMS() = cmdHandler
}