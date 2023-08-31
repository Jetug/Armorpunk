package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.BodyPart;
import com.jetug.chassis_core.common.foundation.item.ChassisEquipment;

public class JetpackItem extends ChassisEquipment {
    public final int heat;
    public final float speed;
    public final float force;

    public JetpackItem(int heat, Float speed, float force) {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1), BodyPart.BACK);
        this.heat = heat;
        this.speed = speed;
        this.force = force;
    }
}