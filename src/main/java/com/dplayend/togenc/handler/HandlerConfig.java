package com.dplayend.togenc.handler;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.Builder;
import net.minecraftforge.common.ForgeConfigSpec.BooleanValue;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;

import java.util.*;

public class HandlerConfig {
    public static boolean dv_tooltip = true;
    public static List<String> dv_toggle_hand = Arrays.asList("silk_touch;#00AA00", "fire_aspect;#FFAA00", "flame;#FFAA00");
    public static List<String> dv_toggle_helmet = new ArrayList<>();
    public static List<String> dv_toggle_chestplate = new ArrayList<>();
    public static List<String> dv_toggle_leggings = new ArrayList<>();
    public static List<String> dv_toggle_boots = Arrays.asList("frost_walker;#A5F2F3");

    public static final Builder CONFIG = new Builder();
    public static final ForgeConfigSpec SPEC;

    public static final BooleanValue tooltip;
    public static final ConfigValue<List<? extends String>> toggle_hand;
    public static final ConfigValue<List<? extends String>> toggle_helmet;
    public static final ConfigValue<List<? extends String>> toggle_chestplate;
    public static final ConfigValue<List<? extends String>> toggle_leggings;
    public static final ConfigValue<List<? extends String>> toggle_boots;

    static {
        CONFIG.push("general");

        tooltip = CONFIG.define("enable_tooltip", dv_tooltip);
        toggle_hand = CONFIG.defineList("toggle_hand_enchantments", dv_toggle_hand, toggle_hand_enchantments -> toggle_hand_enchantments instanceof String);
        toggle_helmet = CONFIG.defineList("toggle_helmet_enchantments", dv_toggle_helmet, toggle_helmet_enchantments -> toggle_helmet_enchantments instanceof String);
        toggle_chestplate = CONFIG.defineList("toggle_chestplate_enchantments", dv_toggle_chestplate, toggle_chestplate_enchantments -> toggle_chestplate_enchantments instanceof String);
        toggle_leggings = CONFIG.defineList("toggle_leggings_enchantments", dv_toggle_leggings, toggle_leggings_enchantments -> toggle_leggings_enchantments instanceof String);
        toggle_boots = CONFIG.defineList("toggle_boots_enchantments", dv_toggle_boots, toggle_boots_enchantments -> toggle_boots_enchantments instanceof String);

        CONFIG.pop();
        SPEC = CONFIG.build();
    }
}
