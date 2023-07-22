package com.kwpugh.simple_backpack.portable;

import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.TexturedButtonWidget;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

/*
    Based on vanilla CraftingScreen class
 */
@Environment(EnvType.CLIENT)
public class PortableCraftingScreen extends HandledScreen<PortableCraftingScreenHandler> implements RecipeBookProvider {
    private static final Identifier TEXTURE = new Identifier("textures/gui/container/crafting_table.png");
    private static final Identifier RECIPE_BUTTON_TEXTURE = new Identifier("textures/gui/recipe_button.png");
    private final RecipeBookWidget recipeBook = new RecipeBookWidget();
    private boolean narrow;

    public PortableCraftingScreen(PortableCraftingScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    protected void init() {
        super.init();
        this.narrow = this.width < 379;
        this.recipeBook.initialize(this.width, this.height, this.client, this.narrow, (AbstractRecipeScreenHandler)this.handler);
        this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
        this.addDrawableChild(new TexturedButtonWidget(this.x + 5, this.height / 2 - 49, 20, 18, 0, 0, 19, RECIPE_BUTTON_TEXTURE, (button) -> {
            this.recipeBook.toggleOpen();
            this.x = this.recipeBook.findLeftEdge(this.width, this.backgroundWidth);
            ((TexturedButtonWidget)button).setPosition(this.x + 5, this.height / 2 - 49);
        }));
        this.addSelectableChild(this.recipeBook);
        this.setInitialFocus(this.recipeBook);
        this.titleX = 29;
    }

    public void handledScreenTick() {
        super.handledScreenTick();
        this.recipeBook.update();
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context);
        if (this.recipeBook.isOpen() && this.narrow) {
            this.drawBackground(context, delta, mouseX, mouseY);
            this.recipeBook.render(context, mouseX, mouseY, delta);
        } else {
            this.recipeBook.render(context, mouseX, mouseY, delta);
            super.render(context, mouseX, mouseY, delta);
            this.recipeBook.drawGhostSlots(context, this.x, this.y, true, delta);
        }

        this.drawMouseoverTooltip(context, mouseX, mouseY);
        this.recipeBook.drawTooltip(context, this.x, this.y, mouseX, mouseY);
    }
    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        this.renderWithTooltip(context, mouseX, mouseY, delta);
    }

    protected boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        return (!this.narrow || !this.recipeBook.isOpen()) && super.isPointWithinBounds(x, y, width, height, pointX, pointY);
    }

    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.recipeBook.mouseClicked(mouseX, mouseY, button)) {
            this.setFocused(this.recipeBook);
            return true;
        } else {
            return this.narrow && this.recipeBook.isOpen() ? true : super.mouseClicked(mouseX, mouseY, button);
        }
    }

    protected boolean isClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button) {
        boolean bl = mouseX < (double)left || mouseY < (double)top || mouseX >= (double)(left + this.backgroundWidth) || mouseY >= (double)(top + this.backgroundHeight);
        return this.recipeBook.isClickOutsideBounds(mouseX, mouseY, this.x, this.y, this.backgroundWidth, this.backgroundHeight, button) && bl;
    }

    protected void onMouseClick(Slot slot, int slotId, int button, SlotActionType actionType) {
        super.onMouseClick(slot, slotId, button, actionType);
        this.recipeBook.slotClicked(slot);
    }

    public void refreshRecipeBook() {
        this.recipeBook.refresh();
    }

    public void removed() {
        //this.recipeBook.close();
        super.removed();
    }

    public RecipeBookWidget getRecipeBookWidget() {
        return this.recipeBook;
    }
}
