package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.ChassisPart;
import com.jetug.chassis_core.common.foundation.item.ChassisEquipment;

public class EngineItem extends ChassisEquipment {
    public final Integer overheat;
    public final Integer heat;
    public final Float speed;

    public EngineItem(Integer overheat, Integer heat, Float speed) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1), ChassisPart.ENGINE);
        this.overheat = overheat;
        this.heat = heat;
        this.speed = speed;
    }
}
