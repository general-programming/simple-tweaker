package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.*
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

    override fun getOnlinePlayers(): List<IPlayer> {
        val playerList = this.getPlayerListNms()

        val playerListField = ClassDemystifier.getPlayerListClass().getDeclaredField("i").apply { isAccessible = true }
        val nmsPlayerList = playerListField.get(playerList) as List<*>

        return nmsPlayerList.map {
            PlayerWrapper(it!!)
        }
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

    override fun getCommandHandler(): ICommandHandler {
        val cmdHandlerMeth = ClassDemystifier.getServerClass().getDeclaredMethod("aL")
        val nmsCmdHandler = cmdHandlerMeth.invoke(server)

        return CommandHandlerWrapper(nmsCmdHandler)
    }

    override fun getCommandRegistry(): ICommandRegistry {
        val cmdRegistryMeth = ClassDemystifier.getServerClass().getDeclaredMethod("aK")
        val nmsCmdRegistry = cmdRegistryMeth.invoke(server)

        return CommandRegistryWrapper(nmsCmdRegistry)
    }

    override fun dispatch(command: String) {
        this.getCommandRegistry().dispatch(this.getCommandHandler(), command)
    }

    override fun asNMS() = server
}