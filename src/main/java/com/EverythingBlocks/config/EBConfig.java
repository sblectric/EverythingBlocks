package com.EverythingBlocks.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

public class EBConfig {
	
	public static boolean ignore2x2Recipes;
	public static boolean ignore3x3Recipes;
	public static boolean enableCreativeTabVariants;
	
	/** Set config defaults */
	private static void setDefaultValues() {
		// crafting
		ignore2x2Recipes = false;
		ignore3x3Recipes = false;
		
		// creative tab
		enableCreativeTabVariants = true;
	}
	
	/** Load the mod config */
	public static void loadConfig(File f) {
		
		// load the config file and default values
		Configuration config = new Configuration(f);
		setDefaultValues();
		config.load();
		Property p;
		
		// Creative tab variants
		p = config.get("General", "Enable creative tab variants", enableCreativeTabVariants);
		p.comment = "Set to false to hide Everything Block variants from the creative tab / NEI / etc. Might be a workaround for certain crashes.";
		enableCreativeTabVariants = p.getBoolean();
		
		// 2x2 recipe settings
		p = config.get("Crafting", "Ignore 2x2 recipes", ignore2x2Recipes);
		p.comment = "Set to true to be able to craft Everything Blocks made from items that have existing 2x2 crafting recipes.";
		ignore2x2Recipes = p.getBoolean();
		
		// 3x3 recipe settings
		p = config.get("Crafting", "Ignore 3x3 recipes", ignore3x3Recipes);
		p.comment = "Set to true to be able to craft Everything Blocks made from items that have existing 3x3 crafting recipes (not recommended).";
		ignore3x3Recipes = p.getBoolean();
		
		// write all to disk
		config.save();
	}

}
