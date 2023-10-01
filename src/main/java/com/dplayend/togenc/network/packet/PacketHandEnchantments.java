package com.dplayend.togenc.network.packet;

import com.dplayend.togenc.handler.HandlerConfig;
import com.dplayend.togenc.network.ServerNetworking;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class PacketHandEnchantments {
    public PacketHandEnchantments() {
    }

    public PacketHandEnchantments(FriendlyByteBuf buffer) {
    }

    public void toBytes(FriendlyByteBuf buffer) {
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer player = context.getSender();
            if (player != null) {
                ItemStack stack = player.getMainHandItem();
                ServerNetworking.toggleEnchantmentNetwork(player, stack, HandlerConfig.toggle_hand.get());
            }
        });
        context.setPacketHandled(true);
    }
}
