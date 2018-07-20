package gq.genprog.simpletweaker.api

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface IServer: INMSObject {
    fun getPlayer(username: String): IPlayer?
    fun getPlayer(uuid: UUID): IPlayer?
    fun broadcast(component: ITextComponent, isSystem: Boolean)
    fun broadcast(text: String)
    fun registerCommand(command: ICommand)
}