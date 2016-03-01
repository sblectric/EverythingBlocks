package com.EverythingBlocks.util;

import java.util.HashMap;
import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry.UniqueIdentifier;

/** Forge-related utilities */
public class ForgeUtils {
	
	/** Cache for fast lookups */
	private static HashMap<String, String> nameCache = new HashMap();
	private static final String ERROR = "<none>";
	
	/** Get the name of a mod from its mod ID (non-case sensitive) */
	public static String getModNameFromID(String modid) {
		modid = modid.toLowerCase();
		if(modid.equals("minecraft")) return "Minecraft";
		if(nameCache.containsKey(modid)) {
			return nameCache.get(modid);
		} else {
			List<ModContainer> modlist = Loader.instance().getModList();
			for(ModContainer m : modlist) {
				if(m.getModId().toLowerCase().equals(modid)) {
					nameCache.put(modid, m.getName());
					return m.getName();
				}
			}
			return ERROR;
		}
	}
	
	/** Get the name of the mod the item is from */
	public static String getModNameFromItem(Item item) {
		UniqueIdentifier u = GameRegistry.findUniqueIdentifierFor(item);
		if(u != null) {
			return getModNameFromID(u.modId);
		} else {
			return ERROR;
		}
	}

}
