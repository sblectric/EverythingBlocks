package com.EverythingBlocks.util;

import java.text.DecimalFormat;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.blocks.BlockEverything;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.render.EverythingColor;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;

public class EBUtils {
	
	public static final DecimalFormat dFormat = new DecimalFormat("#.####");
	
	/** Shortcut to get the item stack of an Everything Block in the world */
	public static ItemStack getBlockContains(World world, BlockPos pos) {
		TileEntity t = world.getTileEntity(pos);
		if(t != null && t instanceof TileEntityBlockEverything) {
			return ((TileEntityBlockEverything)t).contains;
		} else {
			return null;
		}
	}
 	
	/** Helper method to put an item into an Everything Block */
	public static ItemStack getBlockContaining(ItemStack containing, Block block) {
		ItemStack add = new ItemStack(block);
		add.setTagCompound(new NBTTagCompound());
		NBTTagCompound tags = new NBTTagCompound();
		containing.writeToNBT(tags);
		add.getTagCompound().setTag("contains", tags);
		return add;
	}
	
	/** Reverse of getBlockContaining */
	public static ItemStack getItemStackFromBlock(ItemStack block) {
		if(block.hasTagCompound()) {
			return ItemStack.loadItemStackFromNBT(block.getTagCompound().getCompoundTag("contains"));
		} else {
			return null;
		}
	}
	
	/** get the sub blocks (there will be MANY) */
	@SideOnly(Side.CLIENT)
	public static void getSubBlocks(Item item, CreativeTabs tab, List list, Block block) {
		if(EBConfig.enableCreativeTabVariants) {
			for(ItemStack s : BlockEverything.eligibleItemStacks) {
				list.add(getBlockContaining(s, block));
			}
		} else {
			list.add(new ItemStack(item));
		}
	}
	
	/** Block coloring method */
    @SideOnly(Side.CLIENT)
    public static int colorMultiplier(IBlockAccess world, BlockPos pos) {
		// read the tile entity position
		TileEntityBlockEverything tile = (TileEntityBlockEverything)world.getTileEntity(pos);
		ItemStack contains;
		if(tile != null) {
			contains = tile.contains;
		} else {
			contains = null;
		}
		return EverythingColor.getAverageColor(contains);
    }
    
    /** An unlimited type of areItemStacksEqual for crafting recipes (non-amount sensitive, cannot be null) */
    public static boolean areItemStacksEqualForCrafting(ItemStack... stacks) {
    	ItemStack comp = stacks[0];
    	if(comp == null) return false;
    	ItemStack comp1 = comp.copy(); comp1.stackSize = 1;
    	for(int n = 1; n < stacks.length; n++) {
    		if(stacks[n] == null) return false;
    		ItemStack comp2 = stacks[n].copy(); comp2.stackSize = 1;
    		if(!ItemStack.areItemStacksEqual(comp1, comp2)) return false;
    	}
    	return true;
    }

}
