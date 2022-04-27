package com.kwpugh.simple_backpack;

import com.kwpugh.simple_backpack.backpack.BackpackItem;
import com.kwpugh.simple_backpack.backpack.BackpackScreenHandler;
import com.kwpugh.simple_backpack.bundle.SimpleBundleItem;
import com.kwpugh.simple_backpack.bundle.VoidBundleItem;
import com.kwpugh.simple_backpack.config.ModConfig;
import com.kwpugh.simple_backpack.enderpack.EnderPackItem;
import com.kwpugh.simple_backpack.portable.PortableCraftingScreenHandler;
import com.kwpugh.simple_backpack.portable.PortableCraftingTable;
import com.kwpugh.simple_backpack.voidpack.VoidpackItem;
import com.kwpugh.simple_backpack.voidpack.VoidpackScreenHandler;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.JanksonConfigSerializer;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Backpack implements ModInitializer
{
    public static Logger LOGGER = LogManager.getLogger();

    public static final String MOD_ID = "simple_backpack";
    public static final String MOD_NAME = "SimpleBackpack";
    public static final ItemGroup SIMPLE_BACKPACK_GROUP = FabricItemGroupBuilder.build(new Identifier(MOD_ID, "simple_backpack_group"), () -> new ItemStack(Backpack.BACKPACK));
    public static final ModConfig CONFIG = AutoConfig.register(ModConfig.class, PartitioningSerializer.wrap(JanksonConfigSerializer::new)).getConfig();

    public static final Identifier ENDER_PACK_IDENTIFIER = new Identifier(MOD_ID, "ender_pack");

    public static ScreenHandlerType<BackpackScreenHandler> BACKPACK_SCREEN_HANDLER;
    public static ScreenHandlerType<VoidpackScreenHandler> VOID_PACK_SCREEN_HANDLER;
    public static ScreenHandlerType<PortableCraftingScreenHandler> PORTABLE_CRAFTING_SCREEN_HANDLER;

    public static final Item BACKPACK = new BackpackItem(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1));
    public static final Item VOID_PACK = new VoidpackItem(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1));
    public static final Item ENDER_PACK = new EnderPackItem(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1));
    public static final Item SIMPLE_BUNDLE = new SimpleBundleItem(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1),  3456);
    public static final Item VOID_BUNDLE = new VoidBundleItem(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1),  3456);
    public static final Item PORTABLE_CRAFTER = new PortableCraftingTable(new Item.Settings().group(Backpack.SIMPLE_BACKPACK_GROUP).maxCount(1));

    @Override
    public void onInitialize()
    {
        BACKPACK_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, createID("backpack"), new ScreenHandlerType<>(BackpackScreenHandler::new));
        VOID_PACK_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, createID("void_pack"), new ScreenHandlerType<>(VoidpackScreenHandler::new));
        PORTABLE_CRAFTING_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, createID("portable_crafting"), new ScreenHandlerType<>(PortableCraftingScreenHandler::new));

        Registry.register(Registry.ITEM, createID("backpack"), BACKPACK);
        Registry.register(Registry.ITEM, createID("void_pack"), VOID_PACK);
        Registry.register(Registry.ITEM, createID("portable_crafter"), PORTABLE_CRAFTER);

        Registry.register(Registry.ITEM, ENDER_PACK_IDENTIFIER, ENDER_PACK);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "simple_bundle"), SIMPLE_BUNDLE);
        Registry.register(Registry.ITEM, new Identifier(MOD_ID, "void_bundle"), VOID_BUNDLE);
    }

    public static void log(Level level, String message)
    {
        LOGGER.log(level, "["+MOD_NAME+"] " + message);
    }

    public static Identifier createID(String path)
    {
        return new Identifier(MOD_ID, path);
    }
}