package ca.felixnoiseux.monplugin;

import java.util.ArrayList;

import org.bukkit.entity.Player;

//Lobby en attendant un combat
//Lorsque 2 joueurs sont dans le lobby, ils sont teleporte a larene et le combat commence

public class LobbyCombat {

	ArrayList<PlayerCustom> _playerCustomInLobby;
	int _lobbyCapacite;
	boolean _estComplet;
	
	public LobbyCombat() {
		_playerCustomInLobby = new ArrayList<PlayerCustom>();
		_lobbyCapacite = 2;
		_estComplet = false;
	}
	
	public void JoinLobby(PlayerCustom playerCustom) {
		
		if(!_estComplet) {
			
			_playerCustomInLobby.add(playerCustom);
			
			//Verifier si le lobby est maintenant plein
			if(_playerCustomInLobby.size() == _lobbyCapacite) {
				Player p1 = _playerCustomInLobby.get(0).RecevoirPlayer();
				Player p2 = _playerCustomInLobby.get(1).RecevoirPlayer();
				_estComplet = true;
			}
		}
	}
	
	public void QuitterLobby(PlayerCustom playerCustom) {
		_playerCustomInLobby.remove(playerCustom);
		if(_estComplet) {
			_estComplet = false;
		}
	}
	
	public ArrayList<PlayerCustom> Players(){
		return _playerCustomInLobby;
	}
	
	public void ViderLobby() {
		_playerCustomInLobby = new ArrayList<PlayerCustom>();
		_estComplet = false;
	}
	
	public boolean EstComplet() {
		return _estComplet;
	}
}
