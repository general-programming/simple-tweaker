package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.ICommandHandler
import gq.genprog.simpletweaker.api.ICommandRegistry
import gq.genprog.simpletweaker.nms.ClassDemystifier

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class CommandRegistryWrapper(val cmdRegistry: Any): ICommandRegistry {
    override fun dispatch(handler: ICommandHandler, command: String) {
        val dispatchMeth = ClassDemystifier.getCommandRegistryClass().getDeclaredMethod("a",
                ClassDemystifier.getCommandHandlerClass(), String::class.java)

        dispatchMeth.invoke(cmdRegistry, handler.asNMS(), command)
    }

    override fun asNMS() = cmdRegistry
}