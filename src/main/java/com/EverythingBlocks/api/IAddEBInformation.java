package com.EverythingBlocks.api;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/** Items that implement this interface can add custom information to the corresponding Everything Block, in addition to vanilla information */
public interface IAddEBInformation {
	
	/** Works the same as vanilla addInformation, will display on the Everything Block tooltip.
	 * Note that the ItemStack is not the stack that contains the block, but the stack stored in the block. */
	@SideOnly(Side.CLIENT)
	public void addEBInformation(ItemStack stack, EntityPlayer player, List list);

}
