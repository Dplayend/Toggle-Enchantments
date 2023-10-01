package com.dplayend.togenc.mixin;

import com.dplayend.togenc.handler.HandlerConfig;
import com.dplayend.togenc.network.ServerNetworking;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;
import java.util.List;

@Mixin({Item.class})
public class MixItem {
    @Inject(method = "appendHoverText", at = @At("HEAD"))
    private void appendHoverText(ItemStack stack, Level level, List<Component> list, TooltipFlag flag, CallbackInfo info) {
        ListTag enchantList = stack.getEnchantmentTags();
        String enchantName = "";
        String enchantColor = "FFFFFF";

        if (HandlerConfig.tooltip.get()) {
            for (int i = 0; i < enchantList.size(); i++) {
                if (stack.getItem() instanceof ArmorItem) {
                    ArmorItem armorItem = (ArmorItem)stack.getItem();
                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.HEAD)) {
                        enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_helmet.get());
                        enchantColor = ServerNetworking.updateEnchantColor(enchantList, i, HandlerConfig.toggle_helmet.get());
                    }
                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.CHEST)) {
                        enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_chestplate.get());
                        enchantColor = ServerNetworking.updateEnchantColor(enchantList, i, HandlerConfig.toggle_chestplate.get());
                    }
                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.LEGS)) {
                        enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_leggings.get());
                        enchantColor = ServerNetworking.updateEnchantColor(enchantList, i, HandlerConfig.toggle_leggings.get());
                    }
                    if (armorItem.getEquipmentSlot().equals(EquipmentSlot.FEET)) {
                        enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_boots.get());
                        enchantColor = ServerNetworking.updateEnchantColor(enchantList, i, HandlerConfig.toggle_boots.get());
                    }
                } else {
                    enchantName = ServerNetworking.updateEnchantName(enchantList, i, HandlerConfig.toggle_hand.get());
                    enchantColor = ServerNetworking.updateEnchantColor(enchantList, i, HandlerConfig.toggle_hand.get());
                }
                if (enchantList.getCompound(i).getString("id").split(":")[1].equals(enchantName)) break;
            }

            for (int i = 0; i < enchantList.size(); i++) {
                if (enchantList.getCompound(i).getString("id").split(":")[1].equals(enchantName)) {
                    int getEnchantLevel = enchantList.getCompound(i).getInt("enchantLvl");
                    if (getEnchantLevel > 0) {
                        list.add(Component.translatable("enchantment." + enchantList.getCompound(i).getString("id").split(":")[0] + "." + enchantName).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.decode(enchantColor).getRGB()))).append(Component.translatable("text.togenc.tooltip.enabled")));
                    } else {
                        if (enchantList.getCompound(i).contains("defaultLvl")) {
                            list.add(Component.translatable("enchantment." + enchantList.getCompound(i).getString("id").split(":")[0] + "." + enchantName).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.decode(enchantColor).getRGB()))).append(Component.translatable("text.togenc.tooltip.disabled")));
                        } else {
                            list.add(Component.translatable("enchantment." + enchantList.getCompound(i).getString("id").split(":")[0] + "." + enchantName).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(Color.decode(enchantColor).getRGB()))).append(Component.translatable("text.togenc.tooltip.enabled")));
                        }
                    }
                }
            }
        }
    }
}
