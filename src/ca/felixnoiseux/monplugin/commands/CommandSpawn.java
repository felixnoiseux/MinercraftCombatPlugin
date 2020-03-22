package ca.felixnoiseux.monplugin.commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandSpawn implements CommandExecutor {
	
	//Spawn Coords
	int x = -23;
	int y = 74;
	int z = 117;
	
	//View Direcotry
	float vx = 0.5f;
	float vy = 5.0f;
	
	
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String msg, String[] arg) {
		
		if(sender instanceof Player) {
			
			Player player = (Player)sender;
			
			Location spawnLocation = new Location(player.getWorld(), x, y, z, vx, vy);
			Location currentLocation = player.getLocation();
			
			//Verifie si on est a l'exterieur du range du spawn
			if(currentLocation.getX() < spawnLocation.getX() - 10 || currentLocation.getX() > spawnLocation.getX() + 10 ||
			   currentLocation.getY() < spawnLocation.getY() - 10 || currentLocation.getY() > spawnLocation.getY() + 10 ||
			   currentLocation.getZ() < spawnLocation.getZ() - 10 ||  currentLocation.getZ() > spawnLocation.getZ() + 10 ) {
				
				try {
					
					player.sendMessage("Teleportation au Spawn");
					for(int i = 3; i > 0; i--) {
						player.sendMessage("§a===" + Integer.toString(i) + "===");
						TimeUnit.SECONDS.sleep(1);
					}

					player.teleport(spawnLocation);
					
				} catch (InterruptedException e) {
					e.printStackTrace();
					return false;
				}
			}
			else {
				player.sendMessage("§aVous etes trop pres du spawn");
			}
	
			
			
			return true;
		}
		
		return false;
	}
}
