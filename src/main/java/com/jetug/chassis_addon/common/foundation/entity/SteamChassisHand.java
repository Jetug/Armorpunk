package com.jetug.chassis_addon.common.foundation.entity;

import com.jetug.chassis_core.client.HandEntity;
import net.minecraft.client.Minecraft;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static com.jetug.chassis_addon.common.utils.ChassisUtils.getSteamChassis;
import static com.jetug.chassis_addon.common.utils.ChassisUtils.isWearingSteamChassis;
import static com.jetug.chassis_core.common.util.helpers.AnimationHelper.setAnimation;
import static com.jetug.chassis_core.common.util.helpers.PlayerUtils.getPlayerChassis;
import static com.jetug.chassis_core.common.util.helpers.PlayerUtils.isWearingChassis;
import static com.jetug.generated.animations.ArmorChassisHandAnimation.*;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

@SuppressWarnings("ConstantConditions")
public class SteamChassisHand extends HandEntity {
    @Override
    protected <T extends IAnimatable> PlayState predicate(AnimationEvent<T> event) {
        if(!isWearingSteamChassis(player)) return PlayState.STOP;

        var controller = event.getController();
        var chassis = getSteamChassis(player);
        controller.animationSpeed = 1;

        if(chassis.isPunching()){
            controller.animationSpeed = 2;
            setAnimation(controller, PUNCH, PLAY_ONCE);
        }
        else if(chassis.attackChargeController.isMaxCharge()){
            setAnimation(controller,PUNCH_MAX_CHARGE, LOOP);
        }
        else if(chassis.attackChargeController.isChargingAttack()){
            controller.animationSpeed = 0.3;
            setAnimation(controller,PUNCH_CHARGE, LOOP);
        }
        else
        if(player.swinging){
            controller.animationSpeed = 3;
            setAnimation(controller,HIT, LOOP);
        }
        else if(chassis.isWalking()){
            setAnimation(controller,WALK, LOOP);
        }
        else {
            setAnimation(controller,IDLE, LOOP);
        }
        return PlayState.CONTINUE;
    }
}
