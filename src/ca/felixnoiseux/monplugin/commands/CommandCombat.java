package ca.felixnoiseux.monplugin.commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.felixnoiseux.monplugin.LobbyCombat;
import ca.felixnoiseux.monplugin.PlayersList;


public class CommandCombat implements CommandExecutor {

	PlayersList _playersList;
	LobbyCombat lobby = new LobbyCombat();
	//Location du lobby du combat
	double lobbyX = -8.5;
	double lobbyY = 70.0;
	double lobbyZ = 93.5;
	float lobbyVx = -0.4f;
	float lobbyVy = 4.7f;
	
	//Location Arene Postion Ennemi
	double areneEnnemiX = 10.6;
	double areneEnnemiY = 68.0;
	double areneEnnemiZ = 85.227;
	float areneEnnemiVx = 44.8f;
	float areneEnnemiVy = 14.9f;
	
	//Location Arene Postion Ami
	double areneAmiX = 3.561;
	double areneAmiY = 68.0;
	double areneAmiZ = 92.443;
	float areneAmiVx = -131.2f;
	float areneAmiVy = 13.0f;
	
	public CommandCombat(PlayersList playersList){
		_playersList = playersList;
	}
	
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String msg, String[] args) {
		
		if(sender instanceof Player) {
			boolean ennemiExistant = false;
			Player player = (Player)sender;
			
			if(cmd.getName().equalsIgnoreCase("combat")){
				
				//Trop d'arguments
				if(args.length > 1) {
					player.sendMessage("Veuillez ecrire le nom du joueur seulement");
					return false;
				}
				// Request d'un joueur
				else if(args.length == 1) {
					
					
					String nomEnnemi = args[0];
					 
					for(int i = 0; i < _playersList.Players().size(); i++) {
						
						Player p = _playersList.Players().get(i);
						if(p.getName() == nomEnnemi) {
							//Request l'ennemi
							
							ennemiExistant = true;
							break;
						}
						
					}
					
					if(!ennemiExistant) {
						player.sendMessage("Ennemi hors ligne");
						return false;
					}
				
				}
				//Combat aleatoire (Teleportation au Lobby, attente d'un autre joueur)
				else {
					Location lobbyLocation = new Location(player.getWorld(),lobbyX,lobbyY,lobbyZ,lobbyVx,lobbyVy);
					Location areneEnnemiLocation = new Location(player.getWorld(),areneEnnemiX,areneEnnemiY,areneEnnemiZ,areneEnnemiVx,areneEnnemiVy);
					Location areneAmiLocation = new Location(player.getWorld(), areneAmiX, areneAmiY, areneAmiZ, areneAmiVx, areneAmiVy);

					try {
						
						player.sendMessage("Teleportation lobby du combat");
						for(int i = 3; i > 0; i--) {
							player.sendMessage("§a===" + Integer.toString(i) + "===");
							TimeUnit.SECONDS.sleep(1);
						}
						
						lobby.JoinLobby(player);
						player.teleport(lobbyLocation);
						
						
						//Debug
						Bukkit.broadcastMessage(String.valueOf(lobby.Players().size()));
						Bukkit.broadcastMessage(String.valueOf(lobby.EstComplet()));
						
						if(lobby.EstComplet()) {
							//Teleportation vers le combat dans 3 seconde
							Player p1 = lobby.Players().get(0);
							Player p2 = lobby.Players().get(1);
							
							player.sendMessage("Le combat debute dans ");
							for(int i = 3; i > 0; i--) {
								player.sendMessage("§a===" + Integer.toString(i) + "===");
								TimeUnit.SECONDS.sleep(1);
							}
							
							p1.teleport(areneEnnemiLocation);
							p2.teleport(areneAmiLocation);
							
							player.sendMessage("Maintenant !");
							lobby.ViderLobby();
						}
						
						
					} catch (InterruptedException e) {
						e.printStackTrace();
						return false;
					}
				}
				
			}
			
			
			
			
		}
		
		
		
		
		return false;
	}

}
