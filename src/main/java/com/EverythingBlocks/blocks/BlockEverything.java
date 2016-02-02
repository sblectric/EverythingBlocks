package com.EverythingBlocks.blocks;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.render.EverythingColor;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.JointList;

/** The class for the block that can take many forms */
public class BlockEverything extends BlockContainer implements IBlockEverything {
	
	@SideOnly(Side.CLIENT)
	public static List<ItemStack> eligibleItemStacks = new JointList<ItemStack>();

	public BlockEverything() {
		super(Material.rock);
		this.setHardness(3.0f);
		this.setResistance(15.0f);
	}
	
	/** Helper method to put an item into an Everything Block */
	@Override
	public ItemStack getBlockContaining(ItemStack containing) {
		ItemStack add = new ItemStack(this);
		add.setTagCompound(new NBTTagCompound());
		NBTTagCompound tags = new NBTTagCompound();
		containing.writeToNBT(tags);
		add.getTagCompound().setTag("contains", tags);
		return add;
	}
	
	/** Reverse of getBlockContaining */
	@Override
	public ItemStack getItemStackFromBlock(ItemStack block) {
		if(block.hasTagCompound()) {
			return ItemStack.loadItemStackFromNBT(block.getTagCompound().getCompoundTag("contains"));
		} else {
			return null;
		}
	}
	
	/** get the sub blocks (there will be MANY) */
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for(ItemStack s : eligibleItemStacks) {
			list.add(getBlockContaining(s));
		}
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
	
	/** Block coloring method */
	@Override
    @SideOnly(Side.CLIENT)
    public int colorMultiplier(IBlockAccess world, BlockPos pos, int renderPass) {
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
	
	/** Render type: Standard block */
	@Override
	public int getRenderType() {
		return 3;
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

	/** A tile entity is necessary */
	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBlockEverything();
	}

}
