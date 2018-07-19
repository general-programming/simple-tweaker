package gq.genprog.simpletweaker.events

import gq.genprog.simpletweaker.nms.ClassDemystifier
import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class PlayerChatEvent(val player: Any, val message: String) {
    fun getPlayerName(): String {
        return ClassDemystifier.getPlayerName(player)
    }

    fun getPlayerUniqueId(): UUID {
        return ClassDemystifier.getPlayerUID(player)
    }
}