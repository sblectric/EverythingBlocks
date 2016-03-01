package com.EverythingBlocks.config;

import java.io.File;

import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;

import com.EverythingBlocks.util.JointList;

public class EBConfig {
	
	public static boolean ignore2x2Recipes;
	public static boolean ignore3x3Recipes;
	public static boolean enableCreativeTabVariants;
	public static boolean showSlabs;
	public static boolean showStairs;
	public static boolean showWalls;
	public static JointList<String> blacklistedMods;
	
	/** Set config defaults */
	private static void setDefaultValues() {
		// crafting
		ignore2x2Recipes = false;
		ignore3x3Recipes = false;
		
		// creative tab
		enableCreativeTabVariants = false;
		showSlabs = false;
		showStairs = false;
		showWalls = false;
		blacklistedMods = new JointList<String>().join("chiselsandbits");
		
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
		p.comment = "Set to false to hide ALL Everything Block variants from the creative tab / NEI / etc.";
		enableCreativeTabVariants = p.getBoolean();
		
		// Show slabs
		p = config.get("General", "Enable creative tab slabs", showSlabs);
		p.comment = "Set to false to hide Everything Slab variants from the creative tab / NEI / etc.";
		showSlabs = p.getBoolean();
		
		// Show stairs
		p = config.get("General", "Enable creative tab stairs", showStairs);
		p.comment = "Set to false to hide Everything Stairs variants from the creative tab / NEI / etc.";
		showStairs = p.getBoolean();
		
		// Show walls
		p = config.get("General", "Enable creative tab walls", showWalls);
		p.comment = "Set to false to hide Everything Wall variants from the creative tab / NEI / etc.";
		showWalls = p.getBoolean();
		
		// Creative tab mod blacklist
		String[] blacklistedModsArray = blacklistedMods.toArray(new String[blacklistedMods.size()]);
		p = config.get("General", "Creative tab mod blacklist", blacklistedModsArray);
		p.comment = "Mods by mod id that should not be shown in the Everything Blocks creative tab / NEI / etc.";
		blacklistedMods = new JointList<String>().join(p.getStringList());
		
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
