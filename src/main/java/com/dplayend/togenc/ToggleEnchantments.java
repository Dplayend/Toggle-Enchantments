package com.dplayend.togenc;

import com.dplayend.togenc.handler.HandlerConfig;
import com.dplayend.togenc.network.ServerNetworking;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod(ToggleEnchantments.MOD_ID)
public class ToggleEnchantments {
    public static final String MOD_ID = "togenc";
    public static final String MOD_NAME = "toggle_enchantments";

    public ToggleEnchantments() {
        ServerNetworking.init();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, HandlerConfig.SPEC, MOD_NAME + "-common.toml");
    }
}
