package ca.felixnoiseux.monplugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PluginListeners implements Listener  {

	PlayerCustomList _playerCustomList;
	EmplacementLocation _emplacementLocation;
	
	public PluginListeners(PlayerCustomList playerList) {
		_playerCustomList = playerList;
		_emplacementLocation = new EmplacementLocation();
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		PlayerCustom playerCustom = new PlayerCustom(player, _emplacementLocation);
		
		_playerCustomList.InsertPlayer(playerCustom);
		
		Bukkit.broadcastMessage("["+ player.getName() +"] §9 Vien de joindre la partie !");

	}
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        _playerCustomList.RemovePlayer(player);
        Bukkit.broadcastMessage("["+ player.getName() +"] §9 Quitte la partie !");
    }
	
	@EventHandler
    public void onEntityDamgeByEntityEvent(EntityDamageByEntityEvent event){
        if(event.getDamager() instanceof Player && event.getEntity() instanceof Player){
            Player dead = (Player) event.getEntity();
            Player killer = (Player) event.getDamager();
            
            PlayerCustom deadCustom = null;
            PlayerCustom killerCustom = null;
            
            //Recevoir player Custom
            for(int i = 0; i < _playerCustomList.PlayersCustom().size(); i++) {
            	if(_playerCustomList.PlayersCustom().get(i).RecevoirPlayer() == dead) {
            		deadCustom = _playerCustomList.PlayersCustom().get(i);
            	}
            	else if(_playerCustomList.PlayersCustom().get(i).RecevoirPlayer() == killer) {
            		killerCustom = _playerCustomList.PlayersCustom().get(i);
            	}
            }
            
            
            
            if(dead.isDead()){
                deadCustom.RecevoirPlayer().sendMessage(killer.getName() + " vous a kill ! ");
                deadCustom.modifierEmplacementPlayer(Emplacement.SPAWN);
                killerCustom.RecevoirPlayer().sendMessage("Felicitation vous avez kill : " + dead.getName());
            }
        }
    }
}
