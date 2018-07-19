package gq.genprog.simpletweaker.api

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface IPlayer: INMSObject {
    fun getUniqueId(): UUID
    fun getUsername(): String
    fun sendMessage(text: String)

    val uid get() = getUniqueId()
    val name get() = getUsername()
}