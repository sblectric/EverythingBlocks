package com.EverythingBlocks.crafting;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.items.ItemBlockEverything;
import com.EverythingBlocks.util.EBUtils;
import com.EverythingBlocks.util.JointList;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/** Recipe for Everything Stairs (6x block -> 8 stairs) */
public class EverythingStairCraftingRecipes implements IRecipe {

	/** Does the recipe match as expected? */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if(inv.getSizeInventory() < 9) return false; // only 3x3 works
		return isValidRecipeGrid(inv);
	}
	
	/** Check if the grid is valid (6 of same eligible item) */
	private boolean isValidRecipeGrid(InventoryCrafting inv) {
		JointList<ItemStack> s = new JointList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) s.add(inv.getStackInSlot(i));
		}
		
		// size is 6
		if(s.size() != 6) return false;
		
		// make sure it matches a stair recipe style
		if(Block.getBlockFromItem(s.get(0).getItem()) == EBBlocks.blockEverything) {
			if(EBUtils.areItemStacksEqualandValid(inv.getStackInSlot(0), inv.getStackInSlot(3), inv.getStackInSlot(4), inv.getStackInSlot(6), 
					inv.getStackInSlot(7), inv.getStackInSlot(8))) {
				return true;
			}
			if(EBUtils.areItemStacksEqualandValid(inv.getStackInSlot(2), inv.getStackInSlot(4), inv.getStackInSlot(5), inv.getStackInSlot(6), 
					inv.getStackInSlot(7), inv.getStackInSlot(8))) {
				return true;
			}
		}
		return false;
	}

	/** Result is a new block containing the ItemStack type */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack base = inv.getStackInSlot(6);
		if(base == null) return null;
		ItemStack stairs = new ItemStack(EBBlocks.stairEverything, 8);
		stairs.setTagCompound(base.getTagCompound());
		return stairs;
	}

	/** Size of grid */
	@Override
	public int getRecipeSize() {
		return 9;
	}
	
	/** Basic recipe output */
	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(EBBlocks.stairEverything, 8);
	}

	/** No remaining items */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}
	
}
