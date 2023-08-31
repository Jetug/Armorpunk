package com.jetug.chassis_addon.common.events;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_core.ChassisCore;
import com.jetug.chassis_core.common.events.CommonInputEvent;
import com.jetug.chassis_core.common.events.ContainerChangedEvent;
import com.jetug.chassis_core.common.input.InputKey;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.jetug.chassis_addon.common.utils.ChassisUtils.getSteamChassis;
import static com.jetug.chassis_addon.common.utils.ChassisUtils.isWearingSteamChassis;
import static com.jetug.chassis_core.common.input.InputKey.JUMP;
import static com.jetug.chassis_core.common.input.KeyAction.PRESS;
import static com.jetug.chassis_core.common.input.KeyAction.REPEAT;
import static com.jetug.chassis_core.common.util.helpers.PlayerUtils.*;
import static com.jetug.chassis_core.common.util.helpers.PlayerUtils.isWearingChassis;
import static java.lang.System.out;
import static net.minecraftforge.event.entity.player.PlayerInteractEvent.*;

@Mod.EventBusSubscriber(modid = ChassisAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CustomHandler {

    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onEntityContainerChange(ContainerChangedEvent event) {
        var entity = event.getEntity();
        out.println(entity);
    }

    @SubscribeEvent
    public static void onPlayerInteract(EntityInteract event) {
        if(cancelInteraction(event.getPlayer()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerInteract(EntityInteractSpecific event) {
        if(cancelInteraction(event.getPlayer()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerInteract(RightClickBlock event) {
        if(cancelInteraction(event.getPlayer()))
            event.setCanceled(true);
    }

    @SubscribeEvent
    public static void onPlayerHurt(LivingHurtEvent event) {
        if(event.getEntity() instanceof Player player
                && isWearingSteamChassis(player)
                && getSteamChassis(player).hasFireProtection()){
            event.setCanceled(true);
        }
    }

    private static boolean cancelInteraction(Player player){
        return isWearingSteamChassis(player) && getSteamChassis(player).attackChargeController.isChargingAttack();
    }
}