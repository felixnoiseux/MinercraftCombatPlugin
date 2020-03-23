package ca.felixnoiseux.monplugin.commands;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.felixnoiseux.monplugin.Emplacement;
import ca.felixnoiseux.monplugin.LobbyCombat;
import ca.felixnoiseux.monplugin.PlayerCustom;
import ca.felixnoiseux.monplugin.PlayerCustomList;


public class CommandCombat implements CommandExecutor {

	private PlayerCustomList _playerCustomList;
	private LobbyCombat _lobby;
	private Player _player;
	private PlayerCustom _playerCustom;
	private PlayerCustom _ennemiCustom;

	
	public CommandCombat(PlayerCustomList playerCustomList){
		_playerCustomList = playerCustomList;
		_lobby = new LobbyCombat();
	}
	
	@Override
	public boolean onCommand(CommandSender sender,  Command cmd,  String msg, String[] args) {
		
		if(sender instanceof Player) {
			
			boolean ennemiExistant = false;
			_player = (Player)sender;
			InitialiserPlayerCustom();
			
			if(cmd.getName().equalsIgnoreCase("combat")){
				
				if(args.length > 1) {
					_player.sendMessage("Veuillez ecrire le nom du joueur seulement");
					return false;
				}
				// Request d'un joueur
				else if(args.length == 1) {
					
					
					String arg = args[0];
					
					// /combat accept
					if(arg.equalsIgnoreCase("accept")) {
						
						if(_playerCustom.estRequestPourCombat()) {
							
							try {
							 Player ennemi = _playerCustom.ennemiRequestPourCombat().RecevoirPlayer();
							 _player.sendMessage("Teleportation arene");
							 ennemi.sendMessage("Combat accepte ! | Teleportation arene");
								for(int i = 3; i > 0; i--) {
									_player.sendMessage("§a===" + Integer.toString(i) + "===");
									ennemi.sendMessage("§a===" + Integer.toString(i) + "===");
									TimeUnit.SECONDS.sleep(1);
									
								}
								
								_playerCustom.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_AMI);
								_playerCustom.ennemiRequestPourCombat().modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_ENNEMI);
							}
							 catch (InterruptedException e) {
									e.printStackTrace();
								}
						}
						
					}
					// /combat nomEnnemi
					else {
					
						//Verifier Si Ennemi existe
						for(int i = 0; i < _playerCustomList.PlayersCustom().size(); i++) {
							
							Player p = _playerCustomList.PlayersCustom().get(i).RecevoirPlayer();
							if(p.getName().equals(arg)) {
								
								_ennemiCustom = _playerCustomList.PlayersCustom().get(i);
								RequestCombatEnnemi();
								
								ennemiExistant = true;
								break;
							}
							
						}
						
						if(!ennemiExistant) {
							_player.sendMessage("Ennemi hors ligne");
							return false;
						}
					}
				
				}
				//Combat aleatoire (Teleportation au Lobby, attente d'un autre joueur)
				else {

					try {
						
						_playerCustom.RecevoirPlayer().sendMessage("Teleportation lobby du combat");
						for(int i = 3; i > 0; i--) {
							_playerCustom.RecevoirPlayer().sendMessage("§a===" + Integer.toString(i) + "===");
							TimeUnit.SECONDS.sleep(1);
						}
						
						_lobby.JoinLobby(_playerCustom);
						_playerCustom.modifierEmplacementPlayer(Emplacement.LOBBY_COMBAT);


						if(_lobby.EstComplet()) {
							//Teleportation vers le combat dans 3 seconde
							PlayerCustom p1 = _lobby.Players().get(0);
							PlayerCustom p2 = _lobby.Players().get(1);
							
							_playerCustom.RecevoirPlayer().sendMessage("Le combat debute dans ");
							for(int i = 3; i > 0; i--) {
								_player.sendMessage("§a===" + Integer.toString(i) + "===");
								TimeUnit.SECONDS.sleep(1);
							}
							
							p1.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_ENNEMI);
							p2.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_AMI);
							
							_playerCustom.RecevoirPlayer().sendMessage("Maintenant !");
							_lobby.ViderLobby();
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
	
	//Initialise le PlayerCustom de Player
	private void InitialiserPlayerCustom() {
		//Bukkit.broadcastMessage(String.valueOf(_playerCustomList.PlayersCustom().size()));
		for(int i=0; i < _playerCustomList.PlayersCustom().size(); i++) {
			Player p = _playerCustomList.PlayersCustom().get(i).RecevoirPlayer();
			//Bukkit.broadcastMessage(p.getName());
			if(_player == p) {
				_playerCustom = _playerCustomList.PlayersCustom().get(i);
				break;
			}
		}
	}
	
	private void RequestCombatEnnemi() {
		_ennemiCustom.RecevoirPlayer().sendMessage(_playerCustom.RecevoirPlayer().getName() + " veux vous combattre. ");
		_ennemiCustom.RecevoirPlayer().sendMessage("§c[/combat accept] §epour vous combattre. ");
		_ennemiCustom.definirRequestCombat(true, _playerCustom);
		
	}

}
