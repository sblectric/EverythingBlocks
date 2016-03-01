package com.EverythingBlocks.api;

import net.minecraft.item.ItemStack;

/** Helper interface to reference Everything Block methods. This could also be 
 *  implemented to make your own Everything Block variant. */
public interface IBlockEverything {
	
	/** Helper method to put an item into an Everything Block */
	public ItemStack getBlockContaining(ItemStack containing);
	
	/** Reverse of getBlockContaining */
	public ItemStack getItemStackFromBlock(ItemStack block);
	
	/** Returns the suffix of this block, e.g. "Block" for the basic Everything Block,
	 *  and "Stairs" for Everything Stairs. */
	public String getBlockSuffix();
	
	/** Get the ratio of held items as compared to a full Everything Block (returns 1),
	 *  e.g. slabs return 0.5, stairs return 0.75. */
	public double getCountModifier();

}
