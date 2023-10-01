package com.dplayend.togenc;

import com.dplayend.togenc.handler.HandlerClothConfig;
import com.dplayend.togenc.network.ServerNetworking;
import com.dplayend.togenc.network.packet.*;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.lwjgl.glfw.GLFW;

public class ToggleEnchantmentsClient {
    public static Minecraft client = Minecraft.getInstance();
    public static KeyMapping TOGGLE_HAND_ENCHANTMENTS = new KeyMapping("key." + ToggleEnchantments.MOD_ID + ".toggle_hand_enchantments", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, "key." + ToggleEnchantments.MOD_ID + "." + "title");
    public static KeyMapping TOGGLE_HELMET_ENCHANTMENTS = new KeyMapping("key." + ToggleEnchantments.MOD_ID + ".toggle_helmet_enchantments", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key." + ToggleEnchantments.MOD_ID + "." + "title");
    public static KeyMapping TOGGLE_CHESTPLATE_ENCHANTMENTS = new KeyMapping("key." + ToggleEnchantments.MOD_ID + ".toggle_chestplate_enchantments", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key." + ToggleEnchantments.MOD_ID + "." + "title");
    public static KeyMapping TOGGLE_LEGGINGS_ENCHANTMENTS = new KeyMapping("key." + ToggleEnchantments.MOD_ID + ".toggle_leggings_enchantments", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_UNKNOWN, "key." + ToggleEnchantments.MOD_ID + "." + "title");
    public static KeyMapping TOGGLE_BOOTS_ENCHANTMENTS = new KeyMapping("key." + ToggleEnchantments.MOD_ID + ".toggle_boots_enchantments", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, "key." + ToggleEnchantments.MOD_ID + "." + "title");

    @EventBusSubscriber(modid = ToggleEnchantments.MOD_ID, value = Dist.CLIENT)
    public static class ClientForgeEvents {
        @SubscribeEvent
        public static void checkMouse (InputEvent.MouseButton event){
            if (client.player != null && client.level != null) ServerNetworking.sendToServer(new PacketUpdateEnchants());
        }

        @SubscribeEvent
        public static void checkKeyboard (InputEvent.Key event){
            if (client.player != null && client.level != null) {
                ServerNetworking.sendToServer(new PacketUpdateEnchants());
                if (TOGGLE_HAND_ENCHANTMENTS.consumeClick()) ServerNetworking.sendToServer(new PacketHandEnchantments());
                if (TOGGLE_HELMET_ENCHANTMENTS.consumeClick()) ServerNetworking.sendToServer(new PacketHelmetEnchantments());
                if (TOGGLE_CHESTPLATE_ENCHANTMENTS.consumeClick()) ServerNetworking.sendToServer(new PacketChestplateEnchantments());
                if (TOGGLE_LEGGINGS_ENCHANTMENTS.consumeClick()) ServerNetworking.sendToServer(new PacketLeggingsEnchantments());
                if (TOGGLE_BOOTS_ENCHANTMENTS.consumeClick()) ServerNetworking.sendToServer(new PacketBootsEnchantments());
            }
        }
    }

    @EventBusSubscriber(modid = ToggleEnchantments.MOD_ID, value = Dist.CLIENT, bus = Bus.MOD)
    public static class ClientProxy {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            if (ModList.get().isLoaded("cloth_config")) ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> HandlerClothConfig.getConfigScreen(screen, true)));
        }

        @SubscribeEvent
        public static void registerKeys(final RegisterKeyMappingsEvent event) {
            event.register(ToggleEnchantmentsClient.TOGGLE_HAND_ENCHANTMENTS);
            event.register(ToggleEnchantmentsClient.TOGGLE_HELMET_ENCHANTMENTS);
            event.register(ToggleEnchantmentsClient.TOGGLE_CHESTPLATE_ENCHANTMENTS);
            event.register(ToggleEnchantmentsClient.TOGGLE_LEGGINGS_ENCHANTMENTS);
            event.register(ToggleEnchantmentsClient.TOGGLE_BOOTS_ENCHANTMENTS);
        }
    }
}
