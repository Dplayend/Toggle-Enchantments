package com.dplayend.togenc.network.packet;

import com.dplayend.togenc.client.implement.InterfacePlayerInventory;
import com.dplayend.togenc.handler.HandlerConfig;
import com.dplayend.togenc.network.ServerNetworking;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketUpdateEnchants {
    public PacketUpdateEnchants() {}

    public PacketUpdateEnchants(FriendlyByteBuf buf) {
    }

    public void toBytes(FriendlyByteBuf buf) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                for (int inv = 0; inv < 36; inv++) {
                    if (((InterfacePlayerInventory)player).getInv().getItem(inv).getEnchantmentTags().size() > 0) {
                        ItemStack stack = ((InterfacePlayerInventory)player).getInv().getItem(inv);
                        ListTag enchantList = stack.getEnchantmentTags();
                        int getEnchantList = 0;
                        String enchantName = "";

                        if (stack.getCount() > 0) {
                            for (int i = 0; i < enchantList.size(); i++) {
                                if (stack.getItem() instanceof ArmorItem) {
                                    ArmorItem armorItem = (ArmorItem)stack.getItem();
                                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.HEAD)) enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_helmet.get());
                                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.CHEST)) enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_chestplate.get());
                                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.LEGS)) enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_leggings.get());
                                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.FEET)) enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_boots.get());
                                } else {
                                    enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_hand.get());
                                }
                                if (enchantList.getCompound(i).getString("id").split(":")[1].equals(enchantName)) {
                                    getEnchantList = i;
                                    break;
                                }
                            }

                            for (int i = 0; i < enchantList.size(); i++) {
                                if (player.containerMenu instanceof InventoryMenu) {
                                    if (enchantList.getCompound(i).getInt("enchantLvl") == -1 && enchantList.getCompound(getEnchantList).getInt("lvl") != -1) {
                                        enchantList.getCompound(getEnchantList).putShort("lvl", (short)-1);
                                    }
                                } else {
                                    if (enchantList.getCompound(i).contains("defaultLvl")) {
                                        enchantList.getCompound(getEnchantList).putShort("lvl", (short)enchantList.getCompound(i).getInt("defaultLvl"));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
        context.setPacketHandled(true);
    }
}
