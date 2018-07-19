package gq.genprog.simpletweaker.nms

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
object ClassDemystifier {
    fun getTextComponentBaseClass(): Class<*> {
        return Class.forName("ig")
    }

    fun getTextComponentStringClass(): Class<*> {
        return Class.forName("iq")
    }

    fun getITextComponentClass(): Class<*> {
        return Class.forName("ij")
    }

    fun newTextComponentString(text: String): Any {
        return this.getTextComponentStringClass().getDeclaredConstructor(String::class.java).newInstance(text)
    }

    fun getTextComponentText(component: Any): String {
        return this.getTextComponentBaseClass().getDeclaredMethod("getString").invoke(component) as String
    }
}