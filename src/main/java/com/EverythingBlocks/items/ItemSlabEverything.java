package com.EverythingBlocks.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import org.lwjgl.input.Keyboard;

import com.EverythingBlocks.api.IAddEBInformation;
import com.EverythingBlocks.api.IReplaceEBInformation;
import com.EverythingBlocks.blocks.BlockEverything;
import com.EverythingBlocks.blocks.EBBlocks;
import com.EverythingBlocks.blocks.SlabEverything;
import com.EverythingBlocks.color.ColorHelper;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;
import com.EverythingBlocks.util.EBUtils;
import com.EverythingBlocks.util.ForgeUtils;

public class ItemSlabEverything extends ItemSlab {

	private SlabEverything slab;
	private static BlockEverything block = EBBlocks.blockEverything;
	private static IBlockState blockState = block.getDefaultState();

	public ItemSlabEverything(Block parent, SlabEverything slab) {
		super(parent, slab, slab);
		this.slab = (SlabEverything)parent;
	}

	/** Custom slab behavior */
	@Override
	 public EnumActionResult onItemUse(ItemStack stack, EntityPlayer p, World world, BlockPos pos, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
		IBlockState state = world.getBlockState(pos);
		Block exist = world.getBlockState(pos).getBlock();
		if(exist == slab && p.canPlayerEdit(pos.offset(side), side, stack) && ItemStack.areItemStacksEqual(
				EBUtils.getBlockContains(world, pos), slab.getItemStackFromBlock(stack))) {
			ItemStack contains = ((TileEntityBlockEverything)world.getTileEntity(pos)).contains; // preserve it
			if (world.checkNoEntityCollision(block.getBoundingBox(state, world, pos)) && world.setBlockState(pos, blockState, 3)) {
				((TileEntityBlockEverything)world.getTileEntity(pos)).contains = contains;
				//world.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), 
				//		block.getStepSound().getPlaceSound(), (block.getStepSound().getVolume() + 1.0F) / 2.0F, block.getStepSound().pitch * 0.8F);
				--stack.stackSize;
				return EnumActionResult.SUCCESS;
			} else {
				return EnumActionResult.FAIL;
			}
		} else {
			if(func_150946_a(stack, p, world, pos, side)) {
				return EnumActionResult.SUCCESS;
			} else {
				return super.onItemUse(stack, p, world, pos, hand, side, hitX, hitY, hitZ);
			}
		}
	}

	private boolean func_150946_a(ItemStack stack, EntityPlayer p, World world, BlockPos pos1, EnumFacing side) {
		BlockPos pos = pos1.add(side.getDirectionVec());

		IBlockState state = world.getBlockState(pos);
		Block b = state.getBlock();

		if (b == slab && p.canPlayerEdit(pos.offset(side), side, stack) && ItemStack.areItemStacksEqual(
				EBUtils.getBlockContains(world, pos), slab.getItemStackFromBlock(stack))) {
			ItemStack contains = ((TileEntityBlockEverything)world.getTileEntity(pos)).contains; // preserve it
			if (world.checkNoEntityCollision(block.getBoundingBox(state, world, pos)) && world.setBlockState(pos, blockState, 3)) {
				((TileEntityBlockEverything)world.getTileEntity(pos)).contains = contains;
				//world.playSound((double)((float)pos.getX() + 0.5F), (double)((float)pos.getY() + 0.5F), (double)((float)pos.getZ() + 0.5F), block.getStepSound().getPlaceSound(), 
				//		(block.getStepSound().getVolume() + 1.0F) / 2.0F, block.getStepSound().pitch * 0.8F);
				--stack.stackSize;
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * allows items to add custom lines of information to the mouseover description
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List list, boolean par4) {

		// it's an Everything Slab
		list.add(I18n.translateToLocal(stack.getUnlocalizedName() + ".name"));

		// add item details
		if(stack.hasTagCompound()) {
			ItemStack contains = slab.getItemStackFromBlock(stack);
			Item item = contains.getItem();

			// add the name and amount (rarity sensitive)
			list.add(ColorHelper.darkenFormatting(contains.getRarity().rarityColor) + 
					contains.getDisplayName() + " x " + EBUtils.dFormat.format(contains.stackSize * slab.getCountModifier()));

			// add default item lore to the container lore
			if(!(item instanceof IReplaceEBInformation)) {
				item.addInformation(contains, player, list, par4);
			}

			// add EverythingBlocks lore to the container lore
			if(item instanceof IAddEBInformation) {
				((IAddEBInformation)item).addEBInformation(contains, player, list);
			}
			
			// what mod is it from?
			if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
				list.add(TextFormatting.DARK_GRAY + "" + TextFormatting.ITALIC + ForgeUtils.getModNameFromItem(item));
			}

		} else {
			list.add(TextFormatting.ITALIC + "[Any Item]"); // generic block
		}
	}

	/** Add the NBT data to the tile entity */
	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState metadata) {
		boolean flag = super.placeBlockAt(stack, player, world, pos, side, hitX, hitY, hitZ, metadata);
		TileEntityBlockEverything tile = (TileEntityBlockEverything)world.getTileEntity(pos);
		
		// set the contains field
		if(stack.hasTagCompound()) {
			tile.contains = slab.getItemStackFromBlock(stack);
		} else {
			tile.contains = null;
		}
		
		tile.markDirty();
		return flag;
	}

	/** Display a dynamic name based on the item the block is made from */
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound()) {
			ItemStack contains = slab.getItemStackFromBlock(stack);
			try {
				return contains.getDisplayName() + " " + slab.getBlockSuffix();
			} catch(Exception e) {
				return I18n.translateToLocal(contains.getUnlocalizedName() + ".name") + " " + slab.getBlockSuffix();
			}
		} else {
			return super.getItemStackDisplayName(stack);
		}

	}
}
