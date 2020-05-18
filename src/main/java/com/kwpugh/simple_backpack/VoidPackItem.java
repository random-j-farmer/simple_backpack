package com.kwpugh.simple_backpack;

import java.util.List;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class VoidPackItem extends Item
{
	public VoidPackItem(Settings settings)
	{
		super(settings);
	}



	@Override
	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
	{
		ItemStack itemStack = player.getStackInHand(hand);
		player.setCurrentHand(hand);

		if (!world.isClient)
		{
            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
               return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, new BasicInventory(27));
            }, new TranslatableText("container.simple_backpack.void_pack.title")));
		}

		return TypedActionResult.success(itemStack);
	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
	    tooltip.add(new TranslatableText("item.simple_backpack.void_pack").formatted(Formatting.YELLOW));
	}
}