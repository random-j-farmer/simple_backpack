package com.kwpugh.simple_backpack.voidpack;

import java.util.List;

import com.kwpugh.simple_backpack.Backpack;

import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
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
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand)
    {
        if(!world.isClient)
        {
            ContainerProviderRegistry.INSTANCE.openContainer(Backpack.VOID_PACK_IDENTIFIER, user, buf -> {
                buf.writeItemStack(user.getStackInHand(hand));
                buf.writeInt(hand == Hand.MAIN_HAND ? 0 : 1);
            });
        }

        return super.use(world, user, hand);
    }

    public static VoidPackInventory getInventory(ItemStack stack, Hand hand, PlayerEntity player)
    {
        if(!stack.hasTag())
        {
            stack.setTag(new NbtCompound());
        }

        if(!stack.getTag().contains("void_pack"))
        {
            stack.getTag().put("void_pack", new NbtCompound());
        }

        return new VoidPackInventory(stack.getTag().getCompound("void_pack"), hand, player);
    }




//	@Override
//	public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand)
//	{
//		ItemStack itemStack = player.getStackInHand(hand);
//		player.setCurrentHand(hand);
//
//		if (!world.isClient)
//		{
//            player.openHandledScreen(new SimpleNamedScreenHandlerFactory((i, playerInventory, playerEntity) -> {
//               return GenericContainerScreenHandler.createGeneric9x3(i, playerInventory, new BasicInventory(27));
//            }, new TranslatableText("container.simple_backpack.void_pack.title")));
//		}
//
//		return TypedActionResult.success(itemStack);
//	}

	@Override
	public void appendTooltip(ItemStack itemStack, World world, List<Text> tooltip, TooltipContext tooltipContext)
	{
	    tooltip.add(new TranslatableText("item.simple_backpack.void_pack").formatted(Formatting.YELLOW));
	}
}