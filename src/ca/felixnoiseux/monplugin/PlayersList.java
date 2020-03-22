package ca.felixnoiseux.monplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayersList {
	
	ArrayList<Player> players;
	
	public PlayersList() {
		players = new ArrayList<Player>();
	}
	
	public void InsertPlayer(Player player) {
		if(!players.contains(player)) {
			players.add(player);
		}
	}
	
	//Fonctionnalite futur
	public void InsertPlayers(List<Player> players) {
	
		for(int i = 0; i < this.players.size(); i++) {
			
		}
		
	}
	
	public void RemovePlayer(Player player) {
		if(players.contains(player)) {
			players.remove(players.indexOf(player));
		}
	}
	
	public ArrayList<Player> Players(){
		return players;
	}
}
