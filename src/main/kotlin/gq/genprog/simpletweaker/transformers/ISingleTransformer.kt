package gq.genprog.simpletweaker.transformers

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
interface ISingleTransformer {
    fun getClassName(): String
    fun transform(classBytes: ByteArray): ByteArray
}