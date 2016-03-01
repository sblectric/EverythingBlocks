package com.EverythingBlocks.crafting;

import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.items.ItemBlockEverything;
import com.EverythingBlocks.util.JointList;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.world.World;

public class EverythingBlockDecraftingRecipes implements IRecipe {

	/** Does the recipe match as expected? */
	@Override
	public boolean matches(InventoryCrafting inv, World world) {
		return isValidRecipeGrid(inv);
	}
	
	/** Check if the grid is valid (1 Everything Block) */
	private boolean isValidRecipeGrid(InventoryCrafting inv) {
		JointList<ItemStack> s = new JointList();
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) s.add(inv.getStackInSlot(i));
		}
		
		// one Everything Block
		if(s.size() == 1 && Block.getBlockFromItem(s.get(0).getItem()) == EBBlocks.blockEverything && s.get(0).hasTagCompound()) {
			return true;
		} else {		
			return false;
		}
	}

	/** Get the type of item to output from the block */
	@Override
	public ItemStack getCraftingResult(InventoryCrafting inv) {
		for(int i = 0; i < inv.getSizeInventory(); i++) {
			if(inv.getStackInSlot(i) != null) {
				ItemStack block = inv.getStackInSlot(i);
				return EBBlocks.blockEverything.getItemStackFromBlock(block);
			}
		}
		return getRecipeOutput();
	}

	/** Single block -> result */
	@Override
	public int getRecipeSize() {
		return 1;
	}

	/** No default recipe output */
	@Override
	public ItemStack getRecipeOutput() {
		return new ItemStack(Blocks.air);
	}

	/** No remaining items */
	@Override
	public ItemStack[] getRemainingItems(InventoryCrafting inv) {
		return new ItemStack[inv.getSizeInventory()];
	}

}
