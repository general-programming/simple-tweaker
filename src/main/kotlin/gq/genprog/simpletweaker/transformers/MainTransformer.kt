package gq.genprog.simpletweaker.transformers

import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class MainTransformer: ClassFileTransformer {
    val mcServerTransformer = McServerTransformer()
    val netHandlerTransformer = NetHandlerTransformer()
    val playerListTransformer = PlayerListTransformer()

    override fun transform(loader: ClassLoader?, className: String, classBeingRedefined: Class<*>?, protectionDomain: ProtectionDomain, classBytes: ByteArray?): ByteArray? {
        if (classBytes == null) return null

        val transformer = when (className) {
            "net.minecraft.server.MinecraftServer" -> mcServerTransformer
            "ub" -> netHandlerTransformer
            "vo" -> playerListTransformer
            else -> null
        }

        return try {
            transformer?.transform(classBytes)
        } catch (err: Exception) {
            err.printStackTrace() // otherwise the JVM is silent about this error
            null
        }
    }
}