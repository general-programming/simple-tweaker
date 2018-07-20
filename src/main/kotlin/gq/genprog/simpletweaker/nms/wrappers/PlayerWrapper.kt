package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.IPlayer
import gq.genprog.simpletweaker.nms.ClassDemystifier
import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class PlayerWrapper(private val playerInst: Any): CommandSenderWrapper(playerInst), IPlayer {
    override fun getUniqueId(): UUID = ClassDemystifier.getPlayerUID(playerInst)

    override fun getUsername(): String = ClassDemystifier.getPlayerName(playerInst)

    override fun sendMessage(text: String) {
        val sendTextCompMeth = ClassDemystifier.getPlayerMPClass().getDeclaredMethod("a",
                ClassDemystifier.getITextComponentClass(), Boolean::class.java)

        sendTextCompMeth.invoke(playerInst, ClassDemystifier.newTextComponentString(text), false)
    }

    override fun asNMS(): Any = playerInst
}