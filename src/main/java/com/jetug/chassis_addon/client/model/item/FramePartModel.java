package com.jetug.chassis_addon.client.model.item;

import com.jetug.chassis_addon.common.foundation.item.FramePartItem;
import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.model.AnimatedGeoModel;

import static com.jetug.chassis_core.common.data.constants.Resources.*;
import static com.jetug.generated.resources.Models.ARMOR_CHASSIS;

public class FramePartModel extends AnimatedGeoModel<FramePartItem> {
    @Override
    public ResourceLocation getModelLocation(FramePartItem object) {
        return switch (object.bodyPart) {
            case BODY_FRAME -> FRAME_MODEL_BODY     ;
            case LEFT_ARM_FRAME -> FRAME_MODEL_LEFT_ARM ;
            case RIGHT_ARM_FRAME -> FRAME_MODEL_RIGHT_ARM;
            case LEFT_LEG_FRAME -> FRAME_MODEL_LEFT_LEG ;
            case RIGHT_LEG_FRAME -> FRAME_MODEL_RIGHT_LEG;
            default -> ARMOR_CHASSIS;
        };
    }

    @Override
    public ResourceLocation getTextureLocation(FramePartItem object) {
        return FRAME_TEXTURE_LOCATION;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FramePartItem animatable) {
        return null;
    }
}
