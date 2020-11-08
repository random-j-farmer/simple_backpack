package com.kwpugh.simple_backpack;

import com.kwpugh.simple_backpack.backpack.BackpackClientScreen;
import com.kwpugh.simple_backpack.backpack.BackpackScreenHandler;
import com.kwpugh.simple_backpack.voidpack.VoidPackClientScreen;
import com.kwpugh.simple_backpack.voidpack.VoidPackScreenHandler;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screen.ScreenProviderRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class BackpackClient implements ClientModInitializer
{
     @Override
    public void onInitializeClient()
    {
        ScreenProviderRegistry.INSTANCE.<BackpackScreenHandler>registerFactory(Backpack.BACKPACK_IDENTIFIER, (container -> new BackpackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText(Backpack.BACKPACK_TRANSLATION_KEY))));
        ScreenProviderRegistry.INSTANCE.<VoidPackScreenHandler>registerFactory(Backpack.VOID_PACK_IDENTIFIER, (container -> new VoidPackClientScreen(container, MinecraftClient.getInstance().player.getInventory(), new TranslatableText(Backpack.VOID_PACK_TRANSLATION_KEY))));
    }
}
