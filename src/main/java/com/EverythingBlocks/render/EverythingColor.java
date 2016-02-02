package com.EverythingBlocks.render;

import java.awt.Color;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import com.EverythingBlocks.api.IOverrideEBColor;
import com.EverythingBlocks.main.Log;
import com.EverythingBlocks.util.ColorHelper;
import com.EverythingBlocks.util.IntList;

/** Coloring for Everything Blocks */
@SideOnly(Side.CLIENT)
public class EverythingColor {
	
	// default color
	private static final int DEFAULT_COLOR = Color.WHITE.getRGB();
	
	// caches
	private static HashMap<String, Integer> itemColorCache = new HashMap();
	private static HashMap<String, Integer> stackColorCache = new HashMap();
	
	/** Add a color to an appropriate cache and return that color */
	public static int addStackToColorCache(ItemStack s, int col) {
		if(s.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
			itemColorCache.put(s.getItem().toString(), col);
		} else {
			stackColorCache.put(s.toString(), col);
		}
		return col;
	}
	
	/** Find the average color of an item, but not too dark */
	public static int getAverageColor(ItemStack stack) {
		int result = getAverageColorUnbounded(stack);
		int r = ColorHelper.redFromColor(result);
		int g = ColorHelper.greenFromColor(result);
		int b = ColorHelper.blueFromColor(result);
		if(r < 16) r = 16;
		if(g < 16) g = 16;
		if(b < 16) b = 16;
		return new Color(r,g,b).getRGB();
	}

	/** Find the average color of an item */
	public static int getAverageColorUnbounded(ItemStack stack) {
		
		// null -> return white
		if(stack == null) return DEFAULT_COLOR;
		ItemStack nStack = stack.copy();
		nStack.stackSize = 1; // constant size (for counting / caching)
		Item item = nStack.getItem();
		
		// this stack overrides all color calculations
		if(item instanceof IOverrideEBColor) {
			return ((IOverrideEBColor)item).getEverythingBlockColor(nStack);
		}
		
		// non-overridden calculations cached for performance
		if(itemColorCache.containsKey(item.toString())) {
			return itemColorCache.get(item.toString());
		} else if(stackColorCache.containsKey(nStack.toString())) {
			return stackColorCache.get(nStack.toString());
		}
		
		// the color of potion stacks is their liquid color
		if(item instanceof ItemPotion) {
			return addStackToColorCache(nStack, item.getColorFromItemStack(stack, 0));
		}
		
		// the color of spawn egg blocks is the average of their layers
		if(item instanceof ItemMonsterPlacer) {
			return addStackToColorCache(nStack, ColorHelper.averageColors(item.getColorFromItemStack(stack, 0), item.getColorFromItemStack(stack, 1)));
		}
		
		// dyeable armor returns a slightly darkened dye color
		if(item instanceof ItemArmor && ((ItemArmor)item).getColor(stack) > -1) {
			return addStackToColorCache(nStack, ColorHelper.averageColors(((ItemArmor)item).getColor(stack), 0, 0.75));
		}
		
		// get the texture from the ItemStack (IN GAME, not in init methods or events!)
		TextureAtlasSprite sprite = Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(nStack).getParticleTexture();
		if(sprite == null) return addStackToColorCache(nStack, item.getColorFromItemStack(stack, 0)); // default to item stack color
		int w = sprite.getIconWidth();
        int h = sprite.getIconHeight();
		
		// now look at the pixels
        IntList colors = new IntList();
        int[] aint = sprite.getFrameTextureData(0)[0];
		for(int x = 0; x < w; x++) {
			for(int y = 0; y < h; y++) {
				int c = aint[x + y * h];
				if(c == 0) continue; // skip alpha pixels
				colors.add(c);
			}
		}
		
		// average them and return
		try {
			Integer col = ColorHelper.averageColors(colors);
			return addStackToColorCache(nStack, col); // add this result to the cache and return it
		} catch (Exception e) {
			Log.logger.error("Exception caught getting color from ItemStack " + nStack.toString());
			return addStackToColorCache(nStack, DEFAULT_COLOR);
		}
	}

}
