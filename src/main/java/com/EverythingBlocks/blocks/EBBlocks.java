package com.EverythingBlocks.blocks;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.api.EverythingBlocksAPI;
import com.EverythingBlocks.creativetabs.EBCreativeTabs;
import com.EverythingBlocks.items.ItemBlockEverything;
import com.EverythingBlocks.ref.RefStrings;

public class EBBlocks {
	
	public static void mainRegistry() {
		addBlocks();
		registerBlocks();
	}
	
	public static BlockEverything blockEverything;
	
	private static void addBlocks() {
		blockEverything = (BlockEverything) new BlockEverything().
				setUnlocalizedName("blockEverything").setCreativeTab(EBCreativeTabs.main);//setBlockTextureName(RefStrings.MODID + ":everything_block");
		EverythingBlocksAPI.blockEverything = blockEverything; // direct the API to this block
	}
	
	private static void registerBlocks() {
		GameRegistry.registerBlock(blockEverything, ItemBlockEverything.class, blockEverything.getUnlocalizedName().substring(5));
	}
	
	@SideOnly(Side.CLIENT)
	public static void registerRendering() {
		RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
		renderItem.getItemModelMesher().register(Item.getItemFromBlock(blockEverything), 0, 
				new ModelResourceLocation(RefStrings.MODID + ":" + blockEverything.getUnlocalizedName().substring(5), "inventory"));
	}

}
