package com.kwpugh.simple_backpack;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;

public class Backpack implements ModInitializer
{
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "simple_backpack";
    public static final String MOD_NAME = "SimpleBackpack";
    public static final Identifier BACKPACK_IDENTIFIER = new Identifier(MOD_ID, "backpack");
    public static final String BACKPACK_TRANSLATION_KEY = Util.createTranslationKey("container", BACKPACK_IDENTIFIER);
    public static final Item BACKPACK = new BackpackItem(new Item.Settings().group(ItemGroup.MISC).maxCount(1));

    @Override
    public void onInitialize()
    {
        ContainerProviderRegistry.INSTANCE.registerFactory(BACKPACK_IDENTIFIER, ((syncId, identifier, player, buf) -> {
            final ItemStack stack = buf.readItemStack();
            final Hand hand = buf.readInt() == 0 ? Hand.MAIN_HAND : Hand.OFF_HAND;
            final BackpackInventoryInterface inventory = BackpackItem.getInventory(stack, hand, player);

            return new BackpackScreenHandler(syncId, player.inventory, inventory.getInventory(), inventory.getInventoryWidth(), inventory.getInventoryHeight(), hand);
        }));

        Registry.register(Registry.ITEM, BACKPACK_IDENTIFIER, BACKPACK);
    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }
}