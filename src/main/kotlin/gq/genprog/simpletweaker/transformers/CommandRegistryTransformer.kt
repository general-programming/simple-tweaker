package gq.genprog.simpletweaker.transformers

import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.Opcodes
import org.objectweb.asm.tree.*

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class CommandRegistryTransformer: ISingleTransformer {
    override fun getClassName() = "bv"

    override fun transform(classBytes: ByteArray): ByteArray {
        val reader = ClassReader(classBytes)
        val node = ClassNode()
        reader.accept(node, 0)

        node.methods.find { it.name == "a" && it.desc == "(Lbu;Ljava/lang/String;)I" }?.apply {
            instructions.find { it.opcode == Opcodes.ALOAD && (it as VarInsnNode).`var` == 1 }?.also {
                val extra = InsnList()
                val label = LabelNode()

                extra.add(VarInsnNode(Opcodes.ALOAD, 2))
                extra.add(VarInsnNode(Opcodes.ALOAD, 1))
                extra.add(MethodInsnNode(Opcodes.INVOKESTATIC,
                        "gq/genprog/simpletweaker/hooks/MinecraftHooks",
                        "emitCustomCommand", "(Ljava/lang/String;Ljava/lang/Object;)Z", false))
                extra.add(JumpInsnNode(Opcodes.IFEQ, label))
                extra.add(LdcInsnNode(0))
                extra.add(InsnNode(Opcodes.IRETURN))
                extra.add(label)

                instructions.insertBefore(it, extra)
            }
        }

        val writer = ClassWriter(ClassWriter.COMPUTE_FRAMES)
        node.accept(writer)

        return writer.toByteArray()
    }
}