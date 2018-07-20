package gq.genprog.simpletweaker.transformers

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.ClassNode
import org.objectweb.asm.tree.InsnList
import org.objectweb.asm.tree.MethodInsnNode
import org.objectweb.asm.tree.VarInsnNode

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class PlayerListTransformer: ISingleTransformer {
    override fun getClassName() = "vo"

    override fun transform(classBytes: ByteArray): ByteArray {
        val reader = ClassReader(classBytes)
        val node = ClassNode().also { reader.accept(it, 0) }

        node.methods.find { it.name == "a" && it.desc == "(Lhw;Lte;)V" }?.apply {
            val extra = InsnList()

            extra.add(VarInsnNode(Opcodes.ALOAD, 2))
            extra.add(MethodInsnNode(Opcodes.INVOKESTATIC,
                    "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                    "emitPlayerJoin", "(Ljava/lang/Object;)V", false))

            instructions.insert(extra)
        }

        val writer = ClassWriter(0)
        node.accept(writer)

        return writer.toByteArray()
    }
}