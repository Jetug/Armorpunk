package com.jetug.chassis_addon.client.hud;

import com.jetug.generated.resources.Textures;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;

import static com.jetug.chassis_addon.common.utils.ChassisUtils.getSteamChassis;
import static com.jetug.chassis_addon.common.utils.ChassisUtils.isWearingSteamChassis;

public class HeatRenderer implements IIngameOverlay
{
    public static final int BAR_HEIGHT = 52;
    public static final int BAR_WIDTH = 95;

    @Override
    public void render(ForgeIngameGui gui, PoseStack poseStack, float partialTick, int width, int height) {
        if (!isWearingSteamChassis()) return;

        RenderSystem.setShaderTexture(0, Textures.GUI_HUD);
        RenderSystem.enableBlend();
        var minecraft = Minecraft.getInstance();
        var screenWidth  = minecraft.getWindow().getGuiScaledWidth();
        var screenHeight = minecraft.getWindow().getGuiScaledHeight();

        var x = screenWidth / 2 + BAR_WIDTH;
        var y = height - BAR_HEIGHT;

        gui.blit(poseStack, x, y, 0, 0, 9, BAR_HEIGHT);

        //RenderSystem.setShaderTexture(0, ICONS_LOCATION);
        var armor = getSteamChassis(minecraft.player);
        int heat = armor.heatController.getHeatInPercent() / 2;

        var offset = 50 - heat + 1;

        gui.blit(poseStack, x + 1, y + offset, 10,  offset, 7, heat);

    }

}
