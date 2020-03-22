package ca.felixnoiseux.monplugin;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class EmplacementLocation {

	
	//Location du Spawn
	private double spawnX = -23;
	private double spawnY = 74;
	private double spawnZ = 117;
	private float spawnVx = 0.5f;
	private float spawnVy = 5.0f;
	
	//Location du lobby du combat
	private double lobbyX = -8.5;
	private double lobbyY = 70.0;
	private double lobbyZ = 93.5;
	private float lobbyVx = -0.4f;
	private float lobbyVy = 4.7f;
	
	
	//Location Arene
	private double areneX = 7.935;
	private double areneY = 68.0;
	private double areneZ = 88.930;
	
	//Location Arene Postion Ennemi
	private double areneEnnemiX = 10.6;
	private double areneEnnemiY = 68.0;
	private double areneEnnemiZ = 85.227;
	private float areneEnnemiVx = 44.8f;
	private float areneEnnemiVy = 14.9f;
	
	//Location Arene Postion Ami
	private double areneAmiX = 3.561;
	private double areneAmiY = 68.0;
	private double areneAmiZ = 92.443;
	private float areneAmiVx = -131.2f;
	private float areneAmiVy = 13.0f;
	
	private Location spawnLocation;
	private Location lobbyLocation;
	private Location areneLocation;
	private Location areneEnnemiLocation;
	private Location areneAmiLocation;
	
	
	public EmplacementLocation() {
		World world = Bukkit.getWorlds().get(0);
		spawnLocation = new Location(world, spawnX, spawnY, spawnZ, spawnVx, spawnVy);
		lobbyLocation = new Location(world ,lobbyX,lobbyY,lobbyZ,lobbyVx,lobbyVy);
		areneLocation = new Location(world, areneX, areneY, areneZ);
		areneEnnemiLocation = new Location(world ,areneEnnemiX,areneEnnemiY,areneEnnemiZ,areneEnnemiVx,areneEnnemiVy);
		areneAmiLocation = new Location(world, areneAmiX, areneAmiY, areneAmiZ, areneAmiVx, areneAmiVy);
	}
	
	public Location RecevoirLocation(Emplacement emplacement) {
		
		Location returnLocation;
		
		switch(emplacement) {
		case SPAWN:
			returnLocation = spawnLocation;
			break;
		case LOBBY_COMBAT:
			returnLocation = lobbyLocation;
			break;
		case ARENE_COMBAT:
			returnLocation = areneLocation;
			break;
		case ARENE_COMBAT_AMI:
			returnLocation = areneAmiLocation;
			break;
		case ARENE_COMBAT_ENNEMI:
			returnLocation = areneEnnemiLocation;
			break;
		default:
			returnLocation = spawnLocation;
			break;
		}
		
		return returnLocation;
	}
	
}
