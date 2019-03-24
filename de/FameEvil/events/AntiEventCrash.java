package de.FameEvil.events;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

import de.FameEvil.main.Main;

import org.bukkit.event.player.PlayerPreLoginEvent;




public class AntiEventCrash implements Listener{


	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onRed(BlockRedstoneEvent e) {
		int red = Main.RedStone.get("red");
		int red1 = Main.RedStone.get("red") + 1;
		Main.RedStone.put("red", red1);
		if(Main.RedStone.get("red") > 2000) {
			e.setNewCurrent(0);
			Main.Crashes+=1;

			
		}
	} 
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onSignChange(final SignChangeEvent event) {
		if(event.getLine(0).length() > 49||event.getLine(1).length() > 49||event.getLine(2).length() > 49||event.getLine(3).length() > 49) {
			event.setCancelled(true);
			event.setLine(0, "§cUnterlass");
            event.setLine(1, "§cdas");
            event.setLine(2, "§cdbitte");
            event.setLine(3, "§cby FameEvil");
         	event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.VILLAGER_NO, 10, 1);
         	Main.Crashes+=1;
		}

	
		
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
	public void onChat(AsyncPlayerChatEvent e) {
		if(e.getMessage().equalsIgnoreCase("#AntiCrasher") || e.getMessage().equalsIgnoreCase("#AC") || e.getMessage().equalsIgnoreCase("#AntiCrash")|| e.getMessage().equalsIgnoreCase("#FameEvilArmy")) {
			e.setCancelled(true);
			e.getPlayer().sendMessage(Main.Prefix + "Seit dem letzten §creload §7hat §d" + Main.Crashes + "§7 mal jemand versucht zu §ccrashen§8");
			e.getPlayer().sendMessage(Main.Prefix + "Made by §cFameEvil§8(§cFabian§8) §5YT§8: §cFameEvil");
		}
	}
}
