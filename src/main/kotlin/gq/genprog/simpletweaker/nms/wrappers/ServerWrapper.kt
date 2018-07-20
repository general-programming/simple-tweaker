package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.ICommand
import gq.genprog.simpletweaker.api.IPlayer
import gq.genprog.simpletweaker.api.IServer
import gq.genprog.simpletweaker.api.ITextComponent
import gq.genprog.simpletweaker.nms.ClassDemystifier
import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class ServerWrapper(private val server: Any): IServer {
    val commands: HashMap<String, ICommand> = hashMapOf()

    fun getPlayerListNms(): Any {
        val playerListField = ClassDemystifier.getServerClass().getDeclaredField("s").apply { isAccessible = true }
        return playerListField.get(server)
    }

    override fun getPlayer(username: String): IPlayer? {
        val playerList = this.getPlayerListNms()

        val getPlayerByUsernameMeth = ClassDemystifier.getPlayerListClass().getDeclaredMethod("a", String::class.java)
        val nmsPlayer = getPlayerByUsernameMeth.invoke(playerList, username) ?: return null

        return PlayerWrapper(nmsPlayer)
    }

    override fun getPlayer(uuid: UUID): IPlayer? {
        val playerList = this.getPlayerListNms()

        val getPlayerByUidMeth = ClassDemystifier.getPlayerListClass().getDeclaredMethod("a", UUID::class.java)
        val nmsPlayer = getPlayerByUidMeth.invoke(playerList, uuid) ?: return null

        return PlayerWrapper(nmsPlayer)
    }

    override fun broadcast(component: ITextComponent, isSystem: Boolean) {
        val broadcastMeth = ClassDemystifier.getPlayerListClass().getDeclaredMethod("a",
                ClassDemystifier.getITextComponentClass(), Boolean::class.java)

        broadcastMeth.invoke(this.getPlayerListNms(), component.asNMS(), isSystem)
    }

    override fun broadcast(text: String) {
        this.broadcast(TextComponentWrapper(text), false)
    }

    override fun registerCommand(command: ICommand) {
        for (alias in command.getAliases()) {
            commands[alias] = command
        }
    }

    override fun asNMS() = server
}