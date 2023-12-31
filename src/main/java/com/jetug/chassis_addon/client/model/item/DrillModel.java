package com.jetug.chassis_addon.client.model.item;

import com.jetug.chassis_addon.common.foundation.item.DrillItem;
import com.jetug.generated.resources.Animations;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.jetug.generated.resources.Models.DRILL;
import static com.jetug.generated.resources.Textures.ITEM_DRILL;

public class DrillModel extends AnimatedGeoModel<DrillItem> {
    @Override
    public ResourceLocation getModelLocation(DrillItem DrillItem) {
        return DRILL;
    }

    @Override
    public ResourceLocation getTextureLocation(DrillItem DrillItem) {
        return ITEM_DRILL;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(DrillItem animatable) {
        return Animations.DRILL;
    }
}
