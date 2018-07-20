package gq.genprog.simpletweaker.api

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ICommand {
    fun getAliases(): Array<String>
    fun getDescription(): String
    fun execute(sender: ICommandSender, array: Array<String>)
}