package com.EverythingBlocks.api;

import net.minecraft.item.ItemStack;

/** Items that implement this interface will have a corresponding Everything Block only if 
 * hasEverythingBlock returns true */
public interface IHasEverythingBlockCond {
	
	/** Returns true if the specified stack of this item has an Everything Block */
	public boolean hasEverythingBlock(ItemStack stack);

}
