package ca.felixnoiseux.monplugin.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.md_5.bungee.api.ChatColor;

public class CommandTest implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {
		
		if(sender instanceof Player) {
			Player player = (Player)sender;
			
			//Alert ou alErt ..
			if(cmd.getName().equalsIgnoreCase("test")) {
				//Envoie un message au joueur courant (celui qui ecrit la cmd)
				player.sendMessage("§eBravo tu as reussi le §btest");
				return true;
			}
			if(cmd.getName().equalsIgnoreCase("alert")) {
				
				if(args.length == 0) {
					player.sendMessage("la commande est : /alert <message>");
				}
				
				if(args.length >= 1) {
					StringBuilder bc = new StringBuilder();
					for(String part : args) {
						bc.append(part + " ");
					}
					Bukkit.broadcastMessage("["+ player.getName() +"] §9" + bc.toString());
				}
				
				
				return true;
			}
			
		}
		
		//Affiche dans la console
		//sender.sendMessage("Bravo tu as reussi le test");
		return false;
	}

}
