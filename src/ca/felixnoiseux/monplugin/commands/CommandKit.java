package ca.felixnoiseux.monplugin.commands;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import ca.felixnoiseux.monplugin.PlayerCustom;
import ca.felixnoiseux.monplugin.PlayerCustomList;

public class CommandKit implements CommandExecutor {

	private ArrayList<String> _lstKit = new ArrayList<String>() {{add("guerrier"); add("archer"); add("mage"); }};
	private PlayerCustomList _playerCustomList;
	private PlayerCustom _playerCustom;
	private Player _player;
	private String _kit;
	
	public CommandKit(PlayerCustomList playerCustomList){
		_playerCustomList = playerCustomList;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if(!(sender instanceof Player)) return false;
		
		_player = (Player)sender;
		_playerCustom = PlayerCustom();
		_kit = args[0].toLowerCase();
		
		EquiperJoueur();
		DonnerKit();
		
		return false;
	}
	
	private void EquiperJoueur() {
		_player.getInventory().clear();
		_player.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		_player.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		_player.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		_player.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		_player.updateInventory();
	}
	
	private void DonnerKit() {
		
		if(!_lstKit.contains(_kit)) {
			_player.sendMessage("|Kit Inexistant|-GUERRIER-ARCHER-MAGE-");
			return;
		}
		
		
		switch(_kit) {
		case "guerrier":
			_player.getInventory().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
			break;
		case "archer":
			_player.getInventory().setItemInMainHand(new ItemStack(Material.BOW));
			_player.getInventory().addItem(new ItemStack(Material.TIPPED_ARROW, 64));
			break;
		case "mage":
			_player.getInventory().addItem(new ItemStack(Material.SPLASH_POTION,16));
			_player.getInventory().addItem(new ItemStack(Material.SPLASH_POTION,16));
			break;
		}
		
		_player.updateInventory();
	}
	
	private PlayerCustom PlayerCustom() {

		for(int i=0; i < _playerCustomList.PlayersCustom().size(); i++) {
			if(_player == _playerCustomList.PlayersCustom().get(i).RecevoirPlayer()) {
				return _playerCustomList.PlayersCustom().get(i);
			}
		}
		return null;
	}

}
