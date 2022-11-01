package com.kwpugh.simple_backpack.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.PartitioningSerializer;
import me.shedaniel.cloth.clothconfig.shadowed.blue.endless.jankson.Comment;

@Config(name = "simple_backpack")
public class ModConfig extends PartitioningSerializer.GlobalData
{
    public General GENERAL = new General();

    @Config(name = "general")
    public static class General implements ConfigData
    {
        @Comment("***********************"
                +"\nGeneral Settings"
                +"\nTo bbe safe,"
                +"\nOnly change max storage"
                +"\nif bunndles are empty"
                +"\nUse with caution"
                +"\n***********************")

        public int simpleBundleMaxStorage = 3456;
        public int voidBundleMaxStorage = 3456;
    }
}
