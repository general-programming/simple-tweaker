package gq.genprog.simpletweaker.loader

import gq.genprog.simpletweaker.tweaks.ITweak

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class LoadedTweak<T: ITweak>(val instance: T, val manifest: ModManifest) {
    val id get() = manifest.id
    val name get() = manifest.name
}