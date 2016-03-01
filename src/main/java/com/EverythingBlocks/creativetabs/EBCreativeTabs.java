package com.EverythingBlocks.creativetabs;

import net.minecraft.creativetab.CreativeTabs;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.config.EBConfig;

public class EBCreativeTabs {
	
	public static CreativeTabs main;
	
	public static void mainRegistry() {
		registerCreativeTabs();
	}
	
	private static void registerCreativeTabs() {
		if(EBConfig.enableCreativeTabVariants) {
			main = new EBCreativeTab("ebMain");
		} else {
			main = CreativeTabs.tabDecorations;
		}
	}
	
	public static void updateCreativeTabs() {
		if(EBConfig.enableCreativeTabVariants) ((EBCreativeTab)main).updateItem(EBBlocks.blockEverything);
	}

}
