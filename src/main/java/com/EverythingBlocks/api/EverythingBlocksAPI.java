package com.EverythingBlocks.api;

import com.EverythingBlocks.blocks.WallEverything;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.event.FMLInterModComms;

/** Use this class to reference internal EverythingBlocks stuff
 * @author sblectric */
public class EverythingBlocksAPI {
	
	/** Use to reference the Everything Block. Cast it to a Block to use regular block methods! */
	public static IBlockEverything blockEverything;
	
	/** Use to reference the Everything Slab. */
	public static IBlockEverything slabEverything;
	
	/** Use to reference the Everything Stairs. */
	public static IBlockEverything stairEverything;
	
	/** Use to reference the Everything Wall. */
	public static IBlockEverything wallEverything;
	
	/** Single IRecipe holding all Everything Block recipes (9x item -> block). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe craftingRecipes;
	
	/** Single IRecipe holding all Everything Block recipes (block -> 9x item). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe decraftingRecipes;
	
	/** Single IRecipe holding all Everything Slab recipes (3x Everything Blocks -> 6x Everything Slabs). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe craftingRecipesSlabs;
	
	/** Single IRecipe holding all Everything Stair recipes (6x Everything Blocks -> 4x Everything Stairs). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe craftingRecipesStairs;
	
	/** Single IRecipe holding all Everything Stair recipes (2x Everything Stair -> 1x Everything Block). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe decraftingRecipesStairs;
	
	/** Single IRecipe holding all Everything Wall recipes (6x Everything Blocks -> 6x Everything Walls). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe craftingRecipesWalls;
	
	/** Single IRecipe holding all Everything Wall recipes (1x Everything Wall -> 1x Everything Block). 
	 * You can't modify this recipe, only reference it. */
	public static IRecipe decraftingRecipesWalls;
	
	/** For the following methods, an ItemStack item metadata of OreDictionary.WILDCARD_VALUE sets it for all
	 * items of the specified type. Call all of these methods before post-initialization. */
	
	/** Call this method to remove the Everything Block corresponding to the provided ItemStack.
	 * NOTE: only use this for items you can't get access to (i.e, vanilla items). Otherwise, use 
	 * IHasNoEverythingBlock or IHasEverythingBlockCond! */
	public static void removeEverythingBlockForItemStack(ItemStack stack) {
		NBTTagCompound tag = new NBTTagCompound();
		stack.writeToNBT(tag);
		FMLInterModComms.sendMessage("EverythingBlocks", "stackRemoveBlock", tag);
	}
	
	/** Call this method to override the default color of the specified ItemStack. NOTE: only use 
	 * this for items you can't get access to (i.e, vanilla items). Otherwise, use IOverrideEBColor! */
	public static void setCustomColorForItemStack(ItemStack stack, int colorOverride) {
		NBTTagCompound tag = new NBTTagCompound();
		stack.writeToNBT(tag);
		tag.setInteger("colorOverride", colorOverride);
		FMLInterModComms.sendMessage("EverythingBlocks", "stackColorOverride", tag);
	}

}
