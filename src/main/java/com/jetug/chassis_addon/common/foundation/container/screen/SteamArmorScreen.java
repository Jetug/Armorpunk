package com.jetug.chassis_addon.common.foundation.container.screen;

import com.jetug.chassis_addon.common.foundation.container.menu.SteamChassisMenu;
import com.jetug.chassis_core.common.foundation.container.screen.ChassisScreen;
import com.jetug.generated.resources.Textures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public class SteamArmorScreen extends ChassisScreen<SteamChassisMenu> {
    public SteamArmorScreen(SteamChassisMenu container, Inventory inventory, Component name) {
        super(container, inventory, name, Textures.GUI_ARMOR_INVENTORY);
    }
}