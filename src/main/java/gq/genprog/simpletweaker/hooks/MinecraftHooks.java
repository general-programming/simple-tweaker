package gq.genprog.simpletweaker.hooks;

import gq.genprog.simpletweaker.SimpleTweaker;
import gq.genprog.simpletweaker.api.ICommand;
import gq.genprog.simpletweaker.api.ICommandHandler;
import gq.genprog.simpletweaker.api.IPlayer;
import gq.genprog.simpletweaker.events.PlayerChatEvent;
import gq.genprog.simpletweaker.events.PlayerJoinEvent;
import gq.genprog.simpletweaker.events.PlayerLeaveEvent;
import gq.genprog.simpletweaker.events.ServerStoppingEvent;
import gq.genprog.simpletweaker.loader.ModLoader;
import gq.genprog.simpletweaker.nms.wrappers.CommandHandlerWrapper;
import gq.genprog.simpletweaker.nms.wrappers.PlayerWrapper;
import gq.genprog.simpletweaker.nms.wrappers.ServerWrapper;
import gq.genprog.simpletweaker.tweaks.TweakStage;

import java.io.File;
import java.util.Arrays;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class MinecraftHooks {
	public static SimpleTweaker tweaker = new SimpleTweaker();
	public static Object mcServer;
	public static ServerWrapper wrappedServer;

	public static void injectMcServer(Object server) {
		System.out.println("Got MinecraftServer object " + server.toString());

		mcServer = server;
		wrappedServer = new ServerWrapper(server);

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
		IPlayer wrapped = new PlayerWrapper(player);

		tweaker.getEventBus().post(new PlayerJoinEvent(wrapped));
	}

	public static void emitPlayerLeave(Object player) {
		IPlayer wrapped = new PlayerWrapper(player);

		tweaker.getEventBus().post(new PlayerLeaveEvent(wrapped));
	}

	public static void emitChat(String message, Object player) {
		IPlayer wrapped = new PlayerWrapper(player);

		tweaker.getEventBus().post(new PlayerChatEvent(wrapped, message));
	}

	public static void emitShuttingDown() {
		tweaker.getEventBus().post(new ServerStoppingEvent());
	}

	public static boolean emitCustomCommand(String commandString, Object commandHandler) {
		String[] parts = commandString.split("\\s");
		String commandName = parts[0];

		ICommandHandler wrappedHandler = new CommandHandlerWrapper(commandHandler);
		ICommand cmd = wrappedServer.getCommands().get(commandName);

		if (cmd != null) {
			String[] args = Arrays.copyOfRange(parts, 1, parts.length);
			cmd.execute(wrappedHandler.getCommandSender(), args);

			return true;
		}

		return false;
	}
}
