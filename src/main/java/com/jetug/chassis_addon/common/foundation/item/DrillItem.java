package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.client.renderers.item.DrillRenderer;
import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.foundation.item.AnimatableItem;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

public class DrillItem extends AnimatableItem {
    public DrillItem() {
        super(new Properties().tab(ModCreativeModeTab.CHASSIS_TAB).stacksTo(1));
    }

    @Override
    protected DrillRenderer createRenderer() {
        return new DrillRenderer();
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::animate));
    }

    private <T extends IAnimatable> PlayState animate(AnimationEvent<T> event) {
        var controller = event.getController();
//
//        if (PlayerUtils.isWearingChassis()) {
//            var armor = PlayerUtils.getPlayerChassis();
//            controller.animationSpeed = 1;
//
//            if (armor.isPunching()) {
//                controller.animationSpeed = 2;
//                setAnimation(controller, PUNCH, PLAY_ONCE);
//            } else if (armor.isMaxCharge()) {
//                setAnimation(controller, PUNCH_MAX_CHARGE, LOOP);
//            } else if (armor.isChargingAttack()) {
//                controller.animationSpeed = 0.3;
//                setAnimation(controller, PUNCH_CHARGE, LOOP);
//            }
//            else {
//                setAnimation(controller, DRILL_USE, LOOP);
//            }
//        }

        return PlayState.CONTINUE;
    }
}
