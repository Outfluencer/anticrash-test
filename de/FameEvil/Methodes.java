package de.FameEvil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.FameEvil.main.Main;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;
import net.minecraft.server.v1_8_R3.PacketPlayOutKickDisconnect;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;

public class Methodes {
	
	
	
	public static void NothingCannotBeNull(Player p, String Grund) {
       	String MSG = "---";
			String line1 = "§5§lAntiCrasher §8× §c" + Grund;
			String line2 = "";
			String line3 = "§cDu wurdest vom §cNetzwerk gekickt";
			String line4 = "§4Grund§8: §cDu hast ein Unverständliches Packet gesendet";
			String line7 = "§7Bei §cFragen §7wende dich an den §cTeamSpeak §7Support!";
			String line8 = "\n";
			MSG = line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line7 + "\n" + line8;
			PacketPlayOutKickDisconnect paaa = new PacketPlayOutKickDisconnect(ChatSerializer.a("{text:\"" + MSG + "\"}"));
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(paaa);
       }
	
	   public static void BookCrasher(Player p, String Grund) {
       	String MSG = "---";
			String line1 = "§5§lAntiCrasher §8× §c" + Grund;
			String line2 = "";
			String line3 = "§cDu wurdest vom §cNetzwerk gekickt";
			String line4 = "§4Grund§8: §cBücher sind auf dem Server deaktiviert";
			String line7 = "§7Bei §cFragen §7wende dich an den §cTeamSpeak §7Support!";
			String line8 = "";
			MSG = line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line7 + "\n" + line8;
			PacketPlayOutKickDisconnect paaa = new PacketPlayOutKickDisconnect(ChatSerializer.a("{text:\"" + MSG + "\"}"));
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(paaa);
       }
	   
	   public static void CustomPayload(Player p) {
		   		String MSG = "---";
				String line1 = "§5§lAntiCrasher §8× §cCustomPayload";
				String line2 = "";
				String line3 = "§cDu wurdest vom §cNetzwerk gekickt";
				String line4 = "§4Grund§8: §cCustomPayload Crash verdacht";
				String line7 = "§7Bei §cFragen §7wende dich an den §cTeamSpeak §7Support!";
				String line8 = "";
				MSG = line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line7 + "\n" + line8;
				PacketPlayOutKickDisconnect paaa = new PacketPlayOutKickDisconnect(ChatSerializer.a("{text:\"" + MSG + "\"}"));
				((CraftPlayer) p).getHandle().playerConnection.sendPacket(paaa);
	       }
       
       public static void CreativCrasher(Player p, String Grund) {
       	String MSG = "---";
			String line1 = "§5§lAntiCrasher §8× §c" + Grund;
			String line2 = "";
			String line3 = "§cDu wurdest vom §cNetzwerk gekickt";
			String line4 = "§4Grund§8: §cHau den Client raus oder du wirst gebannt";
			String line7 = "§7Bei §cFragen §7wende dich an den §cTeamSpeak §7Support!";
			String line8 = "";
			MSG = line1 + "\n" + line2 + "\n" + line3 + "\n" + line4 + "\n" + line7 + "\n" + line8;
			PacketPlayOutKickDisconnect paaa = new PacketPlayOutKickDisconnect(ChatSerializer.a("{text:\"" + MSG + "\"}"));
			((CraftPlayer) p).getHandle().playerConnection.sendPacket(paaa);
       }
       
       public static void CMDBlock(Player p, String Grund) {
          	String MSG = "---";
   			String line1 = "§5§lCommandBlöcke sind nicht erlaubt";
   			String line2 = "";
   			String line3 = "§cDu wurdest vom §cNetzwerk gekickt";
   			String line7 = "§7Bei §cFragen §7wende dich an den §cTeamSpeak §7Support!";
   			String line8 = "";
   			MSG = line1 + "\n" + line2 + "\n" + line3 + "\n" + line7 + "\n" + line8;
   			PacketPlayOutKickDisconnect paaa = new PacketPlayOutKickDisconnect(ChatSerializer.a("{text:\"" + MSG + "\"}"));
   			((CraftPlayer) p).getHandle().playerConnection.sendPacket(paaa);
          }
       
       public static void BookOpenInfo(Player p, Packet<?> packet) {
       	PacketPlayInBlockPlace pa = (PacketPlayInBlockPlace)packet;
       	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
       	for(Player player : Bukkit.getOnlinePlayers()) {
       		if(player.hasPermission("*")||player.hasPermission("AntiCrasherInfo")||player.isOp()){
       			
       			player.sendMessage(Main.Prefix + "§7Der Spieler §c" + p.getName() + "§7 wollte einen §dExploit nutzen");
       			player.sendMessage(Main.Prefix + "§dBook open §8➜ §7" + pa.getItemStack().getItem().getName());
       			player.sendMessage(Main.Prefix + "§dIP Adresse §8➜ §7" + p.getAddress().getAddress().getHostAddress());
       			player.sendMessage(Main.Prefix + "§dUUID §8➜ §7" + p.getUniqueId());
       			player.sendMessage(Main.Prefix + "§dUhrzeit §8➜ §7" + format.format(new Date()));

       		}
       	}
       }
       
       public static void BookKlickInfo(Player p, Packet<?> packet) {
          	PacketPlayInWindowClick pa = (PacketPlayInWindowClick)packet;
          	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
   			format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
          	for(Player player : Bukkit.getOnlinePlayers()) {
          		if(player.hasPermission("*")||player.hasPermission("AntiCrasherInfo")||player.isOp()){
          			
          			player.sendMessage(Main.Prefix + "§7Der Spieler §c" + p.getName() + "§7 wollte einen §dExploit nutzen");
          			player.sendMessage(Main.Prefix + "§dBook click §8➜ §7" + pa.e().getItem().getName());
          			player.sendMessage(Main.Prefix + "§dIP Adresse §8➜ §7" + p.getAddress().getAddress().getHostAddress());
          			player.sendMessage(Main.Prefix + "§dUUID §8➜ §7" + p.getUniqueId());
          			player.sendMessage(Main.Prefix + "§dUhrzeit §8➜ §7" + format.format(new Date()));

          		}
          	}
          }
       
       public static void Gm0GiveInfo(Player p, Packet<?> packet) {
       	PacketPlayInSetCreativeSlot pa = (PacketPlayInSetCreativeSlot)packet;
       	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
			format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
       	for(Player player : Bukkit.getOnlinePlayers()) {
       		if(player.hasPermission("*")||player.hasPermission("AntiCrasherInfo")||player.isOp()){
       			
       			player.sendMessage(Main.Prefix + "Der Spieler §c" + p.getName() + "§7 wollte einen §dExploit nutzen");
       			player.sendMessage(Main.Prefix + "§dBook give §8➜ §7" + pa.getItemStack().getItem().getName());
       			player.sendMessage(Main.Prefix + "§dIP Adresse §8➜ §7" + p.getAddress().getAddress().getHostAddress());
       			player.sendMessage(Main.Prefix + "§dUUID §8➜ §7" + p.getUniqueId());
       			player.sendMessage(Main.Prefix + "§dUhrzeit §8➜ §7" + format.format(new Date()));

       		}
       	}
       }
       
       public static void PayLoadInfo(Player p, Packet<?> packet) {
          	PacketPlayInCustomPayload pa = (PacketPlayInCustomPayload)packet;
          	SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
   			format.setTimeZone(TimeZone.getTimeZone("Europe/Berlin"));
          	for(Player player : Bukkit.getOnlinePlayers()) {
          		if(player.hasPermission("*")||player.hasPermission("AntiCrasherInfo")||player.isOp()){
          			
          			player.sendMessage(Main.Prefix + "§7Der Spieler §c" + p.getName() + "§7 wollte einen §dExploit nutzen");
          			player.sendMessage(Main.Prefix + "§dCustomPayload §8➜ §7" + pa.a());
          			player.sendMessage(Main.Prefix + "§dIP Adresse §8➜ §7" + p.getAddress().getAddress().getHostAddress());
          			player.sendMessage(Main.Prefix + "§dUUID §8➜ §7" + p.getUniqueId());
          			player.sendMessage(Main.Prefix + "§dUhrzeit §8➜ §7" + format.format(new Date()));

          		}
          	}
          }
}
