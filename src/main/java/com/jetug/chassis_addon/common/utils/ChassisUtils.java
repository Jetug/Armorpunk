package com.jetug.chassis_addon.common.utils;


import com.jetug.chassis_addon.common.foundation.entity.SteamArmorChassis;
import com.jetug.chassis_core.common.foundation.entity.WearableChassis;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import javax.annotation.Nullable;

import static com.jetug.chassis_core.client.ClientConfig.getLocalPlayer;
import static com.jetug.chassis_core.common.network.PacketSender.doServerAction;

public class ChassisUtils {
    public static boolean isWearingSteamChassis(){
        return isWearingSteamChassis(getLocalPlayer());
    }

    public static boolean isWearingSteamChassis(Entity player){
        return player != null && player.getVehicle() instanceof SteamArmorChassis;
    }

    @Nullable
    public static SteamArmorChassis getSteamChassis(Player player){
        if(player.getVehicle() instanceof SteamArmorChassis)
            return (SteamArmorChassis) player.getVehicle();
        else return null;
    }


    @Nullable
    public static WearableChassis getPlayerChassis(Player player){
        if(player.getVehicle() instanceof WearableChassis)
            return (WearableChassis) player.getVehicle();
        else return null;
    }
}
