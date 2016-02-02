package com.EverythingBlocks.util;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class EBOreDictionary extends OreDictionary {
	
	public static boolean itemMatchesStrict(ItemStack target, ItemStack input, boolean strict)
    {
        if (input == null && target != null || input != null && target == null)
        {
            return false;
        }
        return (target.getItem() == input.getItem() && 
        		((target.getItemDamage() == WILDCARD_VALUE && !strict) || target.getItemDamage() == input.getItemDamage()) && 
        		(target.getTagCompound().equals(input.getTagCompound())));
    }

}
