package com.jetug.chassis_addon.common.foundation.container.screen;

import com.jetug.chassis_addon.common.foundation.container.menu.SteamArmorStationMenu;
import com.jetug.chassis_core.common.foundation.container.screen.ArmorStationScreen;
import com.jetug.chassis_core.common.foundation.entity.WearableChassis;
import com.jetug.generated.resources.Textures;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;

public class SteamArmorStationScreen extends ArmorStationScreen<SteamArmorStationMenu> {
    public SteamArmorStationScreen(SteamArmorStationMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle, Textures.GUI_ARMOR_STATION_GUI);
    }
}
