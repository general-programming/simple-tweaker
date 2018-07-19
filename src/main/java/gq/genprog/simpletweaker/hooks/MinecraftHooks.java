package gq.genprog.simpletweaker.hooks;

import gq.genprog.simpletweaker.SimpleTweaker;
import gq.genprog.simpletweaker.events.PlayerChatEvent;
import gq.genprog.simpletweaker.events.PlayerJoinEvent;
import gq.genprog.simpletweaker.loader.ModLoader;
import gq.genprog.simpletweaker.tweaks.TweakStage;

import java.io.File;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class MinecraftHooks {
	public static SimpleTweaker tweaker = new SimpleTweaker();
	public static Object mcServer;

	public static void injectMcServer(Object server) {
		System.out.println("Got MinecraftServer object " + server.toString());

		mcServer = server;

		ModLoader loader = new ModLoader();
		loader.discover(new File("./tweaks/"));

		tweaker.initTweaks();
		tweaker.runTweaks(TweakStage.INIT);
	}

	public static void preWorldInit() {
		System.out.println("Pre world tweaks");

		tweaker.runTweaks(TweakStage.PRE_WORLD);
	}

	public static void postWorldInit() {
		System.out.println("Post world tweaks");

		tweaker.runTweaks(TweakStage.POST_WORLD);
	}

	public static void emitPlayerJoin(Object player) {
		tweaker.getEventBus().post(new PlayerJoinEvent(player));
	}

	public static void emitChat(String message, Object player) {
		tweaker.getEventBus().post(new PlayerChatEvent(player, message));
	}
}
