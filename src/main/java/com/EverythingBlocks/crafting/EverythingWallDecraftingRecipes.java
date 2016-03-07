package com.EverythingBlocks.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.util.EBUtils;
import com.EverythingBlocks.util.JointList;

/** Recipe for Everything Walls (1x wall -> 1 wall, shapeless) */
public class EverythingWallDecraftingRecipes implements IRecipe {

	/** Does the recipe match as expected? */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return isValidRecipeGrid(inv);
	}
	
	/** Check if the grid is valid (1 of same eligible item) */
	private boolean isValidRecipeGrid(InventoryCrafting inv) {
		JointList<ItemStack> s = new JointList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) s.add(inv.getStackInSlot(i));
		}
		
		// size is 1
		if(s.size() != 1) return false;
		
		// 1 item
		if(Block.getBlockFromItem(s.get(0).getItem()) == EBBlocks.wallEverything) {
			return true;
		}		
		return false;
	}

	/** Result is a new block containing the ItemStack type */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack base = null;
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(base == null) base = inv.getStackInSlot(i);
		}
		if(base == null) return null;
		ItemStack blocks = new ItemStack(EBBlocks.blockEverything, 1);
		blocks.setTagCompound(base.getTagCompound());
		return blocks;
	}

	/** Size of grid */
	@Override
	public int getRecipeSize() {
		return 1;
	}
	
	/** Basic recipe output */
	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(EBBlocks.blockEverything, 1);
	}

	/** No remaining items */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
