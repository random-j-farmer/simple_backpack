package com.kwpugh.simple_backpack.voidpack;

import com.kwpugh.simple_backpack.api.ImplementedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

final class VoidpackInventory implements ImplementedInventory
{
    private final ItemStack stack;
    private final DefaultedList<ItemStack> items = DefaultedList.ofSize(54, ItemStack.EMPTY);

    VoidpackInventory(ItemStack stack)
    {
        this.stack = stack;
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return items;
    }

    @Override
    public void markDirty()
    {
        // Nothing, its garbage!
    }
}