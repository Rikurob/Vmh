package com.rikurob.vmh;

import com.mojang.logging.LogUtils;
import com.rikurob.vmh.config.VmhConfig;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Vmh.MOD_ID)
public class Vmh
{
    public static final String MOD_ID = "vmh";
    private static final Logger LOGGER = LogUtils.getLogger();
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VmhConfig.GENERAL_SPEC, "vmh/general.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VmhConfig.MAIN_LIST, "vmh/entity_list.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VmhConfig.ADVANCED_SPEC, "vmh/advanced.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VmhConfig.BUFFS, "vmh/buffs.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, VmhConfig.BIOME_LIST, "vmh/by_biome_list.toml");

    }

    private void commonSetup(final FMLCommonSetupEvent event)
    {
        VmhConfig.loadForgeConfig();
    }

   @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents
    {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event)
        {

        }
    }
}
