package gq.genprog.simpletweaker.api

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ICommandHandler: INMSObject {
    fun getCommandSender(): ICommandSender
}