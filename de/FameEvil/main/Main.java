package de.FameEvil.main;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import de.FameEvil.Methodes;
import de.FameEvil.command.AntiCrashCommand;
import de.FameEvil.events.AntiEventCrash;
import de.FameEvil.nettyinjektion.NettyInjektion;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInFlying;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;

public class Main extends JavaPlugin implements Listener {
	public static String Prefix = "§5§lAntiCrasher§r §8┃ §7";
	public static HashMap<String, Integer> RedStone = new HashMap<String, Integer>();
	public static HashMap<Player, NettyInjektion> Injektions = new HashMap<Player, NettyInjektion>();
	public static int Crashes = 0;
	public static HashMap<Player, Long> lastCrashPacket = new HashMap<Player, Long>();
	public static File file = new File("plugins/AntiCrasher", "config.yml");
	public static YamlConfiguration cfg;
	public static Main instance;

	@Override
	public void onEnable() {
		instance = this;
		
		CreateConfigFile(file);
		
		
		Bukkit.getPluginManager().registerEvents(this, this);
		Bukkit.getPluginManager().registerEvents(new AntiEventCrash(), this);
		getCommand("AntiCrasher").setExecutor(new AntiCrashCommand());
		RedStone.put("red", 0);
		int Scetul = Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, new Runnable() {
			public void run() {
				RedStone.put("red", 0);
			}
		}, 0, 20);
		for (Player p : Bukkit.getOnlinePlayers()) {

			NettyInjektion pr = new NettyInjektion(p, p.getName()) {
			};
			pr.inject();
			Injektions.put(p, pr);
		}
	}

	@Override
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			lastCrashPacket.remove(p);
			Injektions.get(p).uninject();
			Injektions.remove(p);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent ev) {
		lastCrashPacket.remove(ev.getPlayer());
		Injektions.get(ev.getPlayer()).uninject();
		Injektions.remove(ev.getPlayer());
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		NettyInjektion pr = new NettyInjektion(event.getPlayer(), event.getPlayer().getName()) {};
		pr.inject();
		Injektions.put(event.getPlayer(), pr);
	}

	public static void CreateConfigFile(File file) {
		if (!file.exists()) {
			new File(file.getParent()).mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
				cfg = YamlConfiguration.loadConfiguration(file);
				cfg.set("Detectmode", "normal");
				try {cfg.save(file);} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	public static void setAggres() {
		cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Detectmode", "aggressiv");
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void setNorm() {
		cfg = YamlConfiguration.loadConfiguration(file);
		cfg.set("Detectmode", "normal");
		try {
			cfg.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}