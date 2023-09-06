package com.jetug.chassis_addon.common.foundation.item;

import com.jetug.chassis_addon.client.renderers.item.FramePartRenderer;
import com.jetug.chassis_addon.common.foundation.registery.ModCreativeModeTab;
import com.jetug.chassis_core.common.data.enums.ChassisPart;
import com.jetug.chassis_core.common.foundation.item.ChassisEquipment;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraftforge.client.IItemRenderProperties;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.function.Consumer;

import static software.bernie.geckolib3.util.GeckoLibUtil.createFactory;

public class FramePartItem extends ChassisEquipment implements IAnimatable {
    public AnimationFactory factory = createFactory(this);
    public final String bodyPart;
    public FramePartRenderer renderer;

    public FramePartItem(String bodyPart) {
        super(new Properties().stacksTo(1).tab(ModCreativeModeTab.CHASSIS_TAB), bodyPart);
        this.bodyPart = bodyPart;
    }

    @Override
    public void initializeClient(Consumer<IItemRenderProperties> consumer) {
        super.initializeClient(consumer);
        renderer = new FramePartRenderer();

        consumer.accept(new IItemRenderProperties() {
            @Override
            public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                return renderer;
            }
        });
    }

    @Override
    public void registerControllers(AnimationData data) {}

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
