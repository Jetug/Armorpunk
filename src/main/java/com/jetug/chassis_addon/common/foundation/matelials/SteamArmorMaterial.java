package com.jetug.chassis_addon.common.foundation.matelials;

import com.jetug.chassis_core.common.foundation.ChassisArmorMaterial;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.function.Supplier;

public class SteamArmorMaterial extends ChassisArmorMaterial {
    private final int[] craftPerSlot;

    public SteamArmorMaterial(String name, int durabilityMultiplier, int[] slotProtections,
                              float toughness, int enchantmentValue, SoundEvent sound,
                              float knockbackResistance, Supplier<Ingredient> p_40481_, int[] craftPerSlot) {
        super(name, durabilityMultiplier, slotProtections, toughness, enchantmentValue, sound, knockbackResistance, p_40481_);
        this.craftPerSlot = craftPerSlot;
    }

    public int getCraftPerSlotForSlot(String bodyPart) {
        return this.craftPerSlot[this.getPartId(bodyPart)];
    }
}
