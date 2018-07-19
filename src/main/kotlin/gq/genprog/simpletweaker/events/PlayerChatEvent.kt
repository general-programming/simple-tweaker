package gq.genprog.simpletweaker.events

import gq.genprog.simpletweaker.nms.ClassDemystifier

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class PlayerChatEvent(private val textComponent: Any) {
    fun getMessage(): String {
        return ClassDemystifier.getTextComponentText(textComponent)
    }
}