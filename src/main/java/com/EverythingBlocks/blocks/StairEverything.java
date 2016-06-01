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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.handler.BlockEvents;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.EBUtils;

public class StairEverything extends BlockStairs implements ITileEntityProvider, IBlockEverything {

	/** An Everything Stair is based on the Everything Block */
	public StairEverything(BlockEverything block) {
		super(block.getStateFromMeta(0));
		this.setHardness(3.0f);
		this.setResistance(15.0f);
		this.useNeighborBrightness = true;
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
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		BlockEvents.doEverythingDrop(world, pos, this);
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
	
    /** No standard drops from this block */
	@Override
	public ArrayList<ItemStack> getDrops(IBlockAccess world, BlockPos pos, IBlockState metadata, int fortune) {
		return new ArrayList<ItemStack>(); // no drops
	}
	
	/** Get the pick block */
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
		return this.getBlockContaining(EBUtils.getBlockContains(world, pos));
	}
	
	/** No silk touch code here */
    @Override
	public boolean canSilkHarvest() {
    	return false;
    }
    
	/** Render type: Standard block */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}
	
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockEverything();
	}

}
