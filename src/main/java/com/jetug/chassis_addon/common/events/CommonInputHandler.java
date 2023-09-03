package com.jetug.chassis_addon.common.events;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.data.enums.DashDirection;
import com.jetug.chassis_core.common.events.CommonInputEvent;
import com.jetug.chassis_core.common.input.InputKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.jetug.chassis_addon.common.utils.ChassisUtils.getSteamChassis;
import static com.jetug.chassis_addon.common.utils.ChassisUtils.isWearingSteamChassis;
import static com.jetug.chassis_core.common.input.InputKey.*;
import static com.jetug.chassis_core.common.input.KeyAction.PRESS;
import static com.jetug.chassis_core.common.input.KeyAction.REPEAT;
import static com.jetug.chassis_core.common.util.helpers.PlayerUtils.*;

@Mod.EventBusSubscriber(modid = ChassisAddon.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonInputHandler {
    @SubscribeEvent(priority = EventPriority.NORMAL)
    public static void onCommonInput(CommonInputEvent event) {
        var player = event.getPlayer();
        var action = event.getAction();
        var key = event.getKey();
        if (!isWearingSteamChassis(player) || key == null) return;

//        if((action == PRESS || action == REPEAT) && key == JUMP)
//            getSteamChassis(player).jump();

        switch (action) {
            case PRESS -> onPress(key, player);
            case RELEASE -> onRelease(key, player);
            case DOUBLE_CLICK -> onDoubleClick(key, player);
            case LONG_PRESS -> onLongPress(key, player);
        }
    }

    public static void onPress(InputKey key, Player player){
        if (key == InputKey.LEAVE)
            stopWearingArmor(player);
    }

    public static void onRelease(InputKey key, Player player){
        if (!isWearingSteamChassis(player)) return;
        if(key == ATTACK) getSteamChassis(player).powerPunch();
        if(key == USE) getSteamChassis(player).attackChargeController.resetAttackCharge();
    }

    public static void onDoubleClick(InputKey key, Player player){
        if (!isWearingSteamChassis(player)) return;

        DashDirection direction = switch (key){
            case UP    -> DashDirection.FORWARD;
            case DOWN  -> DashDirection.BACK;
            case LEFT  -> DashDirection.LEFT;
            case RIGHT -> DashDirection.RIGHT;
            case JUMP  -> DashDirection.UP;
            default -> null;
        };

        if(direction == null) return;
        getSteamChassis(player).dash(direction);
    }

    public static void onLongPress(InputKey key, Player player){
        if (!isWearingSteamChassis(player)) return;
        var bool = key == USE;
        if(bool){
            getSteamChassis(player).attackChargeController.addAttackCharge();
        }
    }
}