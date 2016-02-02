package com.EverythingBlocks.creativetabs;

import com.EverythingBlocks.blocks.EBBlocks;

public class EBCreativeTabs {
	
	public static EBCreativeTab main;
	
	public static void mainRegistry() {
		registerCreativeTabs();
	}
	
	private static void registerCreativeTabs() {
		main = new EBCreativeTab("ebMain");
	}
	
	public static void updateCreativeTabs() {
		main.updateItem(EBBlocks.blockEverything);
	}

}
