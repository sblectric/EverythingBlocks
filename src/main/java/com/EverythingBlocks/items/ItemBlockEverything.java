package com.EverythingBlocks.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.IAddEBInformation;
import com.EverythingBlocks.api.IReplaceEBInformation;
import com.EverythingBlocks.blocks.BlockEverything;
import com.EverythingBlocks.render.EverythingColor;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.ColorHelper;

public class ItemBlockEverything extends ItemBlock {
	
	private BlockEverything block;

	public ItemBlockEverything(Block base) {
		super(base);
		this.block = (BlockEverything)base;
	}
	
	/**
     * allows items to add custom lines of information to the mouseover description
     */
    @Override
	@SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {
		
		// it's an Everything Block
		list.add(StatCollector.translateToLocal(stack.getUnlocalizedName() + ".name"));
		
		// add item details
		if(stack.hasTagCompound()) {
			ItemStack contains = block.getItemStackFromBlock(stack);
			Item item = contains.getItem();
			
			// add the name and amount (rarity sensitive)
			list.add(ColorHelper.darkenFormatting(contains.getRarity().rarityColor) + contains.getDisplayName() + " x " + contains.stackSize);
			
			// add default item lore to the container lore
			if(!(item instanceof IReplaceEBInformation)) {
				item.addInformation(contains, player, list, par4);
			}
			
			// add EverythingBlocks lore to the container lore
			if(item instanceof IAddEBInformation) {
				((IAddEBInformation)item).addEBInformation(contains, player, list);
			}
			
		} else {
			list.add(EnumChatFormatting.ITALIC + "[Any Item]"); // generic block
		}
    }
	
	/** Add the NBT data to the tile entity */
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState metadata) {
		boolean flag = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, metadata);
		TileEntityBlockEverything tile = (TileEntityBlockEverything)world.getTileEntity(pos);
		
		// set the contains field
		if(stack.hasTagCompound()) {
			tile.contains = block.getItemStackFromBlock(stack);
		} else {
			tile.contains = null;
		}
		
		tile.markDirty();
		return flag;
	}
	
	/** Coloring! */
	@Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int par2) {
		ItemStack contains;
		if(stack.hasTagCompound()) {
			contains = block.getItemStackFromBlock(stack);
		} else {
			contains = null;
		}
        return EverythingColor.getAverageColor(contains);
    }
	
	/** Display a dynamic name based on the item the block is made from */
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			ItemStack contains = block.getItemStackFromBlock(stack);
			try {
				return contains.getDisplayName() + " Block";
			} catch(Exception e) {
				return StatCollector.translateToLocal(contains.getUnlocalizedName() + ".name") + " Block";
			}
		} else {
			return super.getItemStackDisplayName(stack);
		}
		
	}

}
