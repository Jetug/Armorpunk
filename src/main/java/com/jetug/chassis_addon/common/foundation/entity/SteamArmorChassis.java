package com.jetug.chassis_addon.common.foundation.entity;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.container.menu.SteamArmorStationMenu;
import com.jetug.chassis_addon.common.foundation.container.menu.SteamChassisMenu;
import com.jetug.chassis_addon.common.foundation.item.*;
import com.jetug.chassis_addon.common.foundation.registery.ItemRegistry;
import com.jetug.chassis_addon.common.utils.AttackChargeController;
import com.jetug.chassis_addon.common.data.enums.DashDirection;
import com.jetug.chassis_core.common.foundation.entity.ArmorChassisBase;
import com.jetug.chassis_core.common.foundation.entity.HandEntity;
import com.jetug.chassis_core.common.foundation.entity.WearableChassis;
import com.jetug.chassis_core.common.network.data.ArmorData;
import com.jetug.chassis_core.common.util.helpers.*;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.List;
import java.util.function.Consumer;

import static com.jetug.chassis_addon.common.utils.PovUtils.getBlockHitResult;
import static com.jetug.generated.animations.ArmorChassisAnimation.*;
import static com.jetug.generated.animations.ArmorChassisAnimation.DASH_UP;
import static com.jetug.chassis_core.common.data.enums.BodyPart.*;
import static com.jetug.chassis_core.common.network.data.ArmorData.HEAT;
import static com.jetug.chassis_core.common.util.helpers.AnimationHelper.setAnimation;
import static com.jetug.chassis_core.common.util.helpers.MathHelper.getPercentOf;
import static net.minecraft.client.renderer.debug.DebugRenderer.getTargetedEntity;
import static net.minecraft.util.Mth.cos;
import static net.minecraft.util.Mth.sin;
import static net.minecraft.world.InteractionHand.MAIN_HAND;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.*;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME;

public class SteamArmorChassis extends WearableChassis {
    private static final int MAX_ATTACK_CHARGE = 60;

    private boolean isDashing = false;
    private boolean isPunching = false;
    private DashDirection dashDirection;

    public static final SteamChassisHand HAND = new SteamChassisHand();
    public static final ResourceLocation ICON
            = new ResourceLocation(ChassisAddon.MOD_ID, "textures/item/steam_chassis.png");

    public HeatController heatController = new HeatController(){
        @Override
        public int getHeatCapacity(){
            return hasEquipment(ENGINE) ? ((EngineItem)getEquipment(ENGINE).getItem()).overheat : 0;
        }
    };

    public AttackChargeController attackChargeController = new AttackChargeController(MAX_ATTACK_CHARGE){
        @Override
        public void addAttackCharge() {
            if (hasPlayerPassenger() && hasPowerKnuckle() && (playerHandIsEmpty() || isDrillItemInHand()))
                super.addAttackCharge();
        }
    };

    @Override
    public ResourceLocation getIcon() {
        return ICON;
    }

    @Override
    public HandEntity getHandEntity() {
        return HAND;
    }

    @Override
    public void tick() {
        super.tick();
        applyEffects();

        if(isServerSide && isDashing && dashDirection != DashDirection.UP) {
            pushEntitiesAround();
        }

        heatController.subHeat(COOLING);
        if(isInLava()){
            heatController.addHeat(20);
        }
        if (isInWaterOrRain()){
            heatController.subHeat(5);
        }
    }

    private void applyEffects(){
        var player = getPlayerPassenger();

        if(player != null) {
            PlayerUtils.addEffect(player, MobEffects.DIG_SPEED         , 1);
            PlayerUtils.addEffect(player, MobEffects.DAMAGE_BOOST      , 1);
            PlayerUtils.addEffect(player, MobEffects.DAMAGE_RESISTANCE , 1);
            if(hasFireProtection()) {
                PlayerUtils.addEffect(player, MobEffects.FIRE_RESISTANCE, 1);
                player.clearFire();
            }
        }
    }

    @Override
    protected void updateSpeed() {
        if(inventory.getItem(ENGINE.ordinal()).getItem() instanceof EngineItem engine)
            setSpeed(getSpeedAttribute() * engine.speed);
        else {
            setSpeed(getMinSpeed());
            return;
        }

        for (var part : armor){
            if (inventory.getItem(part.ordinal()).getItem() instanceof SteamArmorItem armorItem)
                setSpeed(getSpeed() * armorItem.speed);
        }
    }

    @Override
    public MenuProvider getMenuProvider(){
        return new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int id, Inventory menu, Player player) {
                return new SteamChassisMenu(id, inventory, menu, SteamArmorChassis.this);
            }

            @Override
            public Component getDisplayName() {
                return SteamArmorChassis.this.getDisplayName();
            }
        };
    }

    @Override
    protected MenuProvider getStantionMenuProvider() {
        return new MenuProvider() {
            @Override
            public AbstractContainerMenu createMenu(int id, Inventory menu, Player player) {
                return new SteamArmorStationMenu(id, inventory, menu, SteamArmorChassis.this);
            }

            @Override
            public Component getDisplayName() {
                return SteamArmorChassis.this.getDisplayName();
            }
        };
    }

    @Override
    public boolean hurt(DamageSource damageSource, float damage) {
        if (damageSource.isFire() && hasFireProtection()) return false;
        return super.hurt(damageSource, damage);
    }

    @Override
    public void addAdditionalSaveData(@NotNull CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt(HEAT, heatController.getHeat());
    }

    @Override
    public void readAdditionalSaveData(@NotNull CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        heatController.setHeat(compound.getInt(HEAT));
    }

    @Override
    public ArmorData getArmorData() {
        var data = super.getArmorData();
        data.heat = heatController.getHeat();
        return data;
    }

    @Override
    public boolean causeFallDamage(float height, float multiplier, DamageSource damageSource) {
        //if (height > 1) this.playSound(SoundEvents.HORSE_LAND, 0.4F, 1.0F);
        if (height > 4 && hasPlayerPassenger() && !getPlayerPassenger().isShiftKeyDown())
            pushEntitiesAround();

        int damage = this.calculateFallDamage(height, multiplier);
        if (damage <= 0)
            return false;

        if(height >= 20 )
            this.hurt(damageSource, damage);

        this.playBlockFallSound();
        return true;
    }

    public boolean hasPowerKnuckle(){
        return (getEquipment(RIGHT_HAND).getItem() instanceof PowerKnuckle);
    }

    public PowerKnuckle getPowerKnuckle(){
        return (PowerKnuckle) getEquipment(RIGHT_HAND).getItem();
    }

    public boolean hasJetpack(){
        return getEquipment(BACK).getItem() instanceof JetpackItem;
    }

    public JetpackItem getJetpack(){
        return (JetpackItem) getEquipment(BACK).getItem();
    }

    public SteamArmorChassis(EntityType<? extends ArmorChassisBase> type, Level worldIn) {
        super(type, worldIn);
    }

    public boolean hasFireProtection(){
        return getEquipment(BODY_FRAME).getItem() == ItemRegistry.FIREPROOF_COATING.get();
    }

    public boolean isPunching() {
        return isPunching;
    }

    public boolean isDashing() {
        return isDashing;
    }

    boolean isDrillItemInHand() {
        return getPlayerPassenger().getItemInHand(MAIN_HAND).getItem() instanceof DrillItem;
    }

    boolean playerHandIsEmpty() {
        return getPlayerPassenger().getItemInHand(MAIN_HAND).isEmpty();
    }


    public void dash(DashDirection direction) {
        if (!hasJetpack() || !hasEquipment(ENGINE)) return;

        heatController.doHeatAction(getDashHeat(), () -> applyToPlayer((player) -> {
            dashDirection = direction;
            isDashing = true;
            timer.addCooldownTimer(DASH_DURATION, () -> isDashing = false);
            dashInDirection(direction, getJetpack().force);
        }));
    }

    public void powerPunch(){
        if(!hasPowerKnuckle()) return;

        powerPunch(() -> {
            if(!getPlayerPassenger().isShiftKeyDown())
                dash(DashDirection.FORWARD);
            var optional = getTargetedEntity(getPlayerPassenger(), 5);
            if(optional.isPresent() && optional.get() instanceof LivingEntity livingEntity){
                powerPunchTarget(livingEntity);
            }
            else{
                var pick = getBlockHitResult(level, getPlayerPassenger(), ClipContext.Fluid.NONE);
                if(pick.getType() == HitResult.Type.BLOCK) powerPunchBlock(pick.getBlockPos());
            }
        });
    }

    private int getDashHeat(){
        return hasJetpack() ? getJetpack().heat : 0;
    }

    private void pushEntitiesAround(){
        for(LivingEntity entity : getEntitiesInRange(2, 1,2)) {
            if (entity == this || entity == getControllingPassenger())
                continue;
            //var push = 0.5;
            var direction = VectorHelper.getDirection(position(), entity.position());
            entity.knockback(1, direction.x, direction.z);
            //entity.push(direction.x * push, 0.5, direction.z * push);
            entity.hurt(DamageSource.ANVIL, 10);
        }
    }

    private List<LivingEntity> getEntitiesInRange(double x, double y, double z) {
        return this.level.getEntitiesOfClass(LivingEntity.class, new AABB(position(), position()).inflate(x, y, z));
    }

    private void powerPunchTarget(LivingEntity target){
        var vector = getPlayerPassenger().getViewVector(1.0F);
        var force = getPunchForce();

        //target.knockback(force, vector.x, vector.z);
        EntityUtils.push(target, vector.multiply(force, force, force));
    }

    private void powerPunchBlock(BlockPos blockPos){
        var force = getPunchForce();
        var player = getPlayerPassenger();

        if(force == MAX_PUNCH_FORCE)
            dig(blockPos, player);
    }

    private void dig(BlockPos pos, Player player){
        var direction = player.getDirection();
        var centerPos = pos.offset(direction.getNormal());

        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                BlockPos targetPos = centerPos.offset(direction.getStepX() * xOffset, yOffset, direction.getStepZ() * xOffset);
                player.level.destroyBlock(targetPos, true);
            }
        }
    }

    private Vec3 rotateVector(float viewYRot, int angle, float force){
        float rot = (viewYRot + angle) * ROTATION;
        return new Vec3(-sin(rot) * force, 0, cos(rot) * force);
    }

    private void powerPunch(Runnable runnable){
        if(!hasPlayerPassenger() || attackChargeController.getAttackCharge() == 0) return;

        heatController.doHeatAction(getPowerKnuckle().heat, () -> {
            isPunching = true;
            timer.addCooldownTimer(PUNCH_DURATION, () -> isPunching = false);
            runnable.run();
        });

        attackChargeController.resetAttackCharge();
    }

    private float getPunchForce(){
        var charge = attackChargeController.getAttackChargeInPercent();
        return getPercentOf(MAX_PUNCH_FORCE, charge);
    }

    private void applyToPlayer(Consumer<Player> consumer){
        if(!hasPlayerPassenger()) return;
        consumer.accept(getPlayerPassenger());
    }

    private void dashInDirection(DashDirection direction, float force) {
        if(!hasPlayerPassenger()) return;
        float viewYRot = getPlayerPassenger().getViewYRot(1);
        float x = sin(viewYRot * ROTATION) * force;
        float z = cos(viewYRot * ROTATION) * force;
        var vector = Vec3.ZERO;

        switch (direction) {
            case FORWARD -> vector = new Vec3(-x, 0, z);
            case BACK    -> vector = new Vec3(x, 0, -z);
            case RIGHT   -> vector = rotateVector(viewYRot, 90, force);
            case LEFT    -> vector = rotateVector(viewYRot, -90, force);
            case UP      -> vector = new Vec3(0, force / 2, 0);
        }

        setDeltaMovement(getDeltaMovement().add(vector));
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
            if (isDashing) {
                return animateDash(controller);
            }
            else if(isPunching){
                controller.animationSpeed = 2;
                setAnimation(controller, POWER_PUNCH, HOLD_ON_LAST_FRAME);
                return PlayState.CONTINUE;
            }
            else if(attackChargeController.isMaxCharge()){
                setAnimation(controller, POWER_PUNCH_CHARGE_LOOP, LOOP);
                return PlayState.CONTINUE;
            }
            else if(attackChargeController.isChargingAttack()){
                setAnimation(controller, POWER_PUNCH_CHARGE, PLAY_ONCE);
                controller.animationSpeed = 0.7;
                return PlayState.CONTINUE;
            }
            else if (player.attackAnim > 0) {
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

        if(!isDashing) {
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
        }
        return PlayState.STOP;
    }

    private <E extends IAnimatable> PlayState animateDash(AnimationController<E> controller) {
        switch (dashDirection) {
            case FORWARD -> setAnimation(controller, DASH_FORWARD, HOLD_ON_LAST_FRAME);
            case BACK   -> setAnimation(controller, DASH_BACK, HOLD_ON_LAST_FRAME);
            case RIGHT -> setAnimation(controller, DASH_RIGHT, HOLD_ON_LAST_FRAME);
            case LEFT -> setAnimation(controller, DASH_LEFT, HOLD_ON_LAST_FRAME);
            case UP  -> setAnimation(controller, DASH_UP, HOLD_ON_LAST_FRAME);
        }
        return PlayState.CONTINUE;
    }
}
