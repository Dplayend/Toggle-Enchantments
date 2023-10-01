package com.dplayend.togenc.handler;

import com.dplayend.togenc.ToggleEnchantments;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class HandlerClothConfig {
    public static Screen getConfigScreen(Screen parent, boolean isTransparent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.title"));
        builder.setDefaultBackgroundTexture(new ResourceLocation("minecraft:textures/block/dirt.png"));
        ConfigCategory general = builder.getOrCreateCategory(Component.literal("general"));
        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();

        general.addEntry(
                configEntryBuilder
                        .startBooleanToggle(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.tooltip"), HandlerConfig.tooltip.get())
                        .setDefaultValue(true)
                        .setSaveConsumer(HandlerConfig.tooltip::set)
                        .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.tooltip.tooltip"))
                        .build()
        );

        general.addEntry(configEntryBuilder
                .startStrList(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_hand"), (List)HandlerConfig.toggle_hand.get())
                .setDefaultValue((List)HandlerConfig.toggle_hand.getDefault())
                .setSaveConsumer(HandlerConfig.toggle_hand::set)
                .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_hand.tooltip"))
                .build()
        );

        general.addEntry(configEntryBuilder
                .startStrList(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_helmet"), (List)HandlerConfig.toggle_helmet.get())
                .setDefaultValue((List)HandlerConfig.toggle_helmet.getDefault())
                .setSaveConsumer(HandlerConfig.toggle_helmet::set)
                .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_helmet.tooltip"))
                .build()
        );

        general.addEntry(configEntryBuilder
                .startStrList(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_chestplate"), (List)HandlerConfig.toggle_chestplate.get())
                .setDefaultValue((List)HandlerConfig.toggle_chestplate.getDefault())
                .setSaveConsumer(HandlerConfig.toggle_chestplate::set)
                .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_chestplate.tooltip"))
                .build()
        );

        general.addEntry(configEntryBuilder
                .startStrList(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_leggings"), (List)HandlerConfig.toggle_leggings.get())
                .setDefaultValue((List)HandlerConfig.toggle_leggings.getDefault())
                .setSaveConsumer(HandlerConfig.toggle_leggings::set)
                .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_leggings.tooltip"))
                .build()
        );

        general.addEntry(configEntryBuilder
                .startStrList(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_boots"), (List)HandlerConfig.toggle_boots.get())
                .setDefaultValue((List)HandlerConfig.toggle_boots.getDefault())
                .setSaveConsumer(HandlerConfig.toggle_boots::set)
                .setTooltip(Component.translatable("text." + ToggleEnchantments.MOD_ID + ".config.toggle_boots.tooltip"))
                .build()
        );

        return builder.setTransparentBackground(isTransparent).build();
    }
}
