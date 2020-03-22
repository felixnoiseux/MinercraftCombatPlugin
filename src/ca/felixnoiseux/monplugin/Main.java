package ca.felixnoiseux.monplugin;

import org.bukkit.plugin.java.JavaPlugin;

import ca.felixnoiseux.monplugin.commands.CommandCombat;
import ca.felixnoiseux.monplugin.commands.CommandSpawn;
import ca.felixnoiseux.monplugin.commands.CommandTest;

public class Main extends JavaPlugin {
	
	PlayerCustomList _playerCustomList = new PlayerCustomList();
	

	//onEnable (Quand le plugin s'allume)
	@Override
	public void onEnable() {
		System.out.println("==================PLUGIN EN DEMARRAGE================");
		
		System.out.println("INITIALISATION DES EVENEMENTS");
		getServer().getPluginManager().registerEvents(new PluginListeners(_playerCustomList),this);
		System.out.println("INITIALISATION DES EVENEMENTS TERMINE");
		
		System.out.println("INITIALISATION DES COMMANDES");
		getCommand("test").setExecutor(new CommandTest());
		getCommand("alert").setExecutor(new CommandTest());
		getCommand("combat").setExecutor(new CommandCombat(_playerCustomList));
		getCommand("spawn").setExecutor(new CommandSpawn());
		System.out.println("INITIALISATION DES COMMANDES TERMINE");
	
	}
	
	@Override
	public void onDisable() {
		System.out.println("==================PLUGIN FERME====================");
	}
}
