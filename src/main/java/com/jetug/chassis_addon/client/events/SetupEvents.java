package com.jetug.chassis_addon.client.events;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.client.hud.HeatRenderer;
import com.jetug.chassis_addon.client.renderers.SteamArmorRenderer;
import com.jetug.chassis_addon.common.foundation.registery.GuiRegistry;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.OverlayRegistry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import static com.jetug.chassis_addon.common.foundation.registery.ContainerRegistry.*;
import static com.jetug.chassis_addon.common.foundation.registery.EntityTypeRegistry.*;

@Mod.EventBusSubscriber(modid = ChassisAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class SetupEvents {

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent()
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event){
        event.registerEntityRenderer(ARMOR_CHASSIS.get(),  SteamArmorRenderer::new);
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void clientSetup(FMLClientSetupEvent event) {
        GuiRegistry.register();
        setupHud();
    }

    public static void setupHud() {
        OverlayRegistry.registerOverlayAbove(ForgeIngameGui.FOOD_LEVEL_ELEMENT, "Armor heat", new HeatRenderer());
    }
}