package gq.genprog.simpletweaker.loader

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
data class ModManifest(val id: String, val name: String?, val tweaks: Array<String>) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModManifest) return false

        return id == other.id
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}