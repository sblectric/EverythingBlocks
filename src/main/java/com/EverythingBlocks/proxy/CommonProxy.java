package com.EverythingBlocks.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.crafting.EBCraftingManager;
import com.EverythingBlocks.creativetabs.EBCreativeTabs;
import com.EverythingBlocks.handler.BlockEvents;
import com.EverythingBlocks.integration.EBModIntegration;
import com.EverythingBlocks.items.EBItems;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;

public class CommonProxy {
	
	public void preInit(FMLPreInitializationEvent event) {
		EBConfig.loadConfig(event.getSuggestedConfigurationFile());
		EBCreativeTabs.mainRegistry();
	}
	
	public void onInit(FMLInitializationEvent event) {
		// register things
		EBItems.mainRegistry();
		EBBlocks.mainRegistry();
		EBCreativeTabs.updateCreativeTabs();
		GameRegistry.registerTileEntity(TileEntityBlockEverything.class, "TileEntityBlockEverything");
		EBModIntegration.onInit();
	}
	
	public void postInit(FMLPostInitializationEvent event) {
		MinecraftForge.EVENT_BUS.register(new BlockEvents());
		EBCraftingManager.addRecipes();
		EBModIntegration.postInit();
	}
			
}
