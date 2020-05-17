package com.kwpugh.simple_backpack;

import com.kwpugh.simple_backpack.util.BackpackContainer;
import com.kwpugh.simple_backpack.util.BackpackScreen;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

public class BackpackClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<BackpackContainer>registerFactory(Backpack.BACKPACK_IDENTIFIER, (container -> new BackpackScreen(container, MinecraftClient.getInstance().player.inventory, new TranslatableText(Backpack.BACKPACK_TRANSLATION_KEY))));
    }
}
