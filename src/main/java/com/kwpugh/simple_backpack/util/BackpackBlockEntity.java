package com.kwpugh.simple_backpack.util;

import com.kwpugh.simple_backpack.Backpack;

import net.fabricmc.fabric.api.block.entity.BlockEntityClientSerializable;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.LootableContainerBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;

public class BackpackBlockEntity extends LootableContainerBlockEntity implements BlockEntityClientSerializable, BackpackInventoryImpl
{
    private DefaultedList<ItemStack> inventory;

    public int inventory_width = 9;
    public int inventory_height = 6;

    public int players_using = 0;

    public BackpackBlockEntity()
    {
        super(Backpack.BACKPACK_ENTITY_TYPE);
        this.inventory = DefaultedList.ofSize(inventory_width * inventory_height, ItemStack.EMPTY);
    }

    @Override
    protected Text getContainerName()
    {
        return new TranslatableText("container.simple_backpack.backpack");
    }

    @Override
    protected ScreenHandler createScreenHandler(int syncId, PlayerInventory playerInventory)
    {
        return new BackpackHandler(syncId, playerInventory, (Inventory) this.inventory, inventory_width, inventory_height, null);
    }


    @Override
    protected DefaultedList<ItemStack> getInvStackList()
    {
        return inventory;
    }

    @Override
    protected void setInvStackList(DefaultedList<ItemStack> list)
    {
        this.inventory = list;
    }

    @Override
    public int size()
    {
        return inventory_width * inventory_height;
    }

    @Override
    public void onOpen(PlayerEntity player)
    {
        super.onOpen(player);

        if(!player.isSpectator())
        {
            players_using++;
        }
    }

    @Override
    public void onClose(PlayerEntity player)
    {
        super.onClose(player);

        if(!player.isSpectator())
        {
            players_using--;
        }
    }

    public void resizeInventory(boolean copy_contents)
    {
        DefaultedList<ItemStack> new_inventory = DefaultedList.ofSize(inventory_width * inventory_height, ItemStack.EMPTY);

        if(copy_contents)
        {
            DefaultedList<ItemStack> list = this.inventory;

            for(int i = 0; i < list.size(); i++)
            {
                new_inventory.set(i, list.get(i));
            }
        }

        this.inventory = new_inventory;
    }

    @Override
    public void fromTag(BlockState state, CompoundTag tag)
    {
        super.fromTag(state, tag);

        inventory_width = tag.contains("inventory_width") ? tag.getInt("inventory_width") : 9;
        inventory_height = tag.contains("inventory_height") ? tag.getInt("inventory_height") : 6;

        this.inventory = DefaultedList.ofSize(inventory_width * inventory_height, ItemStack.EMPTY);
        readItemsFromTag(this.inventory, tag);
    }

    @Override
    public CompoundTag toTag(CompoundTag tag)
    {
        super.toTag(tag);

        writeItemsToTag(inventory, tag);

        tag.putInt("inventory_width", inventory_width);
        tag.putInt("inventory_height", inventory_height);

        return tag;
    }

    @Override
    public CompoundTag toClientTag(CompoundTag tag)
    {
        return toTag(tag);
    }

    @Override
    public int getInventoryWidth()
    {
        return inventory_width;
    }

    @Override
    public int getInventoryHeight()
    {
        return inventory_height;
    }

	@Override
	public void fromClientTag(CompoundTag tag)
	{
		// TODO Auto-generated method stub

	}
}
