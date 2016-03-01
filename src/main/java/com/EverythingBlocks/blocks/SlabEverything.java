package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStoneSlab;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
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

public class SlabEverything extends BlockSlab implements ITileEntityProvider, IBlockEverything {
	
	private static final PropertyBool VARIANT = PropertyBool.create("variant");

	public SlabEverything() {
		super(Material.rock);
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
	
    protected BlockState createBlockState() {
        return new BlockState(this, new IProperty[] {HALF, VARIANT});
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
		if(EBConfig.showSlabs) {
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

	/**
     * Gets the value of the variant property based on the item.
     * @param itemStack item stack.
     * @return the variant value null.
     */
	@Override
	public Object getVariant(ItemStack stack) {
		return false;
	}

	/** Name */
	@Override
	public String getUnlocalizedName(int meta) {
		return this.getUnlocalizedName();
	}

}
