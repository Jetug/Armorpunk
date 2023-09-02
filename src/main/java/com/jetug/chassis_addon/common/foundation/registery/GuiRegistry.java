package com.jetug.chassis_addon.common.foundation.registery;

import com.jetug.chassis_addon.common.foundation.container.screen.*;
import net.minecraft.client.gui.screens.*;

import static com.jetug.chassis_addon.common.foundation.registery.ContainerRegistry.*;

public class GuiRegistry {
    public static void register() {
        MenuScreens.register(STEAM_CHASSIS_MENU.get(), SteamArmorScreen::new);
        MenuScreens.register(ARMOR_STATION_MENU.get(), SteamArmorStationScreen::new);
        MenuScreens.register(CASTING_TABLE_MENU.get(), CastingTableScreen::new);
    }
}
