package gq.genprog.simpletweaker.transformers

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Label
import org.objectweb.asm.tree.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class NetHandlerTransformer: ISingleTransformer {
    override fun getClassName() = "ub"

    override fun transform(classBytes: ByteArray): ByteArray {
        val reader = ClassReader(classBytes)
        val node = ClassNode().also { reader.accept(it, 0) }

        node.methods.find { it.name == "a" && it.desc == "(Lmh;)V" }?.apply {
            instructions.toArray().find {
                if (it.opcode == Opcodes.INVOKEVIRTUAL) {
                    val inv = it as MethodInsnNode

                    inv.owner == "vo" && inv.name == "a"
                } else {
                    false
                }
            }?.apply {
                val extra = InsnList()

                extra.add(VarInsnNode(Opcodes.ALOAD, 2))
                extra.add(VarInsnNode(Opcodes.ALOAD, 0))
                extra.add(FieldInsnNode(Opcodes.GETFIELD, "ub", "b", "Lte;"))
                extra.add(MethodInsnNode(Opcodes.INVOKESTATIC,
                        "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "emitChat", "(Ljava/lang/String;Ljava/lang/Object;)V", false))

                instructions.insert(this, extra)
            }
        }

        node.methods.find { it.name == "a" && it.desc == "(Lij;)V" }?.apply {
            val extra = InsnList()

            extra.add(VarInsnNode(Opcodes.ALOAD, 0))
            extra.add(FieldInsnNode(Opcodes.GETFIELD, "ub", "b", "Lte;"))
            extra.add(MethodInsnNode(Opcodes.INVOKESTATIC,
                    "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                    "emitPlayerLeave", "(Ljava/lang/Object;)V", false))

            instructions.insert(extra)
        }

        node.methods.find { it.name == "c" && it.desc == "(Ljava/lang/String;)V" }?.apply {
            val extra = InsnList()

            val label = LabelNode(Label())

            extra.add(VarInsnNode(Opcodes.ALOAD, 1))
            extra.add(VarInsnNode(Opcodes.ALOAD, 0))
            extra.add(FieldInsnNode(Opcodes.GETFIELD, "ub", "b", "Lte;"))
            extra.add(MethodInsnNode(Opcodes.INVOKESTATIC,
                    "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                    "emitCustomCommand", "(Ljava/lang/String;Ljava/lang/Object;)Z", false))
            extra.add(JumpInsnNode(Opcodes.IFEQ, label))
            extra.add(InsnNode(Opcodes.RETURN))
            extra.add(label)

            instructions.insert(extra)
        }

        val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        node.accept(writer)

//        File("NetHandlerPlayServer.class").writeBytes(writer.toByteArray())
        return writer.toByteArray()
    }
}