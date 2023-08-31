package com.jetug.chassis_addon.common.network;

import com.jetug.chassis_addon.common.network.actions.CastingStatusAction;
import com.jetug.chassis_addon.common.network.actions.DashAction;
import com.jetug.chassis_core.common.network.ActionRegistry;
import com.jetug.chassis_core.common.network.actions.InputAction;
import com.jetug.chassis_core.common.network.actions.InventorySyncAction;

public class Registry {
    static {
        ActionRegistry.addAction(new DashAction());
        //ActionRegistry.addAction(new CastingStatusAction());
    }
}
