package com.jetug.chassis_addon.common.foundation.registery;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.block.entity.ArmorStationBlockEntity;
import com.jetug.chassis_addon.common.foundation.block.entity.CastingTableEntity;
import com.jetug.chassis_core.ChassisCore;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.jetug.chassis_addon.common.foundation.registery.BlockRegistry.CASTING_TABLE;

public class BlockEntityRegistry {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, ChassisAddon.MOD_ID);

    public static final RegistryObject<BlockEntityType<ArmorStationBlockEntity>> ARMOR_STATION_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("armor_station_block_entity", () ->
                    BlockEntityType.Builder.of(ArmorStationBlockEntity::new,
                            BlockRegistry.ARMOR_STATION.get()).build(null));

    public static final RegistryObject<BlockEntityType<CastingTableEntity>> CASTING_TABLE_BLOCK_ENTITY =
            BLOCK_ENTITIES.register("casting_table_block_entity", () ->
                    BlockEntityType.Builder.of(CastingTableEntity::new,
                            CASTING_TABLE.get()).build(null));


    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
