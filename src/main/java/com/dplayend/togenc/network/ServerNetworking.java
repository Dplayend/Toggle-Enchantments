package com.dplayend.togenc.network;

import com.dplayend.togenc.ToggleEnchantments;
import com.dplayend.togenc.network.packet.*;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.List;

public class ServerNetworking {
    private static int packetId = 0;
    private static int id() {
        return packetId++;
    }
    public static SimpleChannel instance;

    public static void init() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder.named(new ResourceLocation(ToggleEnchantments.MOD_ID, "network")).networkProtocolVersion(() -> "1.0").clientAcceptedVersions(s -> true).serverAcceptedVersions(s -> true).simpleChannel();
        instance = net;

        net.messageBuilder(PacketUpdateEnchants.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketUpdateEnchants::new).encoder(PacketUpdateEnchants::toBytes).consumerMainThread(PacketUpdateEnchants::handle).add();
        net.messageBuilder(PacketHandEnchantments.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketHandEnchantments::new).encoder(PacketHandEnchantments::toBytes).consumerMainThread(PacketHandEnchantments::handle).add();
        net.messageBuilder(PacketHelmetEnchantments.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketHelmetEnchantments::new).encoder(PacketHelmetEnchantments::toBytes).consumerMainThread(PacketHelmetEnchantments::handle).add();
        net.messageBuilder(PacketChestplateEnchantments.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketChestplateEnchantments::new).encoder(PacketChestplateEnchantments::toBytes).consumerMainThread(PacketChestplateEnchantments::handle).add();
        net.messageBuilder(PacketLeggingsEnchantments.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketLeggingsEnchantments::new).encoder(PacketLeggingsEnchantments::toBytes).consumerMainThread(PacketLeggingsEnchantments::handle).add();
        net.messageBuilder(PacketBootsEnchantments.class, id(), NetworkDirection.PLAY_TO_SERVER).decoder(PacketBootsEnchantments::new).encoder(PacketBootsEnchantments::toBytes).consumerMainThread(PacketBootsEnchantments::handle).add();
        net.messageBuilder(PacketDisplayClientMessage.class, id(), NetworkDirection.PLAY_TO_CLIENT).decoder(PacketDisplayClientMessage::new).encoder(PacketDisplayClientMessage::toBytes).consumerMainThread(PacketDisplayClientMessage::handle).add();
    }

    public static <MSG> void sendToServer(MSG message) {
        instance.sendToServer(message);
    }

    public static <MSG> void sendToPlayer(MSG message, ServerPlayer player) {
        instance.send(PacketDistributor.PLAYER.with(() -> player), message);
    }

    public static String updateEnchantName(ListTag enchantList, int id, List<? extends String> toggleList) {
        String enchantName = "";
        for (String s : toggleList) {
            String getEnchant = enchantList.getCompound(id).getString("id").split(":")[1];
            if (getEnchant.equals(s.split(";")[0])) {
                enchantName = getEnchant;
                break;
            }
        }
        return enchantName;
    }

    public static String updateEnchantColor(ListTag enchantList, int id, List<? extends String> toggleList) {
        String enchantColor = "";
        for (String s : toggleList) {
            String getEnchant = enchantList.getCompound(id).getString("id").split(":")[1];
            if (getEnchant.equals(s.split(";")[0])) {
                enchantColor = s.split(";")[1];
                break;
            }
        }
        return enchantColor;
    }

    public static void toggleEnchantmentNetwork(ServerPlayer player, ItemStack itemStack, List<? extends String> toggleList) {
        if (player != null) {
            ListTag enchantList = itemStack.getEnchantmentTags();
            int getEnchantList = 0;
            String enchantName = "";
            String enchantColor = "FFFFFF";

            if (itemStack.getCount() > 0) {
                for (int i = 0; i < enchantList.size(); i++) {
                    for (String s : toggleList) {
                        String getEnchant = enchantList.getCompound(i).getString("id").split(":")[1];
                        if (getEnchant.equals(s.split(";")[0])) {
                            enchantName = getEnchant;
                            enchantColor = s.split(";")[1];
                            break;
                        }
                    }
                    if (enchantList.getCompound(i).getString("id").split(":")[1].equals(enchantName)) {
                        getEnchantList = i;
                        break;
                    }
                }

                if (!enchantName.equals("")) {
                    for (int i = 0; i < enchantList.size(); i++) {
                        if (enchantList.getCompound(i).getString("id").split(":")[1].equals(enchantName)) {
                            int getEnchantLevel = enchantList.getCompound(getEnchantList).getInt("lvl");
                            if (!enchantList.getCompound(i).contains("defaultLvl")) {
                                enchantList.getCompound(i).putInt("defaultLvl", getEnchantLevel);
                                ServerNetworking.sendToPlayer(new PacketDisplayClientMessage(enchantList.getCompound(i), enchantColor, enchantName, "disabled"), player);
                                enchantList.getCompound(getEnchantList).putShort("lvl", (short)-1);
                                enchantList.getCompound(i).putInt("enchantLvl", -1);
                            } else {
                                if (getEnchantLevel <= 0) {
                                    ServerNetworking.sendToPlayer(new PacketDisplayClientMessage(enchantList.getCompound(i), enchantColor, enchantName, "enabled"), player);
                                    EnchantmentHelper.setEnchantmentLevel(enchantList.getCompound(getEnchantList), enchantList.getCompound(i).getInt("defaultLvl"));
                                    enchantList.getCompound(i).putInt("enchantLvl", enchantList.getCompound(i).getInt("defaultLvl"));
                                } else {
                                    ServerNetworking.sendToPlayer(new PacketDisplayClientMessage(enchantList.getCompound(i), enchantColor, enchantName, "disabled"), player);
                                    enchantList.getCompound(getEnchantList).putShort("lvl", (short)-1);
                                    enchantList.getCompound(i).putInt("enchantLvl", -1);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
