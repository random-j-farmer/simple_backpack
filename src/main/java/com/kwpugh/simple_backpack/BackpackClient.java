package com.kwpugh.simple_backpack;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class BackpackClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<BackpackScreenHandler>registerFactory(Backpack.BACKPACK_IDENTIFIER, (container -> new BackpackClientScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText(Backpack.BACKPACK_TRANSLATION_KEY))));
    }
}
