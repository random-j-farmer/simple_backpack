package com.kwpugh.simple_backpack.util;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class BackpackScreen extends HandledScreen<BackpackContainer>
{
    public static final Identifier BACKPACK_SLOTS_TEXTURE = new Identifier("simple_backpack", "textures/gui/chest_slots.png");
    public static final Identifier BACKPACK_BACKGROUND_TEXTURE = new Identifier("simple_backpack", "textures/gui/chest.png");

    private BackpackContainer container;

    public BackpackScreen(BackpackContainer container, PlayerInventory inventory, Text title)
    {
        super(container, inventory, title);
        this.container = container;

        this.backgroundWidth = 14 + container.inventoryWidth * 18;
        this.backgroundHeight = 114 + container.inventoryHeight * 18 + 7;
    }

    @Override
    protected void drawForeground(int mouseX, int mouseY)
    {
        this.font.draw(this.title.asFormattedString(), 8.0F, 6.0F, 4210752);
        this.font.draw(this.playerInventory.getDisplayName().asFormattedString(), 8.0F, (float)(this.backgroundHeight - 96 - 3), 4210752);

        super.drawForeground(mouseX, mouseY);

        this.drawMouseoverTooltip(mouseX - x, mouseY - y);
    }

    @Override
    protected void drawBackground(float delta, int mouseX, int mouseY)
    {
        this.renderBackground();
        RenderSystem.disableLighting();

        minecraft.getTextureManager().bindTexture(BACKPACK_BACKGROUND_TEXTURE);

        blit(x, y, 0, 0, 7, 7, 15, 15);
        blit(x + backgroundWidth - 7, y, 8, 0, 7, 7, 15, 15);

        blit(x, y + backgroundHeight - 7, 0, 8, 8, 7, 15, 15);
        blit(x + backgroundWidth - 7, y + backgroundHeight - 7, 8, 8, 7, 7, 15, 15);

        //middle bit
        blit(x + 7, y + 7, backgroundWidth - 14, backgroundHeight - 14, 7, 7, 1, 1, 15, 15);

        //left side
        blit(x, y + 7, 7, backgroundHeight - 14, 0, 7, 7, 1, 15, 15);

        //right side
        blit(x + backgroundWidth - 7, y + 7, 7, backgroundHeight - 14, 8, 7, 7, 1, 15, 15);

        //top
        blit(x + 7, y, backgroundWidth - 14, 7, 7, 0, 1, 7, 15, 15);

        //bottom
        blit(x + 7, y + backgroundHeight - 7, backgroundWidth - 14, 7, 7, 8, 1, 7, 15, 15);

        minecraft.getTextureManager().bindTexture(BACKPACK_SLOTS_TEXTURE);
        //chest slots
        blit(this.x + 7, this.y + 17, 0, 0, 18 * container.inventoryWidth, 18 * container.inventoryHeight, 432, 216);
        //inv slots
        blit(this.x + (backgroundWidth / 2) - 9 * 9, this.y + (container.inventoryHeight * 18) + 18 + 17, 0, 0, 18 * 9, 18 * 3, 432, 216);
        //hotbar slots
        blit(this.x + (backgroundWidth / 2) - 9 * 9, this.y + (container.inventoryHeight * 18) + 18 + 60 + 17, 0, 0, 18 * 9, 18 * 1, 432, 216);
    }
}
