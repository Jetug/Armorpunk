package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.BodyPart;
import com.jetug.chassis_core.common.foundation.ChassisArmorMaterial;
import com.jetug.chassis_core.common.foundation.item.ChassisArmor;

public class SteamArmorItem extends ChassisArmor {
    public final float speed;

    public SteamArmorItem(ChassisArmorMaterial material, BodyPart part, float speed) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB), material, part);
        this.speed = speed;
    }
}
