package ca.felixnoiseux.monplugin;

import org.bukkit.plugin.java.JavaPlugin;

import ca.felixnoiseux.monplugin.commands.CommandCombat;
import ca.felixnoiseux.monplugin.commands.CommandSpawn;
import ca.felixnoiseux.monplugin.commands.CommandTest;

public class Main extends JavaPlugin {
	
	PlayersList playersList = new PlayersList();
	

	//onEnable (Quand le plugin s'allume)
	@Override
	public void onEnable() {
		//sysout + CTRL + ESPACE
		System.out.println("Le plugin est turn on");
		System.out.println("Initialisation des commandes");
		getCommand("test").setExecutor(new CommandTest());
		getCommand("alert").setExecutor(new CommandTest());
		getCommand("combat").setExecutor(new CommandCombat(playersList));
		getCommand("spawn").setExecutor(new CommandSpawn());
		getServer().getPluginManager().registerEvents(new PluginListeners(playersList),this);
	}
	
	@Override
	public void onDisable() {
		System.out.println("Le plugin est turn off");
	}
}
