package ca.felixnoiseux.monplugin;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class PluginListeners implements Listener {

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

		Bukkit.broadcastMessage("[" + player.getName() + "] §9 Vien de joindre la partie !");

	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		_playerCustomList.RemovePlayer(player);
		Bukkit.broadcastMessage("[" + player.getName() + "] §9 Quitte la partie !");
	}

	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent event) {

	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();
		Action action = event.getAction();

		if (event.getClickedBlock() != null && action == Action.RIGHT_CLICK_BLOCK) {

			BlockState bs = event.getClickedBlock().getState();

			if (bs instanceof Sign) {
				Sign sign = (Sign) bs;
				Bukkit.broadcastMessage(sign.getLine(1).toLowerCase());
				switch (sign.getLine(1).toLowerCase()) {
				case "archer":
					player.getInventory().clear();
					player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
					player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
					player.getInventory().setItemInMainHand(new ItemStack(Material.BOW));
					player.getInventory().addItem(new ItemStack(Material.TIPPED_ARROW, 64));
					break;
				case "guerrier":
					player.getInventory().clear();
					player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
					player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
					player.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
					break;
				case "mage":
					player.getInventory().clear();
					player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
					player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
					player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
					player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
					player.getInventory().addItem(new ItemStack(Material.SPLASH_POTION, 16));
					player.getInventory().addItem(new ItemStack(Material.SPLASH_POTION, 16));
					break;
				}
				player.updateInventory();
			}

		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {

		if (event.getEntity().getKiller() instanceof Player) {

			Player dead = event.getEntity();
			Player killer = event.getEntity().getKiller();

			PlayerCustom deadCustom = null;
			PlayerCustom killerCustom = null;

			// Recevoir player Custom
			for (int i = 0; i < _playerCustomList.PlayersCustom().size(); i++) {
				if (_playerCustomList.PlayersCustom().get(i).RecevoirPlayer() == dead) {
					deadCustom = _playerCustomList.PlayersCustom().get(i);
				} else if (_playerCustomList.PlayersCustom().get(i).RecevoirPlayer() == killer) {
					killerCustom = _playerCustomList.PlayersCustom().get(i);
				}
			}

			// COMBAT ARENE
			if (killerCustom.estDansAreneCombat() && deadCustom.estDansAreneCombat()) {
				Bukkit.broadcastMessage("§a=====ARENE COMBAT======");
				Bukkit.broadcastMessage("§a=====GAGNANT :" + killer.getName() + "======");
				Bukkit.broadcastMessage("§a=====PERDANT :" + dead.getName() + "======");

				killer.sendMessage("Gagnant du combat, /spawn pour retourner au spawn");
			}

			deadCustom.modifierEmplacementPlayer(Emplacement.SPAWN);
		}
	}
}
