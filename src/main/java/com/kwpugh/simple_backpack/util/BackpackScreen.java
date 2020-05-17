package com.kwpugh.simple_backpack.util;

import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BackpackScreen extends HandledScreen<BackpackHandler>
{
    private final int rows;
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/generic_54.png");

    public BackpackScreen(BackpackHandler handler, PlayerInventory inventory, Text title)
    {
        super(handler, inventory, title);
        this.passEvents = false;

        this.rows = 6;
        this.backgroundHeight = 114 + this.rows * 18;
     }

    protected void drawForeground(MatrixStack matrixStack, int i, int j)
    {
        this.textRenderer.draw(matrixStack, this.title, 8.0F, 6.0F, 4210752);
        this.textRenderer.draw(matrixStack, this.playerInventory.getDisplayName(), 8.0F, (float)(this.backgroundHeight - 96 + 2), 4210752);
    }

	@Override
	protected void drawBackground(MatrixStack matrixStack, float f, int mouseY, int i)
	{
	      RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
	      this.client.getTextureManager().bindTexture(TEXTURE);
	      int j = (this.width - this.backgroundWidth) / 2;
	      int k = (this.height - this.backgroundHeight) / 2;
	      this.drawTexture(matrixStack, j, k, 0, 0, this.backgroundWidth, this.rows * 18 + 17);
	      this.drawTexture(matrixStack, j, k + this.rows * 18 + 17, 0, 126, this.backgroundWidth, 96);
	}
}
