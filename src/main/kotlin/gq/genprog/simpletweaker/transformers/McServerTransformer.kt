package gq.genprog.simpletweaker.transformers

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.InsnNode
import org.objectweb.asm.tree.MethodInsnNode

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class McServerTransformer: ISingleTransformer {
    override fun getClassName() = "net.minecraft.server.MinecraftServer"

    override fun transform(classBytes: ByteArray): ByteArray {
        val reader = ClassReader(classBytes)
        val node = ClassNode()
        reader.accept(node, 0)

        // main method

        node.methods.find { it.name == "main" }?.also { mainMeth ->
            mainMeth.instructions.find {
                if (it.opcode != Opcodes.INVOKESPECIAL) return@find false

                it as MethodInsnNode
                it.owner == "sn" && it.name == "<init>"
            }?.apply {
                val extra = InsnList()
                extra.add(InsnNode(Opcodes.DUP))
                extra.add(MethodInsnNode(Opcodes.INVOKESTATIC, "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "injectMcServer", "(Ljava/lang/Object;)V", false))

                mainMeth.instructions.insert(this, extra)
            }
        }

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

        val writer = ClassWriter(0)
        node.accept(writer)

        return writer.toByteArray()
    }
}