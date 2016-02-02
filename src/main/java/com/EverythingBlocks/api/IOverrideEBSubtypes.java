package com.EverythingBlocks.api;

import java.util.List;

import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Items that implement this interface will override the default subtypes to show in the EverythingBlocks
 *  creative tab (specified in getSubItems by default). */
public interface IOverrideEBSubtypes {
	
	/** Method to get the subtypes of the specified item to show in the EverythingBlocks creative tab.
	 * Works similarly to the vanilla getSubItems method. */
	@SideOnly(Side.CLIENT)
	public void getEBSubtypes(Item item, List list);

}
