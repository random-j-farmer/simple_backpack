package com.kwpugh.simple_backpack;

import com.kwpugh.simple_backpack.portable.PortableCraftingScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.item.ModelPredicateProviderRegistry;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class BackpackClient implements ClientModInitializer
{
     @Override
    public void onInitializeClient()
    {
        HandledScreens.register(Backpack.BACKPACK_SCREEN_HANDLER, GenericContainerScreen::new);
        HandledScreens.register(Backpack.VOID_PACK_SCREEN_HANDLER, GenericContainerScreen::new);
        HandledScreens.register(Backpack.PORTABLE_CRAFTING_SCREEN_HANDLER, PortableCraftingScreen::new);

        ModelPredicateProviderRegistry.register(Backpack.SIMPLE_BUNDLE, new Identifier("filled"), (itemStack, clientWorld, livingEntity, i) -> Backpack.SIMPLE_BUNDLE.getItemBarStep(itemStack));
    }
}
