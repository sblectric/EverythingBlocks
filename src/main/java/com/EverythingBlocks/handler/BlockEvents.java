package com.EverythingBlocks.handler;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;

public class BlockEvents {
	
	/** If the block can't be harvested, remove its drops */
	@SubscribeEvent
	public void onBlockBroken(BreakEvent e) {
		Block b = e.getState().getBlock();
		if(!e.getWorld().isRemote && b instanceof IBlockEverything) {
			if(!b.canHarvestBlock(e.getWorld(), e.getPos(), e.getPlayer())) {
				TileEntityBlockEverything tile = (TileEntityBlockEverything)e.getWorld().getTileEntity(e.getPos());
				tile.contains = null;
			}
		}
	}
	
	/** Drop the Everything Block drops */
	public static void doEverythingDrop(World world, BlockPos pos, IBlockEverything block) {
		// get the containing block ItemStack
		TileEntityBlockEverything tile = (TileEntityBlockEverything)world.getTileEntity(pos);
		if(tile.contains != null) {
			// drop the item and remove the tile entity
			Block.spawnAsEntity(world, pos, block.getBlockContaining(tile.contains));
		}
		world.removeTileEntity(pos);
	}

}
