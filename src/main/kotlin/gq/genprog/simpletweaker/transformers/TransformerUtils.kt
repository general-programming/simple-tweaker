package gq.genprog.simpletweaker.transformers

import org.objectweb.asm.tree.AbstractInsnNode
import org.objectweb.asm.tree.InsnList

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
fun InsnList.find(predicate: (AbstractInsnNode) -> Boolean): AbstractInsnNode? {
    var node: AbstractInsnNode? = this.first
    while (node != null) {
        if (predicate.invoke(node))
            return node

        node = node.next
    }

    return null
}