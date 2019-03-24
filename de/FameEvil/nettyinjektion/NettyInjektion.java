package de.FameEvil.nettyinjektion;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import de.FameEvil.Methodes;
import de.FameEvil.main.Main;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.minecraft.server.v1_8_R3.Items;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayInBlockPlace;
import net.minecraft.server.v1_8_R3.PacketPlayInCustomPayload;
import net.minecraft.server.v1_8_R3.PacketPlayInSetCreativeSlot;
import net.minecraft.server.v1_8_R3.PacketPlayInWindowClick;

public class NettyInjektion {

	private Player player;
	private Channel channel;
	private String injectorName;

	public NettyInjektion(Player player, String injectorName) {
		this.injectorName = injectorName;
		this.player = player;
	}
	public void inject() {
		CraftPlayer player = (CraftPlayer) this.player;
		channel = player.getHandle().playerConnection.networkManager.channel;
		channel.pipeline().addAfter("decoder", "inject_dec_" + injectorName, new MessageToMessageDecoder() {
			@Override
			protected void decode(ChannelHandlerContext ctx, Object packet, List out) throws Exception {
				Packet<?> p = (Packet<?>) packet;
				if(!readPackets(NettyInjektion.this.player, ctx.channel(), p))
					out.add(packet);
			}
		});
	}
	public void uninject() {
		if (channel.pipeline().get("inject_dec_" + injectorName) != null) {
			channel.pipeline().remove("inject_dec_" + injectorName);
		}
	}
	public boolean readPackets(Player sender, Channel channel, Packet<?> packet) {
		try {
			Main.cfg = YamlConfiguration.loadConfiguration(Main.file);
			if(Main.cfg.getString("Detectmode").equalsIgnoreCase("aggressiv")) {

				if (packet instanceof PacketPlayInBlockPlace) {
					PacketPlayInBlockPlace pa = (PacketPlayInBlockPlace) packet;
					if (pa.getItemStack() != null && (pa.getItemStack().getItem() == Items.WRITTEN_BOOK || pa.getItemStack().getItem() == Items.WRITABLE_BOOK)) {
						Methodes.BookCrasher(sender, "Bücher geöffnet");
						channel.close();
						Methodes.BookOpenInfo(sender, packet);
						Main.Crashes += 1;
						return true;
					}
				}
				if (packet instanceof PacketPlayInSetCreativeSlot) {
					PacketPlayInSetCreativeSlot pa = (PacketPlayInSetCreativeSlot) packet;
					if (sender.getGameMode() != GameMode.CREATIVE) {
						Methodes.CreativCrasher(sender, "Wie kannst du dir im gm 0 Creativ items geben?");
						channel.close();
						Methodes.Gm0GiveInfo(sender, packet);
						Main.Crashes += 1;
						return true;
					} else {
						if (pa.getItemStack() != null && (pa.getItemStack().getItem() == Items.WRITTEN_BOOK || pa.getItemStack().getItem() == Items.WRITABLE_BOOK)) {
							Methodes.CreativCrasher(sender, "Bitte nicht zuviele Bücher geben!");
							channel.close();
							Methodes.Gm0GiveInfo(sender, packet);
							Main.Crashes += 1;
							return true;

						}

					}

				}
				if (packet instanceof PacketPlayInWindowClick) {

					PacketPlayInWindowClick pa = (PacketPlayInWindowClick) packet;
					if (pa.e() != null && (pa.e().getItem() == Items.WRITTEN_BOOK|| pa.e().getItem() == Items.WRITABLE_BOOK)) {
						Methodes.BookCrasher(sender, "Bücher anklicken");
						channel.close();
						Methodes.BookKlickInfo(sender, packet);
						Main.Crashes += 1;
						return true;
					}

				}

				if (packet instanceof PacketPlayInCustomPayload) {
					PacketPlayInCustomPayload pa = (PacketPlayInCustomPayload) packet;
					if (pa.b().writerIndex() > 32767) {
						return true;
					}
					if (pa.a().equals("MC|AdvCdm") && !sender.hasPermission("CommandBlock.use")
							&& !sender.isOp()) {
						Methodes.CMDBlock(sender, "CMDBlock");
						channel.close();
						Methodes.PayLoadInfo(sender, packet);
						Main.Crashes += 1;
						return true;

					}
					if (pa.a().equals("REGISTER") || pa.a().equals("UNREGISTER") || pa.a().equals("MC|BEdit")|| pa.a().equals("MC|BSign")) {
						Methodes.CustomPayload(sender);
						channel.close();
						Methodes.PayLoadInfo(sender, packet);
						Main.Crashes += 1;
						return true;

					}

					if (pa.a().equals("MC|ItemName")) {
						String ItemName = new String(pa.b().array());
						if (ItemName.length() > 41) {
							Methodes.CustomPayload(sender);
							channel.close();
							Methodes.PayLoadInfo(sender, packet);
							Main.Crashes += 1;
							return true;
						}

					}

				}
				
				
			}else{
				
			
			
			
			if (packet instanceof PacketPlayInBlockPlace) {
				PacketPlayInBlockPlace pa = (PacketPlayInBlockPlace) packet;
				if (pa.getItemStack() != null && (pa.getItemStack().getItem() == Items.WRITTEN_BOOK
						|| pa.getItemStack().getItem() == Items.WRITABLE_BOOK)) {
					if (Main.lastCrashPacket.get(sender) == null) {
						Main.lastCrashPacket.put(sender, System.currentTimeMillis());
						return false;
					} else {
						if (!((System.currentTimeMillis() - Main.lastCrashPacket.get(sender)) < 800)) {
							Main.lastCrashPacket.put(sender, System.currentTimeMillis());
							return false;
						}
					}
					Methodes.BookCrasher(sender, "Bücher geöffnet");
					channel.close();
					Methodes.BookOpenInfo(sender, packet);
					Main.Crashes += 1;
					return true;
				}
			}
			if (packet instanceof PacketPlayInSetCreativeSlot) {
				PacketPlayInSetCreativeSlot pa = (PacketPlayInSetCreativeSlot) packet;
				if (sender.getGameMode() != GameMode.CREATIVE) {
					if (Main.lastCrashPacket.get(sender) == null) {
						Main.lastCrashPacket.put(sender, System.currentTimeMillis());
						return false;
					} else {
						if (!((System.currentTimeMillis() - Main.lastCrashPacket.get(sender)) < 800)) {
							Main.lastCrashPacket.put(sender, System.currentTimeMillis());
							return false;
						}
					}
					Methodes.CreativCrasher(sender, "Wie kannst du dir im gm 0 Creativ items geben?");
					channel.close();
					Methodes.Gm0GiveInfo(sender, packet);
					Main.Crashes += 1;
					return true;
				} else {
					if (pa.getItemStack() != null && (pa.getItemStack().getItem() == Items.WRITTEN_BOOK
							|| pa.getItemStack().getItem() == Items.WRITABLE_BOOK)) {

						if (Main.lastCrashPacket.get(sender) == null) {
							Main.lastCrashPacket.put(sender, System.currentTimeMillis());
							return false;
						} else {
							if (!((System.currentTimeMillis() - Main.lastCrashPacket.get(sender)) < 800)) {
								Main.lastCrashPacket.put(sender, System.currentTimeMillis());
								return false;
							}
						}
						Methodes.CreativCrasher(sender, "Bitte nicht zuviele Bücher geben!");
						channel.close();
						Methodes.Gm0GiveInfo(sender, packet);
						Main.Crashes += 1;
						return true;

					}

				}

			}
			if (packet instanceof PacketPlayInWindowClick) {

				PacketPlayInWindowClick pa = (PacketPlayInWindowClick) packet;
				if (pa.e() != null && (pa.e().getItem() == Items.WRITTEN_BOOK
						|| pa.e().getItem() == Items.WRITABLE_BOOK)) {
					if (Main.lastCrashPacket.get(sender) == null) {
						Main.lastCrashPacket.put(sender, System.currentTimeMillis());
						return false;
					} else {
						if (!((System.currentTimeMillis() - Main.lastCrashPacket.get(sender)) < 800)) {
							Main.lastCrashPacket.put(sender, System.currentTimeMillis());
							return false;
						}
					}
					Methodes.BookCrasher(sender, "Bücher anklicken");
					channel.close();
					Methodes.BookKlickInfo(sender, packet);
					Main.Crashes += 1;
					return true;
				}

			}

			if (packet instanceof PacketPlayInCustomPayload) {
				PacketPlayInCustomPayload pa = (PacketPlayInCustomPayload) packet;
				if (pa.b().writerIndex() > 32767) {
					return true;
				}
				if (pa.a().equals("MC|AdvCdm") && !sender.hasPermission("CommandBlock.use")
						&& !sender.isOp()) {
					Methodes.CMDBlock(sender, "CMDBlock");
					channel.close();
					Methodes.PayLoadInfo(sender, packet);
					Main.Crashes += 1;
					return true;

				}
				if (pa.a().equals("REGISTER") || pa.a().equals("UNREGISTER") || pa.a().equals("MC|BEdit")
						|| pa.a().equals("MC|BSign")) {
					if (Main.lastCrashPacket.get(sender) == null) {
						Main.lastCrashPacket.put(sender, System.currentTimeMillis());
						return false;
					} else {
						if (!((System.currentTimeMillis() - Main.lastCrashPacket.get(sender)) < 800)) {
							Main.lastCrashPacket.put(sender, System.currentTimeMillis());
							return false;
						}
					}
					Methodes.CustomPayload(sender);
					channel.close();
					Methodes.PayLoadInfo(sender, packet);
					Main.Crashes += 1;
					return true;

				}

				if (pa.a().equals("MC|ItemName")) {
					String ItemName = new String(pa.b().array());
					if (ItemName.length() > 41) {
						Methodes.CustomPayload(sender);
						channel.close();
						Methodes.PayLoadInfo(sender, packet);
						Main.Crashes += 1;
						return true;
					}

				}

			}
			
		
			}
			
		} catch (Exception e) {
			if(Main.file.exists()) {

				Methodes.NothingCannotBeNull(sender, "Illegales Packet");
			}else {
				Main.CreateConfigFile(Main.file);
			}
			
			return true;

		}
		return false;
	}
}
