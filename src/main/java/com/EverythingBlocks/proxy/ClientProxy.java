package com.EverythingBlocks.proxy;

import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import com.EverythingBlocks.blocks.BlockEverything;
import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.crafting.CraftableToBlock;

public class ClientProxy extends CommonProxy {
	
	@Override
	public void preInit(FMLPreInitializationEvent event) {
		super.preInit(event);
	}
	
	@Override
	public void onInit(FMLInitializationEvent event) {
		super.onInit(event);

		// rendering
		EBBlocks.registerRendering();
	}
	
	@Override
	public void postInit(FMLPostInitializationEvent event) {
		super.postInit(event);
		
		// get blocks to show in creative tab / NEI
		BlockEverything.eligibleItemStacks = CraftableToBlock.getAllEligibleItemStacks();
	}

}
