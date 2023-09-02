package com.jetug.chassis_addon.client.model.item;

import com.jetug.chassis_addon.common.foundation.item.*;
import com.jetug.generated.resources.*;
import net.minecraft.resources.*;
import software.bernie.geckolib3.model.*;

import static com.jetug.generated.resources.Models.*;

public class FramePartModel extends AnimatedGeoModel<FramePartItem> {
    @Override
    public ResourceLocation getModelLocation(FramePartItem object) {
        return switch (object.bodyPart) {
            case BODY_FRAME -> FRAME_BODY     ;
            case LEFT_ARM_FRAME -> FRAME_LEFT_ARM ;
            case RIGHT_ARM_FRAME -> FRAME_RIGHT_ARM;
            case LEFT_LEG_FRAME -> FRAME_LEFT_LEG ;
            case RIGHT_LEG_FRAME -> FRAME_RIGHT_LEG;
            default -> ARMOR_CHASSIS;
        };
    }

    @Override
    public ResourceLocation getTextureLocation(FramePartItem object) {
        return Textures.ENTITY_ARMOR_CHASSIS;
    }

    @Override
    public ResourceLocation getAnimationFileLocation(FramePartItem animatable) {
        return null;
    }
}
