package ca.felixnoiseux.monplugin;

import org.bukkit.entity.Player;

public class PlayerCustom {
	
	private Player _player;
	private boolean _estDansAreneCombat;
	private boolean _estDansLobbyCombat;
	private boolean _estAuSpawn;
	private EmplacementLocation _emplacementLocation;
	
	PlayerCustom(Player player, EmplacementLocation emplacementLocation){
		_player = player;
		_estDansAreneCombat = false;
		_estDansLobbyCombat = false;
		_estAuSpawn = false;
		_emplacementLocation = emplacementLocation;
	}
	
	public Player RecevoirPlayer() {
		return _player;
	}
	
	public void ModifierPlayer(Player player) {
		_player = player;
	}
	
	//Fonctionnalite aditionnels (GET/SET)
	
	
	//Modifie l'emplacement du joueur en le teleportant
	public void modifierEmplacementPlayer(Emplacement emplacement) {
		
		turnOffEmplacements();
		
		switch(emplacement) {
		case SPAWN:
			_estAuSpawn = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		case LOBBY_COMBAT:
			_estDansLobbyCombat = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		case ARENE_COMBAT:
			_estDansAreneCombat = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		case ARENE_COMBAT_ENNEMI:
			_estDansAreneCombat = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		case ARENE_COMBAT_AMI:
			_estDansAreneCombat = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		default:
			_estAuSpawn = true;
			_player.teleport(_emplacementLocation.RecevoirLocation(emplacement));	
			break;
		}
	}
	public boolean estDansAreneCombat() {
		return _estDansAreneCombat;
	}
	public boolean estDansLobbyCombat() {
		return _estDansLobbyCombat;
	}
	public boolean estAuSpawn() {
		return _estAuSpawn;
	}
	
	private void turnOffEmplacements() {
		_estDansAreneCombat = false;
		_estDansLobbyCombat = false;
		_estAuSpawn = false;
	}
}
