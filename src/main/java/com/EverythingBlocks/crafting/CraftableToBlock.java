package com.EverythingBlocks.crafting;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameData;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import com.EverythingBlocks.api.IHasEverythingBlockCond;
import com.EverythingBlocks.api.IHasNoEverythingBlock;
import com.EverythingBlocks.api.IOverrideEBSubtypes;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.util.JointList;

/** Class that handles block craftability */
public class CraftableToBlock {
	
	/** Gets the list of possible blocks that can be crafted (clientside only) */
	@SideOnly(Side.CLIENT)
	public static List getAllEligibleItemStacks() {
		JointList<ItemStack> list = new JointList<ItemStack>();
		if(!EBConfig.enableCreativeTabVariants) return list;
		
		// iterate through all the items
		Iterable<Item> allItems;
		allItems = GameData.getItemRegistry().typeSafeIterable();
		for(Item i : allItems) {
			// get all of the exposed subitems for this item
			JointList<ItemStack> j = new JointList<ItemStack>();
			if(i instanceof IOverrideEBSubtypes) {
				((IOverrideEBSubtypes)i).getEBSubtypes(i, j);
			} else {
				i.getSubItems(i, CreativeTabs.tabAllSearch, j);
			}
			for(ItemStack s : j) {
				// check each one for eligibility
				ItemStack dupe = s.copy();
				dupe.stackSize = 9; // the amount of blocks to hold
				if(isItemStackValid(s)) {
					if(isItemStackCraftable(s)) list.join(dupe); // add to the list
				}
			}
		}
		
		return list;
	}
	
	// blacklists
	private static JointList<String> stackBlacklist = new JointList();
	private static JointList<String> itemBlacklist = new JointList();
	
	/** Blacklist an ItemStack from having an Everything Block */
	public static void blacklistStack(ItemStack stack) {
		if(stack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			itemBlacklist.join(stack.getItem().toString());
		} else {
			stackBlacklist.join(stack.toString());
		}
	}
	
	/** Combination condition */
	public static boolean isItemStackValidAndCraftable(ItemStack s) {
		return isItemStackValid(s) && isItemStackCraftable(s);
	}
	
	/** Condition: can't be a block or incompatible item */
	private static boolean isItemStackValid(ItemStack s) {
		Item i = s.getItem();
		// no blocks or excluded items
		boolean negcond = i instanceof ItemBlock || i instanceof ItemSkull || i instanceof IHasNoEverythingBlock;
		negcond |= i instanceof IHasEverythingBlockCond && !((IHasEverythingBlockCond)i).hasEverythingBlock(s);
		negcond |= itemBlacklist.contains(s.getItem().toString()) || stackBlacklist.contains(s.toString());
		return !negcond;
	}
	
	/** Condition: Can't have a 2x2 or 3x3 crafting recipe to an existing block */
	private static boolean isItemStackCraftable(ItemStack s) {
		boolean negcond = false;
		
		// check 2x2 recipes
		if(!EBConfig.ignore2x2Recipes) {
			JointList<ItemStack> a = new JointList().join(s, 4);
			negcond |= hasMatchingRecipe(2, a);
		}
		
		// check 3x3 recipes
		if(!EBConfig.ignore3x3Recipes) {
			JointList<ItemStack> b = new JointList().join(s, 9);
			negcond |= hasMatchingRecipe(3, b);
		}
		
		return !negcond;
	}
	
	/** See if there is a matching recipe for the amount of contents */
	private static boolean hasMatchingRecipe(int size, List<ItemStack> contents) {
		EBInventoryCrafting i = new EBInventoryCrafting(size, size, contents);
		return EBCraftingManager.findMatchingRecipeExcluding(i, null, EverythingBlockCraftingRecipes.class) != null;
	}

}
