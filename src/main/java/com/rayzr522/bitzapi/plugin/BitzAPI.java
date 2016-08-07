
package com.rayzr522.bitzapi.plugin;

import org.bukkit.plugin.RegisteredServiceProvider;

import com.rayzr522.bitzapi.BitzPlugin;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandClearSel;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandCreateInv;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandFun;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandItem;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandSet;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandShow;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandTools;
import com.rayzr522.bitzapi.commands.bitz.BitzCommandVersion;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;

public abstract class BitzAPI extends BitzPlugin {

	public static BitzAPI		instance;

	public static Permission	permission	= null;
	public static Economy		economy		= null;
	public static Chat			chat		= null;

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	private boolean setupChat() {
		RegisteredServiceProvider<Chat> chatProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.chat.Chat.class);
		if (chatProvider != null) {
			chat = chatProvider.getProvider();
		}

		return (chat != null);
	}

	private boolean setupEconomy() {
		RegisteredServiceProvider<Economy> economyProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			economy = economyProvider.getProvider();
		}

		return (economy != null);
	}

	@Override
	public final void onEnable() {

		super.onEnable();

		instance = this;

		setupPermissions();
		setupChat();
		setupEconomy();

		registerEventHandler(new BitzAPIHandler(this));

		commandHandler.autoSetup();

		registerCommands();

		onLoad();

	}

	public abstract void onLoad();

	@Override
	public final void onDisable() {

		super.onDisable();

		onUnload();

	}

	public abstract void onUnload();

	private void registerCommands() {

		// Version
		commandHandler.registerCommand(BitzCommandVersion.class);

		// BitzAPI-tools related commands
		commandHandler.registerCommand(BitzCommandTools.class);
		commandHandler.registerCommand(BitzCommandShow.class);
		commandHandler.registerCommand(BitzCommandSet.class);
		commandHandler.registerCommand(BitzCommandClearSel.class);

		// BitzAPI-inventory system tool
		commandHandler.registerCommand(BitzCommandCreateInv.class);

		// Sub CommandHandlers
		commandHandler.registerCommand(BitzCommandFun.class);
		commandHandler.registerCommand(BitzCommandItem.class);

	}

	@Override
	public String getBitzVersion() {
		return this.getDescription().getVersion();
	}

}