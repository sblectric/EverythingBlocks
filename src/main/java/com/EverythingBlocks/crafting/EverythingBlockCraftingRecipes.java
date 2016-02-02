package com.EverythingBlocks.crafting;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.util.JointList;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

/** Recipe for all Everything Blocks (9 x item -> block) */
public class EverythingBlockCraftingRecipes implements IRecipe {

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
		
		// make sure they 9 of the same item
		if(s.size() != 9) return false;
		ItemStack comp = null;
		for(ItemStack stack : s) {
			if(comp == null) {
				comp = stack;
			} else {
				if(!ItemStack.areItemStacksEqual(stack, comp)) return false;
			}
		}
		
		// make sure that item is a valid block constructor
		return CraftableToBlock.isItemStackValidAndCraftable(comp);
	}

	/** Result is a new block containing the ItemStack type */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		ItemStack base = inv.getStackInSlot(0).copy();
		base.stackSize = 9; // set the stack size
		return EBBlocks.blockEverything.getBlockContaining(base);
	}

	/** Size of grid */
	@Override
	public int getRecipeSize() {
		return 9;
	}
	
	/** Basic recipe output */
	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(EBBlocks.blockEverything);
	}

	/** No remaining items */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}
	

}
