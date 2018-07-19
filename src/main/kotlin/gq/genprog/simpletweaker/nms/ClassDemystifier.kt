package gq.genprog.simpletweaker.nms

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object ClassDemystifier {
    fun getTextComponentBaseClass(): Class<*> {
        return Class.forName("ig")
    }

    fun getTextComponentStringClass(): Class<*> {
        return Class.forName("iq")
    }

    fun getITextComponentClass(): Class<*> {
        return Class.forName("ij")
    }

    fun newTextComponentString(text: String): Any {
        return this.getTextComponentStringClass().getDeclaredConstructor(String::class.java).newInstance(text)
    }

    fun getTextComponentText(component: Any): String {
        return this.getTextComponentBaseClass().getDeclaredMethod("getString").invoke(component) as String
    }

    fun getPlayerMPClass(): Class<*> {
        return Class.forName("te")
    }

    fun getPlayerClass(): Class<*> {
        return Class.forName("aoc")
    }

    fun getEntityClass(): Class<*> {
        return Class.forName("aeo")
    }

    fun getPlayerName(player: Any): String {
        val getNameMeth = this.getPlayerClass().getDeclaredMethod("bv")
        return getNameMeth.invoke(player) as String
    }

    fun getPlayerUID(player: Any): UUID {
        val getUidMeth = this.getEntityClass().getDeclaredMethod("bt")
        return getUidMeth.invoke(player) as UUID
    }
}