package gq.genprog.simpletweaker.api

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface IPlayer: INMSObject, ICommandSender {
    fun getUniqueId(): UUID
    fun getUsername(): String

    val uid get() = getUniqueId()
    val name get() = getUsername()
}