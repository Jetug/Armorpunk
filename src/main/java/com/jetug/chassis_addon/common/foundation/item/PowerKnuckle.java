package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.ChassisPart;
import com.jetug.chassis_core.common.foundation.item.ChassisEquipment;

public class PowerKnuckle extends ChassisEquipment {
    public final int heat;
    public final float force;

    public PowerKnuckle(int heat, float force) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1), ChassisPart.RIGHT_HAND);
        this.heat = heat;
        this.force = force;
    }
}
