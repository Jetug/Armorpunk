package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.matelials.SteamArmorMaterial;
import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.foundation.item.ChassisArmor;

public class SteamArmorItem extends ChassisArmor {
    public final float speed;
    public final SteamArmorMaterial material;

    public SteamArmorItem(SteamArmorMaterial material, String part, float speed) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB), material, part);
        this.speed = speed;
        this.material = material;
    }

    public SteamArmorMaterial getMaterial() {
        return this.material;
    }

    public int getIngredientCount(){
        return material.getCraftPerSlotForSlot(part);
    }
}
