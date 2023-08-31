package com.jetug.chassis_addon.common.foundation.container.screen;

import com.jetug.chassis_core.common.data.constants.Resources;
import com.jetug.chassis_core.common.foundation.container.menu.ArmorStationMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

import static com.mojang.blaze3d.systems.RenderSystem.*;

public class ArmorStationScreen extends AbstractContainerScreen<ArmorStationMenu> {
    private final ArmorStationMenu menu;

    public ArmorStationScreen(ArmorStationMenu menu, Inventory pPlayerInventory, Component pTitle) {
        super(menu, pPlayerInventory, pTitle);
        this.menu = menu;
        this.imageHeight = 187;
        this.inventoryLabelY = imageHeight - 94;
    }

    @Override
    public Component getTitle() {
        return super.getTitle();
    }

    @Override
    public void render(PoseStack pPoseStack, int mouseX, int mouseY, float delta) {
        renderBackground(pPoseStack);
        super.render(pPoseStack, mouseX, mouseY, delta);
        renderTooltip(pPoseStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(PoseStack pPoseStack, float pPartialTick, int pMouseX, int pMouseY) {
        setShader(GameRenderer::getPositionTexShader);
        setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        setShaderTexture(0, Resources.ARMOR_STATION_GUI);

        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;
        this.blit(pPoseStack, x, y, 0, 0, imageWidth, imageHeight);

//        if(menu.blockEntity.frame != null)
//            blit(pPoseStack, x + 160, y + 5, 178, 0, 6, 6);
//        else{
            blit(pPoseStack, x + 160, y + 5, 178, 6, 6, 6);
//        }
    }
}