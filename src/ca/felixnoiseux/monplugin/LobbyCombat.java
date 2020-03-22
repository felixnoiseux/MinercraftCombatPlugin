package ca.felixnoiseux.monplugin;

import java.util.ArrayList;

import org.bukkit.entity.Player;

//Lobby en attendant un combat
//Lorsque 2 joueurs sont dans le lobby, ils sont teleporte a larene et le combat commence

public class LobbyCombat {

	ArrayList<Player> _players;
	int _lobbyCapacite;
	boolean _estComplet;
	
	public LobbyCombat() {
		_players = new ArrayList<Player>();
		_lobbyCapacite = 2;
		_estComplet = false;
	}
	
	public void JoinLobby(Player player) {
		
		if(!_estComplet) {
			
			_players.add(player);
			
			//Verifier si le lobby est maintenant plein
			if(_players.size() == _lobbyCapacite) {
				Player p1 = _players.get(0);
				Player p2 = _players.get(1);
				_estComplet = true;
			}
		}
	}
	
	public void QuitterLobby(Player player) {
		_players.remove(player);
		if(_estComplet) {
			_estComplet = false;
		}
	}
	
	public ArrayList<Player> Players(){
		return _players;
	}
	
	public void ViderLobby() {
		_players = new ArrayList<Player>();
		_estComplet = false;
	}
	
	public boolean EstComplet() {
		return _estComplet;
	}
}
