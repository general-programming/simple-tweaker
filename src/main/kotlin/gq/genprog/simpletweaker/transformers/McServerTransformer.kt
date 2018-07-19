package gq.genprog.simpletweaker.transformers

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class McServerTransformer: ClassFileTransformer {
    override fun transform(loader: ClassLoader?, className: String, classBeingRedefined: Class<*>?, protectionDomain: ProtectionDomain, classBytes: ByteArray?): ByteArray? {
        try {
            if (classBytes == null) return classBytes
            if (className != "net/minecraft/server/MinecraftServer") return classBytes

            val reader = ClassReader(classBytes)
            val node = ClassNode()
            reader.accept(node, ClassReader.EXPAND_FRAMES)

            // main method

            val mainMeth = node.methods.find { it.name == "main" } ?: return classBytes
            var foundNode: AbstractInsnNode? = null
            for (insr in mainMeth.instructions) {
                if (insr.opcode == Opcodes.INVOKESPECIAL) {
                    val invoke = insr as MethodInsnNode

                    if (invoke.owner == "sn" && invoke.name == "<init>") {
                        foundNode = invoke
                    }
                }
            }

            if (foundNode == null) return classBytes

            val extraInstructions = InsnList()
            extraInstructions.add(InsnNode(Opcodes.DUP))
            extraInstructions.add(MethodInsnNode(Opcodes.INVOKESTATIC, "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                    "injectMcServer", "(Ljava/lang/Object;)V", false))

            mainMeth.instructions.insert(foundNode, extraInstructions)

            // world gen method

            node.methods.find { it.name == "g_" }?.also { wgMethod ->
                wgMethod.instructions.insert(MethodInsnNode(Opcodes.INVOKESTATIC,
                        "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "preWorldInit", "()V", false))

                val lastReturn = wgMethod.instructions.last.previous
                wgMethod.instructions.insert(lastReturn.previous, MethodInsnNode(Opcodes.INVOKESTATIC,
                        "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "postWorldInit", "()V", false))
            }

            // shutdown method

            node.methods.find { it.name == "h_" }?.also { shutdownMethod ->
                shutdownMethod.instructions.insert(MethodInsnNode(Opcodes.INVOKESTATIC,
                        "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "emitShuttingDown", "()V", false))
            }

            val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES)
            node.accept(writer)

            return writer.toByteArray()
        } catch (err: Throwable) {
            err.printStackTrace()
            return null
        }
    }
}