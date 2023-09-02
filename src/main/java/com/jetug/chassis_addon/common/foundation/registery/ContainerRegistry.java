package com.jetug.chassis_addon.common.foundation.registery;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.container.menu.SteamArmorStationMenu;
import com.jetug.chassis_addon.common.foundation.container.menu.CastingTableMenu;
import com.jetug.chassis_addon.common.foundation.container.menu.SteamChassisMenu;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.network.IContainerFactory;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegistry {
    public static final DeferredRegister<MenuType<?>> CONTAINERS = DeferredRegister
            .create(ForgeRegistries.CONTAINERS, ChassisAddon.MOD_ID);

    public static final RegistryObject<MenuType<SteamChassisMenu>> STEAM_CHASSIS_MENU
            = CONTAINERS.register("steam_chassis_menu", () -> new MenuType<>(SteamChassisMenu::new));

    public static final RegistryObject<MenuType<SteamArmorStationMenu>> ARMOR_STATION_MENU
            = CONTAINERS.register("chassis_station_menu", () -> new MenuType<>(SteamArmorStationMenu::new));

    public static final RegistryObject<MenuType<CastingTableMenu>> CASTING_TABLE_MENU =
            registerMenuType(CastingTableMenu::new, "casting_table_menu");


    private static <T extends AbstractContainerMenu>RegistryObject<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return CONTAINERS.register(name, () -> IForgeMenuType.create(factory));
    }

    public static void register(IEventBus eventBus) {
        CONTAINERS.register(eventBus);
    }
}
