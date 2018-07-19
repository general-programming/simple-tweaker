package gq.genprog.simpletweaker.events

import java.lang.reflect.Method

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
class EventBus {
    val handlers: HashMap<Class<*>, ArrayList<WrappedMethod<*>>> = hashMapOf()

    fun register(subscriber: Any) {
        for (method in subscriber.javaClass.methods) {
            if (method.isAnnotationPresent(EventHandler::class.java)) {
                if (method.parameters.size != 1) throw IllegalArgumentException("Bad method passed to EventBus#register.")

                val eventClass = method.parameters[0].type

                if (!handlers.containsKey(eventClass))
                    handlers[eventClass] = arrayListOf()

                handlers[eventClass]!!.add(WrappedMethod(subscriber, method))
            }
        }
    }

    fun post(event: Any) {
        if (!handlers.containsKey(event.javaClass)) return

        handlers[event.javaClass]?.forEach {
            it.invoke(event)
        }
    }

    class WrappedMethod<T>(val instance: T, val method: Method) {
        fun invoke(vararg params: Any) {
            method.invoke(instance, *params)
        }
    }
}