package com.jetug.chassis_addon.client.events;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.client.renderers.PowerArmorRenderer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.jetug.chassis_addon.common.foundation.registery.EntityTypeRegistry.POWER_ARMOR_FRAME;

@Mod.EventBusSubscriber(modid = ChassisAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SetupEvents {
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent()
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(POWER_ARMOR_FRAME.get(),  PowerArmorRenderer::new);
    }
}