package com.dplayend.togenc.network.packet;

import com.dplayend.togenc.handler.HandlerConfig;
import com.dplayend.togenc.network.ServerNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketLeggingsEnchantments {
    public PacketLeggingsEnchantments() {
    }

    public PacketLeggingsEnchantments(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                Iterable<ItemStack> iterator = player.getArmorSlots();
                iterator.forEach(itemStack -> {
                    if (itemStack.getItem() instanceof ArmorItem) {
                        ArmorItem armorItem = (ArmorItem) itemStack.getItem();
                        if (armorItem.getEquipmentSlot().equals(EquipmentSlot.LEGS)) {
                            ServerNetworking.toggleEnchantmentNetwork(player, itemStack, HandlerConfig.toggle_leggings.get());
                        }
                    }
                });
            }
        });
        context.setPacketHandled(true);
    }
}
