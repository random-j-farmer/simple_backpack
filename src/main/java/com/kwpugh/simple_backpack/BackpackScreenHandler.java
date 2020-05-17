package com.kwpugh.simple_backpack;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class BackpackScreenHandler extends ScreenHandler
{
    private final Inventory inventory;
    private final PlayerInventory playerInventory;

    //public BackpackBlockEntity blockEntity;

    public final int inventoryWidth;
    public final int inventoryHeight;

    private ItemStack backpack;

    public BackpackScreenHandler(final int syncId, final PlayerInventory playerInventory, final Inventory inventory, final int inventoryWidth, final int inventoryHeight, final Hand hand)
    {
        super(null, syncId);
        this.inventory = inventory;
        this.playerInventory = playerInventory;
        this.inventoryWidth = inventoryWidth;
        this.inventoryHeight = inventoryHeight;

//        if(inventory instanceof BackpackBlockEntity)
//        {
//            this.blockEntity = (BackpackBlockEntity) inventory;
//            this.backpack = null;
//        } else if(inventory instanceof BackpackInventory)
//        {
//            this.backpack = playerInventory.player.getStackInHand(hand);
//            this.blockEntity = null;
//        }

        checkSize(inventory, inventoryWidth * inventoryHeight);
        inventory.onOpen(playerInventory.player);

        setupSlots(false);
    }

    //Prevents removing the backpack from the hotbar
    @Override
    public ItemStack onSlotClick(int slotNumber, int button, SlotActionType action, PlayerEntity player)
    {
    	if (this.getStacks().get(slotNumber).getItem() instanceof BackpackItem)
        {
            return ItemStack.EMPTY;
        }
        else
        {
        	return super.onSlotClick(slotNumber, button, action, player);
        	//return transferSlot(player, slotNumber);
        }
    }

    @Override
    public void close(final PlayerEntity player)
    {
        super.close(player);
        inventory.onClose(player);
    }

    public void setupSlots(final boolean includeChestInventory)
    {
        int i = (this.inventoryHeight - 4) * 18;

        int n;
        int m;
        for(n = 0; n < this.inventoryHeight; ++n)
        {
           for(m = 0; m < 9; ++m)
           {
              this.addSlot(new Slot(inventory, m + n * 9, 8 + m * 18, 18 + n * 18));
           }
        }

        for(n = 0; n < 3; ++n)
        {
           for(m = 0; m < 9; ++m)
           {
              this.addSlot(new Slot(playerInventory, m + n * 9 + 9, 8 + m * 18, 103 + n * 18 + i));
           }
        }

        for(n = 0; n < 9; ++n)
        {
           this.addSlot(new Slot(playerInventory, n, 8 + n * 18, 161 + i));
        }
    }

    @Override
    public boolean canUse(final PlayerEntity player)
    {
        return this.inventory.canPlayerUse(player);
    }

    @Override
    public ItemStack transferSlot(final PlayerEntity player, final int invSlot)
    {
        ItemStack newStack = ItemStack.EMPTY;
        final Slot slot = this.slots.get(invSlot);
        final ItemStack originalStack = slot.getStack();
        Item testItem = originalStack.getItem();

        if (slot != null && slot.hasStack() && testItem != Backpack.BACKPACK)  //make sure backpack don't into backpacks
        {
	        newStack = originalStack.copy();
	        if (invSlot < this.inventory.size())
	        {
	            if (!this.insertItem(originalStack, this.inventory.size(), this.slots.size(), true))
	            {
	                return ItemStack.EMPTY;
	            }
	        }
	        else if (!this.insertItem(originalStack, 0, this.inventory.size(), false))
	        {
	            return ItemStack.EMPTY;
	        }

	        if (originalStack.isEmpty())
	        {
	            slot.setStack(ItemStack.EMPTY);
	        }
	        else
	        {
	            slot.markDirty();
	        }
        }

        return newStack;
    }
}
