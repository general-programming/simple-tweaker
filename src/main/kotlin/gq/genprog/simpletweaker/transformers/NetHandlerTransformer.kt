package gq.genprog.simpletweaker.transformers

import jdk.internal.org.objectweb.asm.Opcodes
import org.objectweb.asm.ClassReader
import org.objectweb.asm.ClassWriter
import org.objectweb.asm.tree.*
import java.lang.instrument.ClassFileTransformer
import java.security.ProtectionDomain

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class NetHandlerTransformer: ClassFileTransformer {
    override fun transform(loader: ClassLoader?, className: String, classBeingRedefined: Class<*>?, protectionDomain: ProtectionDomain, classBytes: ByteArray?): ByteArray? {
        if (classBytes == null) return null
        if (className != "ub") return classBytes

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

        val writer = ClassWriter(0)
        node.accept(writer)

        return writer.toByteArray()
    }
}