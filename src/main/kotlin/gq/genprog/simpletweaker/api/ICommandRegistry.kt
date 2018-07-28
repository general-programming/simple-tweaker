package gq.genprog.simpletweaker.api

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ICommandRegistry: INMSObject {
    fun dispatch(handler: ICommandHandler, command: String)
}