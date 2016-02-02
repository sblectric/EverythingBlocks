package com.EverythingBlocks.api;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Items that implement this interface will override the color calculations made by EverythingBlocks.
 * Note that this will override EVERY default EverythingBlocks color calc for this item. */
public interface IOverrideEBColor {
	
	/** Get the color that the Everything Block of this ItemStack will be */
	@SideOnly(Side.CLIENT)
	public int getEverythingBlockColor(ItemStack stack);
	
}
