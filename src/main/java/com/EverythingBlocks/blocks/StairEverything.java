package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.EBUtils;

public class StairEverything extends BlockStairs implements ITileEntityProvider, IBlockEverything {

	/** An Everything Stair is based on the Everything Block */
	public StairEverything(BlockEverything block) {
		super(block.getStateFromMeta(0));
	}
	
	/** The block suffix */
	@Override
	public String getBlockSuffix() {
		return "Stairs";
	}
	
	/** The count modifier */
	@Override
	public double getCountModifier() {
		return 0.75;
	}
	
	/** Drop the block (make sure it's the exact type!) */
	@Override
	public boolean removedByPlayer(World world, BlockPos pos, EntityPlayer player, boolean willHarvest) { 
		if(willHarvest) {
			// read the tile entity position
			TileEntityBlockEverything tile = (TileEntityBlockEverything)world.getTileEntity(pos);
			ItemStack stack = getBlockContaining(tile.contains);
			
			// drop the item
			spawnAsEntity(world, pos, stack);
		}
		return super.removedByPlayer(world, pos, player, willHarvest);
	}
	
	/** Helper method to put an item into an Everything Block */
	@Override
	public ItemStack getBlockContaining(ItemStack containing) {
		return EBUtils.getBlockContaining(containing, this);
	}
	
	/** Reverse of getBlockContaining */
	@Override
	public ItemStack getItemStackFromBlock(ItemStack block) {
		return EBUtils.getItemStackFromBlock(block);
	}

	/** get the sub blocks (there will be MANY) */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		if(EBConfig.showStairs) {
			EBUtils.getSubBlocks(item, tab, list, this);
		} else {
			list.add(new ItemStack(item)); // basic things
		}
	}
	
	/** Block coloring method */
	@Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
		return EBUtils.colorMultiplier(world, pos);
    }
	
    /** No standard drops from this block */
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState metadata, int fortune) {
		return new ArrayList<ItemStack>(); // no drops
	}
	
	/** No silk touch code here */
    @Override
	public boolean canSilkHarvest() {
    	return false;
    }
    
    /**
     * Gets an item for the block being called on. Args: world, x, y, z
     */
    @SideOnly(Side.CLIENT)
    @Override
    public Item getItem(World world, BlockPos pos) {
    	return Item.getItemFromBlock(this);
    }
    
	/** Render type: Standard block */
	@Override
	public int getRenderType() {
		return 3;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockEverything();
	}

}
