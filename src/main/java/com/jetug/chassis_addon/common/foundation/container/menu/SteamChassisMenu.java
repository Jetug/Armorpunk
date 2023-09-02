package com.jetug.chassis_addon.common.foundation.container.menu;

import com.jetug.chassis_core.common.foundation.container.menu.EntityMenu;
import com.jetug.chassis_core.common.foundation.entity.WearableChassis;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;

import static com.jetug.chassis_addon.common.foundation.registery.ContainerRegistry.*;
import static com.jetug.chassis_core.common.data.constants.Gui.*;
import static com.jetug.chassis_core.common.data.enums.BodyPart.*;

public class SteamChassisMenu extends EntityMenu {
    public static final int SIZE = 7;
    private static final int INVENTORY_POS_Y = 84;

    public SteamChassisMenu(int i, Inventory playerInventory) {
        this(i, new SimpleContainer(SIZE), playerInventory, null);
    }

    public SteamChassisMenu(int containerId, Container container, Inventory playerInventory, WearableChassis entity) {
        super(STEAM_CHASSIS_MENU.get(), containerId, container, playerInventory, entity, SIZE, INVENTORY_POS_Y);
        createSlot(HELMET, HEAD_SLOT_POS     );
        createSlot(BODY_ARMOR, BODY_SLOT_POS     );
        createSlot(LEFT_ARM_ARMOR, RIGHT_ARM_SLOT_POS);
        createSlot(RIGHT_ARM_ARMOR, LEFT_ARM_SLOT_POS );
        createSlot(LEFT_LEG_ARMOR, RIGHT_LEG_SLOT_POS);
        createSlot(RIGHT_LEG_ARMOR, LEFT_LEG_SLOT_POS );
        createSlot(ENGINE   , ENGINE_SLOT_POS   );
    }
}