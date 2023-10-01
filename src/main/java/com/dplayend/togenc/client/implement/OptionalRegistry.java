package com.dplayend.togenc.client.implement;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class OptionalRegistry {
    public List<Component> list;
    public ListTag tags;

    public OptionalRegistry(List<Component> list, ListTag tags) {
        this.list = list;
        this.tags = tags;
    }

    public void getTooltip() {
        for (int i = 0; i < tags.size(); i++) {
            CompoundTag nbt = tags.getCompound(i);
            short getEnchantLevel = tags.getCompound(i).getShort("lvl");
            short getDefaultLevel = tags.getCompound(i).getShort("defaultLvl");

            if (getEnchantLevel > 0) {
                BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(nbt.getString("id"))).ifPresent((e) -> list.add(e.getFullname(nbt.getInt("lvl"))));
            } else {
                if (tags.getCompound(i).contains("defaultLvl")) {
                    BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(nbt.getString("id"))).ifPresent((e) -> list.add(e.getFullname(getDefaultLevel)));
                } else {
                    BuiltInRegistries.ENCHANTMENT.getOptional(ResourceLocation.tryParse(nbt.getString("id"))).ifPresent((e) -> list.add(e.getFullname(nbt.getInt("lvl"))));
                }
            }
        }
    }
}
