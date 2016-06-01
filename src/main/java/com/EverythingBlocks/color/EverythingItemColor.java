package com.EverythingBlocks.color;


import net.minecraft.block.Block;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IBlockEverything;

/** Item coloring for Everything Blocks */
@SideOnly(Side.CLIENT)
public class EverythingItemColor implements IItemColor {

	@Override
    public int getColorFromItemstack(ItemStack stack, int par2) {
		ItemStack contains;
		IBlockEverything block = (IBlockEverything)Block.getBlockFromItem(stack.getItem());				
		if(stack.hasTagCompound()) {
			contains = block.getItemStackFromBlock(stack);
		} else {
			contains = null;
		}
        return EverythingColor.getAverageColor(contains);
    }

}
