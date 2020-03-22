package ca.felixnoiseux.monplugin;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class PlayerCustomList {
	
	private ArrayList<PlayerCustom> _playersCustom;
	
	public PlayerCustomList() {
		_playersCustom = new ArrayList<PlayerCustom>();
	}
	
	public void InsertPlayer(PlayerCustom player) {
		if(!_playersCustom.contains(player)) {
			_playersCustom.add(player);
		}
	}
	
	
	public void RemovePlayer(Player player) {
		
		
		for(int i=0; i < _playersCustom.size(); i++) {
			Player p = _playersCustom.get(i).RecevoirPlayer();
			if(p == player) {
				_playersCustom.remove(i);
				break;
			}
		}

	}
	
	public ArrayList<PlayerCustom> PlayersCustom(){
		return _playersCustom;
	}
}
