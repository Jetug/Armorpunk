package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.foundation.item.ChassisArmor;
import net.minecraft.world.item.Item;

public class CastItem extends Item {
    private final SteamArmorItem result;

    public CastItem(SteamArmorItem result) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1));
        this.result = result;
    }

    public SteamArmorItem getResult() {
        return result;
    }
}
