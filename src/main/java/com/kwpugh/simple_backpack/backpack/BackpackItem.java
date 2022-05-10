package com.kwpugh.simple_backpack.backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class BackpackItem extends Item
{
    public BackpackItem(Settings settings)
    {
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
        player.setCurrentHand(hand);
        ItemStack stack = player.getStackInHand(hand);

        if(!world.isClient && !player.isSneaking())
        {
            player.openHandledScreen(createScreenHandlerFactory(stack));
            return TypedActionResult.success(player.getStackInHand(hand));
        }

        return TypedActionResult.pass(player.getStackInHand(hand));
    }

    private NamedScreenHandlerFactory createScreenHandlerFactory(ItemStack stack)
    {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) ->
                new BackpackScreenHandler(syncId, inventory, new BackpackInventory(stack)), stack.getName());
    }
}