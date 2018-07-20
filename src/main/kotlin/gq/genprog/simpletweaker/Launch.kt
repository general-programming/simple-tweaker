package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.transformers.MainTransformer
import java.lang.instrument.Instrumentation

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
fun premain(agentArgs: String?, inst: Instrumentation) {
    inst.addTransformer(MainTransformer())

    println("SimpleTweaker transformers registered.")
}