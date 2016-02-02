package com.EverythingBlocks.integration;

import net.minecraftforge.fml.common.event.FMLInterModComms;

/** Class for mod integration */
public class EBModIntegration {
	
	/** Mods that need integration in the init stage */
	public static void onInit() {
		wailaIntegration();
	}
	
	/** Mods that need integration in the post-init stage */
	public static void postInit() {
		// TODO Auto-generated method stub
	}
	
	/** Waila integration (fix for how it renders the tooltip for blocks) */
	private static void wailaIntegration() {
		FMLInterModComms.sendMessage("Waila", "register", WailaTileHandler.callbackRegister);
	}

}
