package com.EverythingBlocks.api;

import net.minecraft.item.ItemStack;

/** Helper interface to reference Everything Block methods. */
public interface IBlockEverything {
	
	/** Helper method to put an item into an Everything Block */
	public ItemStack getBlockContaining(ItemStack containing);
	
	/** Reverse of getBlockContaining */
	public ItemStack getItemStackFromBlock(ItemStack block);

}
