package com.jetug.chassis_addon.common.events;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.entity.SteamArmorChassis;
import com.jetug.chassis_addon.common.foundation.registery.EntityTypeRegistry;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = ChassisAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(EntityTypeRegistry.ARMOR_CHASSIS.get(), SteamArmorChassis.createAttributes().build());
    }

//    @SubscribeEvent
//    public static void setup(final FMLCommonSetupEvent event) {
//        PacketHandler.register();
//    }

//    @SubscribeEvent
//    public static void registerModifierSerializers(@Nonnull final RegistryEvent.Register<GlobalLootModifierSerializer<?>> event) {
//        event.getRegistry().register(new CastModifier.Serializer()
//                        .setRegistryName(new ResourceLocation(Global.MOD_ID,"cast_in_dungeon")));
//    }
}
