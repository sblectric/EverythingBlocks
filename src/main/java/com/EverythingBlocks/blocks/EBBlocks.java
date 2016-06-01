package com.EverythingBlocks.blocks;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.EverythingBlocksAPI;
import com.EverythingBlocks.color.EverythingBlockColor;
import com.EverythingBlocks.color.EverythingItemColor;
import com.EverythingBlocks.creativetabs.EBCreativeTabs;
import com.EverythingBlocks.items.ItemBlockEverything;
import com.EverythingBlocks.items.ItemSlabEverything;
import com.EverythingBlocks.ref.RefStrings;
import com.EverythingBlocks.util.JointList;

public class EBBlocks {
	
	private static JointList<Block> blocks;
	
	public static void mainRegistry() {
		blocks = new JointList();
		addBlocks();
		registerBlocks();
	}
	
	// the blocks
	public static BlockEverything blockEverything;
	public static SlabEverything slabEverything;
	public static StairEverything stairEverything;
	public static WallEverything wallEverything;
	
	private static void addBlocks() {
		blocks.join(
			blockEverything = (BlockEverything) new BlockEverything().
					setUnlocalizedName("blockEverything").setCreativeTab(EBCreativeTabs.main),
			slabEverything = (SlabEverything) new SlabEverything().
					setUnlocalizedName("slabEverything").setCreativeTab(EBCreativeTabs.main),
			stairEverything = (StairEverything) new StairEverything(blockEverything).
					setUnlocalizedName("stairEverything").setCreativeTab(EBCreativeTabs.main),
			wallEverything = (WallEverything) new WallEverything(blockEverything).
					setUnlocalizedName("wallEverything").setCreativeTab(EBCreativeTabs.main)
		);
	}
	
	private static void registerBlocks() {
		// register the blocks in game
		GameRegistry.registerBlock(blockEverything, ItemBlockEverything.class, blockEverything.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(slabEverything, ItemSlabEverything.class, slabEverything.getUnlocalizedName().substring(5), slabEverything);
		GameRegistry.registerBlock(stairEverything, ItemBlockEverything.class, stairEverything.getUnlocalizedName().substring(5));
		GameRegistry.registerBlock(wallEverything, ItemBlockEverything.class, wallEverything.getUnlocalizedName().substring(5));
		
		// direct the API to the blocks
		EverythingBlocksAPI.blockEverything = blockEverything;
		EverythingBlocksAPI.slabEverything = slabEverything;
		EverythingBlocksAPI.stairEverything = stairEverything;
		EverythingBlocksAPI.wallEverything = wallEverything;
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		BlockColors colors = Minecraft.getMinecraft().getBlockColors();
		ItemColors icolors = Minecraft.getMinecraft().getItemColors();
		for(Block block : blocks) {
			renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), 0, 
					new ModelResourceLocation(RefStrings.MODID + ":" + block.getUnlocalizedName().substring(5), "inventory"));
			colors.registerBlockColorHandler(new EverythingBlockColor(), block);
			icolors.registerItemColorHandler(new EverythingItemColor(), block);
		}
	}

}
