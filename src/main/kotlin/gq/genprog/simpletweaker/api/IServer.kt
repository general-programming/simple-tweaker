package gq.genprog.simpletweaker.api

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface IServer: INMSObject {
    /**
     * Get a player instance by their username.
     * @return Player if found, null otherwise.
     */
    fun getPlayer(username: String): IPlayer?

    /**
     * Get a player instance by their unique ID.
     * @return Player if found, null otherwise
     */
    fun getPlayer(uuid: UUID): IPlayer?

    /**
     * Get a list of currently logged-in players.
     */
    fun getOnlinePlayers(): List<IPlayer>

    /**
     * Broadcast a text component to all players on the server.
     */
    fun broadcast(component: ITextComponent, isSystem: Boolean)

    /**
     * Broadcast a string to all players on the server.
     */
    fun broadcast(text: String)

    /**
     * Register a custom command with this server instance.
     */
    fun registerCommand(command: ICommand)

    fun getCommandHandler(): ICommandHandler
    fun getCommandRegistry(): ICommandRegistry

    /**
     * Dispatch a command string as the server.
     */
    fun dispatch(command: String)
}