package gq.genprog.simpletweaker

import gq.genprog.simpletweaker.transformers.McServerTransformer
import gq.genprog.simpletweaker.transformers.NetHandlerTransformer
import gq.genprog.simpletweaker.transformers.PlayerListTransformer
import java.lang.instrument.Instrumentation

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
fun premain(agentArgs: String?, inst: Instrumentation) {
    inst.addTransformer(McServerTransformer())
    inst.addTransformer(PlayerListTransformer())
    inst.addTransformer(NetHandlerTransformer())

    println("SimpleTweaker transformers registered.")
}