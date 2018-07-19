package gq.genprog.simpletweaker.nms.wrappers

import gq.genprog.simpletweaker.api.ITextComponent
import gq.genprog.simpletweaker.nms.ClassDemystifier

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class TextComponentWrapper(private val component: Any): ITextComponent {
    constructor(text: String): this(ClassDemystifier.newTextComponentString(text))

    override fun getText(): String {
        return ClassDemystifier.getTextComponentBaseClass()
                .getDeclaredMethod("getString").invoke(component) as String
    }

    override fun asNMS(): Any = component
}