package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.BodyPart;
import com.jetug.chassis_core.common.foundation.item.ChassisEquipment;

public class CoatingItem extends ChassisEquipment {
    public CoatingItem() {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1),BodyPart.BODY_FRAME);
    }
}
