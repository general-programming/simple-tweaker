package gq.genprog.simpletweaker.nms

import java.util.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object ClassDemystifier {
    fun getServerClass(): Class<*> = Class.forName("net.minecraft.server.MinecraftServer")
    fun getITextComponentClass(): Class<*> = Class.forName("ij")
    fun getTextComponentBaseClass(): Class<*> = Class.forName("ig")
    fun getTextComponentStringClass(): Class<*> = Class.forName("iq")
    fun getPlayerMPClass(): Class<*> = Class.forName("te")
    fun getPlayerClass(): Class<*> = Class.forName("aoc")
    fun getEntityClass(): Class<*> = Class.forName("aeo")
    fun getPlayerListClass(): Class<*> = Class.forName("vo")
    fun getCommandHandlerClass(): Class<*> = Class.forName("bu")
    fun getCommandSenderClass(): Class<*> = Class.forName("bt")
    fun getCommandRegistryClass(): Class<*> = Class.forName("bv")

    fun newTextComponentString(text: String): Any {
        return this.getTextComponentStringClass().getDeclaredConstructor(String::class.java).newInstance(text)
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