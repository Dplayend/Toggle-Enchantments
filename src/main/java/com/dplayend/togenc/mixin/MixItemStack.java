package com.dplayend.togenc.mixin;

import com.dplayend.togenc.client.implement.OptionalRegistry;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin({ItemStack.class})
public class MixItemStack {
    @Inject(method = "appendEnchantmentNames", at = @At("HEAD"), cancellable = true)
    private static void appendEnchantmentNames(List<Component> list, ListTag tags, CallbackInfo info) {
        info.cancel();
        new OptionalRegistry(list, tags).getTooltip();
    }
}
