package com.jetug.chassis_addon.common.foundation.registery;

import com.jetug.chassis_addon.ChassisAddon;
import com.jetug.chassis_addon.common.foundation.block.ArmorStationBlock;
import com.jetug.chassis_addon.common.foundation.block.CastingTableBlock;
import com.jetug.chassis_core.common.foundation.registery.ItemRegistry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;


public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ChassisAddon.MOD_ID);

    public static final RegistryObject<Block> ARMOR_STATION = registerBlock("armor_station",
            () -> new ArmorStationBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()),
            ModCreativeModeTab.CHASSIS_TAB);

    public static final RegistryObject<Block> CASTING_TABLE = registerBlock("casting_table",
            () -> new CastingTableBlock(BlockBehaviour.Properties.copy(Blocks.IRON_BLOCK).noOcclusion()),
            ModCreativeModeTab.CHASSIS_TAB);


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab){
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block, CreativeModeTab tab){
        return ItemRegistry.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus){
        BLOCKS.register(eventBus);
    }
}
