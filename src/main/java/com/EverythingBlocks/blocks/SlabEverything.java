package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
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

public class SlabEverything extends BlockSlab implements ITileEntityProvider, IBlockEverything {
	
	private static final PropertyBool VARIANT = PropertyBool.create("variant");

	public SlabEverything() {
		super(Material.ROCK);
		this.setHardness(3.0f);
		this.setResistance(15.0f);
		this.useNeighborBrightness = true;
		IBlockState blockState = this.blockState.getBaseState();
		blockState = blockState.withProperty(VARIANT, false);
        blockState = blockState.withProperty(HALF, EnumBlockHalf.BOTTOM);
        setDefaultState(blockState);
	}
	
	/** The block suffix */
	@Override
	public String getBlockSuffix() {
		return "Slab";
	}
	
	/** The count modifier */
	@Override
	public double getCountModifier() {
		return 0.5;
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		BlockEvents.doEverythingDrop(world, pos, this);
	}
	
	@Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, new IProperty[] {HALF, VARIANT});
    }
    
    /**
     * Convert the given metadata into a BlockState for this Block
     */
    public IBlockState getStateFromMeta(int meta) {
        IBlockState iblockstate = this.getDefaultState();
        iblockstate = iblockstate.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        return iblockstate;
    }

    /**
     * Convert the BlockState into the correct metadata value
     */
    public int getMetaFromState(IBlockState state) {
        int i = 0;

        if (state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP)
        {
            i |= 8;
        }

        return i;
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
		if(EBConfig.showSlabs) {
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

	/** A tile entity is necessary */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockEverything();
	}

	/** Single Slab */
	@Override
	public boolean isDouble() {
		return false;
	}

	 /**
     * Gets the variant property.
     * @return the variant property null.
     */
	@Override
	public IProperty getVariantProperty() {
		return VARIANT;
	}

	/** Name */
	@Override
	public String getUnlocalizedName(int meta) {
		return this.getUnlocalizedName();
	}

	@Override
	public Comparable<?> getTypeForItem(ItemStack stack) {
		return false;
	}

}
