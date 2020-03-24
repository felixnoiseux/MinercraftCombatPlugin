package ca.felixnoiseux.monplugin.commands;

import java.util.concurrent.TimeUnit;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import ca.felixnoiseux.monplugin.Emplacement;
import ca.felixnoiseux.monplugin.LobbyCombat;
import ca.felixnoiseux.monplugin.PlayerCustom;
import ca.felixnoiseux.monplugin.PlayerCustomList;

public class CommandCombat implements CommandExecutor {

	private PlayerCustomList _playerCustomList;
	private LobbyCombat _lobby;
	private Player _player;
	private Player _ennemi;
	private PlayerCustom _playerCustom;
	private PlayerCustom _ennemiCustom;

	public CommandCombat(PlayerCustomList playerCustomList) {
		_playerCustomList = playerCustomList;
		_lobby = new LobbyCombat();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String msg, String[] args) {

		if (!(sender instanceof Player) || !(cmd.getName().equalsIgnoreCase("combat")))
			return false;

		_player = (Player) sender;
		_playerCustom = RecevoirPlayerCustom(_player.getName());

		if (args.length >= 1)
			TraiterRequestCombatEtAccept(args);
		else
			TraiterCombatAleatoire();

		return true;
	}

	private void TraiterRequestCombatEtAccept(String[] args) {

		String arg = args[0];

		if (args.length == 0 || args.length > 1) {
			_player.sendMessage("Trop ou aucun paramètre saisi.");
			return;
		}

		// Request
		if (!(arg.equalsIgnoreCase("accept"))) {

			if (!(VerifierJoueurExistant(arg))) {
				_player.sendMessage("Ennemi hors ligne");
				return;
			}

			_ennemiCustom = RecevoirPlayerCustom(arg);
			_ennemi = _ennemiCustom.RecevoirPlayer();
			RequestCombatEnnemi();
			return;
		}

		// Accept
		if (!(_playerCustom.estRequestPourCombat())) {
			_player.sendMessage("Personne ne veut vous defier presentement.");
			return;
		}

		AccepterCombatEnnemi();

	}

	private void TraiterCombatAleatoire() {
		try {

			_player.sendMessage("Teleportation lobby du combat");
			for (int i = 3; i > 0; i--) {
				_player.sendMessage("§a===" + Integer.toString(i) + "===");
				TimeUnit.SECONDS.sleep(1);
			}

			_lobby.JoinLobby(_playerCustom);
			_playerCustom.modifierEmplacementPlayer(Emplacement.LOBBY_COMBAT);

			if (_lobby.EstComplet()) {
				// Teleportation vers le combat dans 3 seconde
				PlayerCustom p1 = _lobby.Players().get(0);
				PlayerCustom p2 = _lobby.Players().get(1);

				_playerCustom.RecevoirPlayer().sendMessage("Le combat debute dans ");
				for (int i = 3; i > 0; i--) {
					_player.sendMessage("§a===" + Integer.toString(i) + "===");
					TimeUnit.SECONDS.sleep(1);
				}

				p1.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_ENNEMI);
				p2.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_AMI);

				_playerCustom.RecevoirPlayer().sendMessage("Maintenant !");
				_lobby.ViderLobby();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
			return;
		}
	}

	private PlayerCustom RecevoirPlayerCustom(String nomJoueur) {

		for (int i = 0; i < _playerCustomList.PlayersCustom().size(); i++) {
			if (nomJoueur.equals(_playerCustomList.PlayersCustom().get(i).RecevoirPlayer().getName())) {
				return _playerCustomList.PlayersCustom().get(i);
			}
		}
		return null;
	}

	private boolean VerifierJoueurExistant(String nomJoueur) {
		for (int i = 0; i < _playerCustomList.PlayersCustom().size(); i++) {

			Player p = _playerCustomList.PlayersCustom().get(i).RecevoirPlayer();
			if (p.getName().equals(nomJoueur)) {
				return true;
			}
		}
		return false;
	}

	private void AccepterCombatEnnemi() {
		try {

			_ennemi = _playerCustom.ennemiRequestPourCombat().RecevoirPlayer();
			_player.sendMessage("Vous avez accepte le combat ! | Teleportation arene");
			_ennemi.sendMessage("Votre combat est accepte ! | Teleportation arene");

			for (int i = 3; i > 0; i--) {
				_player.sendMessage("§a===" + Integer.toString(i) + "===");
				_ennemi.sendMessage("§a===" + Integer.toString(i) + "===");
				TimeUnit.SECONDS.sleep(1);
			}

			_playerCustom.modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_AMI);
			_playerCustom.ennemiRequestPourCombat().modifierEmplacementPlayer(Emplacement.ARENE_COMBAT_ENNEMI);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void RequestCombatEnnemi() {
		_ennemi.sendMessage(_playerCustom.RecevoirPlayer().getName() + " veux vous combattre. ");
		_ennemi.sendMessage("§c[/combat accept] §epour vous combattre. ");
		_ennemiCustom.definirRequestCombat(true, _playerCustom);

	}

}
