package com.jetug.chassis_addon.common.foundation.registery;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.entity.PowerArmorFrame;
import com.jetug.chassis_core.common.data.constants.Global;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityTypeRegistry {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, ChassisAddon.MOD_ID);

    public static final RegistryObject<EntityType<PowerArmorFrame>> POWER_ARMOR_FRAME =
            registerEntity("power_armor_frame", EntityType.Builder
                    .of(PowerArmorFrame::new, MobCategory.MISC)
                    .sized(1.0f, 2.3f));


    private static <T extends Entity> RegistryObject<EntityType<T>> registerEntity(String entityName, EntityType.Builder<T> builder) {
        return ENTITY_TYPES.register(entityName, () -> builder.build(new ResourceLocation(ChassisAddon.MOD_ID, entityName).toString()));
    }

    public static void register(IEventBus eventBus){
        ENTITY_TYPES.register(eventBus);
    }
}