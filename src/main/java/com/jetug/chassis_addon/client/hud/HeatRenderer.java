package com.jetug.chassis_addon.client.hud;

import com.jetug.chassis_core.common.util.helpers.MathHelper;
import com.jetug.generated.resources.Textures;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import static com.jetug.chassis_addon.common.utils.ChassisUtils.getSteamChassis;
import static com.jetug.chassis_addon.common.utils.ChassisUtils.isWearingSteamChassis;
import static com.jetug.chassis_core.common.util.helpers.MathHelper.*;

public class HeatRenderer implements IIngameOverlay
{
    public static final int BAR_OFFSET_X = 95;
    public static final int BG_WIDTH = 44;
    public static final int BG_HEIGHT = 50;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (!isWearingSteamChassis()) return;

        //RenderSystem.setShaderTexture(0, Textures.GUI_HUD);
        //RenderSystem.enableBlend();
        var minecraft = Minecraft.getInstance();
        var screenWidth  = minecraft.getWindow().getGuiScaledWidth();
        var screenHeight = minecraft.getWindow().getGuiScaledHeight();

        var x = screenWidth / 2 + BAR_OFFSET_X;
        var y = height - BG_HEIGHT;

        //gui.blit(poseStack, x, y, 0, 0, BG_WIDTH, BG_HEIGHT);

        var armor = getSteamChassis(minecraft.player);
        int heat = armor.heatController.getHeatInPercent();
        int arrowNumber = (int)getPercentOf(17, heat);

        var offset = 26 * arrowNumber;

        RenderSystem.setShaderTexture(0, Textures.GUI_ARROW);
        gui.blit(poseStack, x + 10, y + 8, 0,  0, 25, 17);
    }

}
