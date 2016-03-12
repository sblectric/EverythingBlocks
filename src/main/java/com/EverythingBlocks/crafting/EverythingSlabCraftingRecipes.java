package com.EverythingBlocks.crafting;

import net.minecraft.block.Block;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.items.ItemBlockEverything;
import com.EverythingBlocks.util.EBUtils;
import com.EverythingBlocks.util.JointList;

/** Recipe for Everything Slabs (3x block -> 6 slabs) */
public class EverythingSlabCraftingRecipes implements IRecipe {
	
	/** Does the recipe match as expected? */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		if(inv.getSizeInventory() < 9) return false; // only 3x3 works
		return isValidRecipeGrid(inv);
	}
	
	/** Check if the grid is valid (9 of same eligible item) */
	private boolean isValidRecipeGrid(InventoryCrafting inv) {
		JointList<ItemStack> s = new JointList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) s.add(inv.getStackInSlot(i));
		}
		
		// make sure there are 3 everything blocks in a row
		if(s.size() != 3) return false;
		
		// make sure it matches a slab recipe style
		if(Block.getBlockFromItem(s.get(0).getItem()) == EBBlocks.blockEverything) {
			if(EBUtils.areItemStacksEqualForCrafting(inv.getStackInSlot(0), inv.getStackInSlot(1), inv.getStackInSlot(2))) {
				return true;
			}
			if(EBUtils.areItemStacksEqualForCrafting(inv.getStackInSlot(3), inv.getStackInSlot(4), inv.getStackInSlot(5))) {
				return true;
			}
			if(EBUtils.areItemStacksEqualForCrafting(inv.getStackInSlot(6), inv.getStackInSlot(7), inv.getStackInSlot(8))) {
				return true;
			}
		}
		return false;
	}

	/** Result is a new block containing the ItemStack type */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack base = inv.getStackInSlot(0);
		if(base == null) base = inv.getStackInSlot(3);
		if(base == null) base = inv.getStackInSlot(6);
		if(base == null) return null;
		ItemStack slabs = new ItemStack(EBBlocks.slabEverything, 6);
		slabs.setTagCompound(base.getTagCompound());
		return slabs;
	}

	/** Size of recipe */
	@Override
	public int getRecipeSize() {
		return 3;
	}
	
	/** Basic recipe output */
	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(EBBlocks.slabEverything, 6);
	}

	/** No remaining items */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}
	
}
