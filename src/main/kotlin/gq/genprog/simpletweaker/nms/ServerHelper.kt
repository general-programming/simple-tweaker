package gq.genprog.simpletweaker.nms

import gq.genprog.simpletweaker.Helper
import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object ServerHelper {
    fun getServerClass() = Class.forName("net.minecraft.server.MinecraftServer")

    fun getPlayer(server: Any, name: String): Any? {
        val playerListField = getServerClass().getDeclaredField("s").apply { isAccessible = true }
        val playerList = playerListField.get(server)

        val getPlayerByUsernameMeth = playerListField.type.getDeclaredMethod("a", String::class.java)
        return getPlayerByUsernameMeth.invoke(playerList, name)
    }

    fun getPlayer(server: Any, uid: UUID): Any? {
        val playerListField = getServerClass().getDeclaredField("s").apply { isAccessible = true }
        val playerList = playerListField.get(server)

        val getPlayerByUidMeth = playerListField.type.getDeclaredMethod("a", UUID::class.java)
        return getPlayerByUidMeth.invoke(playerList, uid)
    }

    fun broadcast(server: Any, textComponent: Any, isSystem: Boolean) {
        val playerListField = getServerClass().getDeclaredField("s").apply { isAccessible = true }
        val playerList = playerListField.get(server)

        val broadcastMeth = playerListField.type.getDeclaredMethod("a", ClassDemystifier.getITextComponentClass(), Boolean::class.java)
        broadcastMeth.invoke(playerList, textComponent, isSystem)
    }

    fun broadcast(textComponent: Any) {
        Helper.getMinecraftServer()?.also {
            this.broadcast(it, textComponent, false)
        }
    }
}