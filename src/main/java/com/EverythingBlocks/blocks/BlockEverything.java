package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
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
import com.EverythingBlocks.handler.BlockEvents;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.EBUtils;
import com.EverythingBlocks.util.JointList;

/** The class for the block that can take many forms */
public class BlockEverything extends BlockContainer implements IBlockEverything {
	
	/** field to hold eligible item stacks for display */
	public static List<ItemStack> eligibleItemStacks = new JointList<ItemStack>();
	
	public BlockEverything() {
		super(Material.ROCK);
		this.setHardness(3.0f);
		this.setResistance(15.0f);
	}
	
	/** The block suffix */
	@Override
	public String getBlockSuffix() {
		return "Block";
	}
	
	/** The count modifier */
	@Override
	public double getCountModifier() {
		return 1.0;
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
		EBUtils.getSubBlocks(item, tab, list, this);
	}
	
	/** Render type: Standard block */
	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
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

	/** A tile entity is necessary */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockEverything();
	}

}
