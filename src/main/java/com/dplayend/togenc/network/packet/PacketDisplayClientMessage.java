package com.dplayend.togenc.network.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraftforge.network.NetworkEvent;

import java.awt.*;
import java.util.function.Supplier;

public class PacketDisplayClientMessage {
    private final CompoundTag enchantList;
    private final String enchantColor;
    private final String enchantName;
    private final String toggle;

    public PacketDisplayClientMessage(CompoundTag enchantList, String enchantColor, String enchantName, String toggle) {
        this.enchantList = enchantList;
        this.enchantColor = enchantColor;
        this.enchantName = enchantName;
        this.toggle = toggle;
    }

    public PacketDisplayClientMessage(FriendlyByteBuf buf) {
        this.enchantList = buf.readNbt();
        this.enchantColor = buf.readUtf();
        this.enchantName = buf.readUtf();
        this.toggle = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeNbt(enchantList);
        buf.writeUtf(enchantColor);
        buf.writeUtf(enchantName);
        buf.writeUtf(toggle);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            LocalPlayer localPlayer = Minecraft.getInstance().player;
            assert localPlayer != null;
            localPlayer.displayClientMessage(Component.translatable("enchantment."+ enchantList.getString("id").split(":")[0] + "." + enchantName).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.decode(enchantColor).getRGB()))).append(Component.translatable("text.togenc.tooltip." + toggle)), true);
        });
        context.setPacketHandled(true);
    }
}
