package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
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

public class WallEverything extends BlockContainer implements IBlockEverything {

	public static final PropertyBool UP = PropertyBool.create("up");
	public static final PropertyBool NORTH = PropertyBool.create("north");
	public static final PropertyBool EAST = PropertyBool.create("east");
	public static final PropertyBool SOUTH = PropertyBool.create("south");
	public static final PropertyBool WEST = PropertyBool.create("west");
    protected static final AxisAlignedBB[] field_185751_g = new AxisAlignedBB[] {new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.3125D, 0.0D, 0.0D, 0.6875D, 0.875D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.75D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.3125D, 1.0D, 0.875D, 0.6875D), new AxisAlignedBB(0.0D, 0.0D, 0.25D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.25D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 0.75D), new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D)};
    protected static final AxisAlignedBB[] field_185750_B = new AxisAlignedBB[] {field_185751_g[0].setMaxY(1.5D), field_185751_g[1].setMaxY(1.5D), field_185751_g[2].setMaxY(1.5D), field_185751_g[3].setMaxY(1.5D), field_185751_g[4].setMaxY(1.5D), field_185751_g[5].setMaxY(1.5D), field_185751_g[6].setMaxY(1.5D), field_185751_g[7].setMaxY(1.5D), field_185751_g[8].setMaxY(1.5D), field_185751_g[9].setMaxY(1.5D), field_185751_g[10].setMaxY(1.5D), field_185751_g[11].setMaxY(1.5D), field_185751_g[12].setMaxY(1.5D), field_185751_g[13].setMaxY(1.5D), field_185751_g[14].setMaxY(1.5D), field_185751_g[15].setMaxY(1.5D)};

	public WallEverything(Block modelBlock) {
		super(modelBlock.getMaterial(null));
		this.setDefaultState(this.blockState.getBaseState().withProperty(UP, Boolean.valueOf(false)).withProperty(NORTH, Boolean.valueOf(false)).
				withProperty(EAST, Boolean.valueOf(false)).withProperty(SOUTH, Boolean.valueOf(false)).withProperty(WEST, Boolean.valueOf(false)));
		this.setSoundType(modelBlock.getSoundType());
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
		if(EBConfig.showWalls) {
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
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isPassable(IBlockAccess worldIn, BlockPos pos) {
		return false;
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks for render
	 */
	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	@Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        state = this.getActualState(state, source, pos);
        return field_185751_g[func_185749_i(state)];
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBox(IBlockState blockState, World worldIn, BlockPos pos) {
        blockState = this.getActualState(blockState, worldIn, pos);
        return field_185750_B[func_185749_i(blockState)];
    }
    
    private static int func_185749_i(IBlockState p_185749_0_)
    {
        int i = 0;

        if (((Boolean)p_185749_0_.getValue(NORTH)).booleanValue())
        {
            i |= 1 << EnumFacing.NORTH.getHorizontalIndex();
        }

        if (((Boolean)p_185749_0_.getValue(EAST)).booleanValue())
        {
            i |= 1 << EnumFacing.EAST.getHorizontalIndex();
        }

        if (((Boolean)p_185749_0_.getValue(SOUTH)).booleanValue())
        {
            i |= 1 << EnumFacing.SOUTH.getHorizontalIndex();
        }

        if (((Boolean)p_185749_0_.getValue(WEST)).booleanValue())
        {
            i |= 1 << EnumFacing.WEST.getHorizontalIndex();
        }

        return i;
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

	 public boolean canConnectTo(IBlockAccess worldIn, BlockPos pos) {
		 IBlockState state = worldIn.getBlockState(pos);
		 Block block = state.getBlock();
		 return block == Blocks.BARRIER ? false : (block != this && !(block instanceof BlockFenceGate) ? 
				 (block.getMaterial(state).isOpaque() && block.isFullCube(state) ? block.getMaterial(state) != Material.GOURD : false) : true);
	 }

	 /**
	  * Get the actual Block state of this Block at the given position. This applies properties not visible in the
	  * metadata, such as fence connections.
	  */
	 @Override
	 public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		 boolean flag = this.canConnectTo(worldIn, pos.north());
	        boolean flag1 = this.canConnectTo(worldIn, pos.east());
	        boolean flag2 = this.canConnectTo(worldIn, pos.south());
	        boolean flag3 = this.canConnectTo(worldIn, pos.west());
	        boolean flag4 = flag && !flag1 && flag2 && !flag3 || !flag && flag1 && !flag2 && flag3;
	        return state.withProperty(UP, Boolean.valueOf(!flag4 || !worldIn.isAirBlock(pos.up()))).
	        		withProperty(NORTH, Boolean.valueOf(flag)).withProperty(EAST, Boolean.valueOf(flag1)).
	        		withProperty(SOUTH, Boolean.valueOf(flag2)).withProperty(WEST, Boolean.valueOf(flag3));	    
	 }

	 @Override
	 protected BlockStateContainer createBlockState() {
		 return new BlockStateContainer(this, new IProperty[] {UP, NORTH, EAST, WEST, SOUTH});
	 }

	 @Override
	 public TileEntity createNewTileEntity(World worldIn, int meta) {
		 return new TileEntityBlockEverything();
	 }

}
