package com.jetug.chassis_addon.common.foundation.registery;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModCreativeModeTab {
    public static final CreativeModeTab CHASSIS_TAB = new CreativeModeTab("ArmorPunk") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ItemRegistry.PA_HELMET.get());
        }
    };
}
