package gq.genprog.simpletweaker.api

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ICommandSender: INMSObject {
    fun sendMessage(text: String)
}