package com.jetug.chassis_addon;


import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.jetug.chassis_addon.ChassisAddon.MOD_ID;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@Mod(MOD_ID)
public class ChassisAddon {
    public static final String MOD_ID = "chassis_addon";
    public static final IEventBus MOD_EVENT_BUS = FMLJavaModLoadingContext.get().getModEventBus();

    public ChassisAddon() {
        //register();
        EVENT_BUS.register(this);
        //var s = ChassisCore.MOD_ID;
    }
//
//    private void register() {
//        EntityTypeRegistry.register(MOD_EVENT_BUS);
//    }
}
