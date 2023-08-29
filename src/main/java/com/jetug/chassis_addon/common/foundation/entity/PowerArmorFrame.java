package com.jetug.chassis_addon.common.foundation.entity;

import com.jetug.chassis_core.common.foundation.entity.WearableChassis;
import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import static com.jetug.generated.animations.ArmorChassisAnimation.*;
import static com.jetug.chassis_core.common.util.helpers.AnimationHelper.setAnimation;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.*;

public class PowerArmorFrame extends WearableChassis {
    public PowerArmorFrame(EntityType<? extends WearableChassis> type, Level worldIn) {
        super(type, worldIn);
    }

    @Override
    public void registerControllers(AnimationData data) {
        var armsController = new AnimationController<>(this, "arm_controller", 0, this::animateArms);
        data.addAnimationController(armsController);
        data.addAnimationController(new AnimationController<>(this, "leg_controller", 0, this::animateLegs));
    }

    private <E extends IAnimatable> PlayState animateArms(AnimationEvent<E> event) {
        var controller = event.getController();
        controller.animationSpeed = 1.0D;

        var player = getPlayerPassenger();

        if (player != null) {
            if (player.attackAnim > 0) {
                controller.animationSpeed = 2.0D;
                setAnimation(controller, HIT, PLAY_ONCE);
                return PlayState.CONTINUE;
            } else if (hurtTime > 0) {
                setAnimation(controller, HURT, PLAY_ONCE);
                return PlayState.CONTINUE;
            }
            else if (isWalking()) {
                setAnimation(controller, WALK_ARMS, LOOP);
                controller.animationSpeed = speedometer.getSpeed() * 4.0D;
                return PlayState.CONTINUE;
            }
        }

        setAnimation(controller, "idle", LOOP);
        return PlayState.CONTINUE;
    }

    private <E extends IAnimatable> PlayState animateLegs(AnimationEvent<E> event) {
        var controller = event.getController();
        controller.animationSpeed = 1.0D;

        if (!hasPlayerPassenger()) return PlayState.STOP;
        var player = getPlayerPassenger();

        if (this.isWalking()) {
            if (player.isShiftKeyDown()){
                setAnimation(controller, SNEAK_WALK, LOOP);
            }
            else {
                setAnimation(controller, WALK_LEGS, LOOP);
                controller.animationSpeed = speedometer.getSpeed() * 4.0D;
            }
            return PlayState.CONTINUE;
        }
        else if (player.isShiftKeyDown()) {
            setAnimation(controller, SNEAK_END, LOOP);
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }
}
