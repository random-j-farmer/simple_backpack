package com.kwpugh.simple_backpack.voidpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidpackItem extends Item
{
    public VoidpackItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean canBeNested()
    {
        return false;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
    {
        ItemStack stack = player.getStackInHand(hand);

        if(!world.isClient)
        {
            player.openHandledScreen(createScreenHandlerFactory(stack));
        }

        return TypedActionResult.success(stack);
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack)
    {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new VoidpackScreenHandler(syncId, inventory, new VoidpackInventory(stack)), stack.getName());
    }
}