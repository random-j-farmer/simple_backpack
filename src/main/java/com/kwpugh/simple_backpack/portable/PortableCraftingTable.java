package com.kwpugh.simple_backpack.portable;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class PortableCraftingTable extends Item
{
    private static final Text TITLE = Text.translatable("container.simple_backpack.portable_crafting");

    public PortableCraftingTable(Settings settings)
    {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getStackInHand(hand);

        if(!world.isClient && !player.isSneaking())
        {
            player.openHandledScreen(createPortableScreenHandlerFactory(world, player.getBlockPos()));
        }

        return TypedActionResult.success(stack);
    }

    public NamedScreenHandlerFactory createPortableScreenHandlerFactory(World world, BlockPos pos)
    {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new PortableCraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);
    }
}