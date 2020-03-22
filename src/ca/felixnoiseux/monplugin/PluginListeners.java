package ca.felixnoiseux.monplugin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PluginListeners implements Listener  {

	PlayersList _playersList;
	
	public PluginListeners(PlayersList playerList) {
		_playersList = playerList;
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		_playersList.InsertPlayer(player);
		Bukkit.broadcastMessage("["+ player.getName() +"] §9 Vien de joindre la partie !");

	}
	
	@EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        _playersList.RemovePlayer(player);
        Bukkit.broadcastMessage("["+ player.getName() +"] §9 Quitte la partie !");
    }
}
