package de.FameEvil.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import de.FameEvil.main.Main;

public class AntiCrashCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender cs, Command command, String label, String[] args) {
		cs.sendMessage(Main.Prefix + "Seit dem letzten §creload §7hat §d" + Main.Crashes + "§7 mal jemand versucht zu §ccrashen§8");
		cs.sendMessage(Main.Prefix + "Made by §cFameEvil§8(§cFabian§8) §5YT§8: §cFameEvil");
		if (cs.isOp()) {
			cs.sendMessage(Main.Prefix + "/AntiCrasher rl");
			cs.sendMessage(Main.Prefix + "/AntiCrasher setModeAggressiv");
			cs.sendMessage(Main.Prefix + "/AntiCrasher setModeNormal");
			if (args.length == 1) {
				if (args[0].equalsIgnoreCase("rl") || args[0].equalsIgnoreCase("reload")) {
					cs.sendMessage(Main.Prefix + "Reload the AntiCrasher");
					reloadPlugin();
					return false;

				}else if(args[0].equalsIgnoreCase("setModeAggressiv")){
					Main.setAggres();
					cs.sendMessage(Main.Prefix + "now in aggressiv mode!");
					reloadPlugin();
					
				}else if(args[0].equalsIgnoreCase("setModeNormal")){
					Main.setNorm();
					cs.sendMessage(Main.Prefix + "now in normal mode!");
					reloadPlugin();
					
				}else {
					
				}
			}
		}
		return false;
	}

	public static void reloadPlugin() {
		Bukkit.getPluginManager().disablePlugin(Main.instance);
		Bukkit.getPluginManager().enablePlugin(Main.instance);
	}

}
