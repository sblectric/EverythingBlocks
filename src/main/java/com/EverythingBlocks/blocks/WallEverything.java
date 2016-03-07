package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.config.EBConfig;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.EBUtils;

public class WallEverything extends BlockContainer implements IBlockEverything {

	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");

	public WallEverything(Block modelBlock) {
		super(modelBlock.getMaterial());
		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).
				withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setStepSound(modelBlock.stepSound);
		this.setHardness(3.0f);
		this.setResistance(15.0f);
		this.useNeighborBrightness = true;
	}

	@Override
	public String getBlockSuffix() {
		return "Wall";
	}

	@Override
	public double getCountModifier() {
		return 1;
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
		if(EBConfig.showWalls) {
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

	public boolean isFullCube()
	{
		return false;
	}

	public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
	{
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	public boolean isOpaqueCube()
	{
		return false;
	}

	public void setBlockBoundsBasedOnState(IBlockAccess worldIn, BlockPos pos)
	{
		boolean flag = this.canConnectTo(worldIn, pos.north());
		boolean flag1 = this.canConnectTo(worldIn, pos.south());
		boolean flag2 = this.canConnectTo(worldIn, pos.west());
		boolean flag3 = this.canConnectTo(worldIn, pos.east());
		float f = 0.25F;
		float f1 = 0.75F;
		float f2 = 0.25F;
		float f3 = 0.75F;
		float f4 = 1.0F;

		if (flag)
		{
			f2 = 0.0F;
		}

		if (flag1)
		{
			f3 = 1.0F;
		}

		if (flag2)
		{
			f = 0.0F;
		}

		if (flag3)
		{
			f1 = 1.0F;
		}

		if (flag && flag1 && !flag2 && !flag3)
		{
			f4 = 0.8125F;
			f = 0.3125F;
			f1 = 0.6875F;
		}
		else if (!flag && !flag1 && flag2 && flag3)
		{
			f4 = 0.8125F;
			f2 = 0.3125F;
			f3 = 0.6875F;
		}

		this.setBlockBounds(f, 0.0F, f2, f1, f4, f3);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBox(World worldIn, BlockPos pos, IBlockState state) {
		this.setBlockBoundsBasedOnState(worldIn, pos);
		this.maxY = 1.5D;
		return super.getCollisionBoundingBox(worldIn, pos, state);
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	 @Override
	 public IBlockState getStateFromMeta(int meta) {
		 return this.getDefaultState();
	 }

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	 @Override
	 public int getMetaFromState(IBlockState state) {
		return 0;
	 }

	 public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos)
	 {
		 Block block = worldIn.getBlockState(pos).getBlock();
		 return block == Blocks.barrier ? false : (block != this && !(block instanceof BlockFenceGate) ? 
				 (block.getMaterial().isOpaque() && block.isFullCube() ? block.getMaterial() != Material.gourd : false) : true);
	 }

	 /**
	  * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	  * metadata, such as fence connections.
	  */
	 @Override
	 public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		 return state.withProperty(UP, Boolean.valueOf(!worldIn.isAirBlock(pos.up()))).
				 withProperty(NORTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.north()))).
				 withProperty(EAST, Boolean.valueOf(this.canConnectTo(worldIn, pos.east()))).
				 withProperty(SOUTH, Boolean.valueOf(this.canConnectTo(worldIn, pos.south()))).
				 withProperty(WEST, Boolean.valueOf(this.canConnectTo(worldIn, pos.west())));
	 }

	 @Override
	 protected BlockState createBlockState() {
		 return new BlockState(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH});
	 }

	 @Override
	 public TileEntity createNewTileEntity(World worldIn, int meta) {
		 return new TileEntityBlockEverything();
	 }

}
