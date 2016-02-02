package com.EverythingBlocks.util;

import java.awt.Color;
import java.util.List;

import net.minecraft.util.EnumChatFormatting;

public class ColorHelper {

	public static int blueFromColor(int col) {
		return col & 0xFF;
	}

	public static int greenFromColor(int col) {
		return (col >> 8) & 0xFF;
	}

	public static int redFromColor(int col) {
		return (col >> 16) & 0xFF;
	}

	public static int alphaFromColor(int col) {
		return (col >> 24) & 0xFF;
	}
	
	public static int averageColors(List<Integer> cols) {
		IntList reds = new IntList();
		IntList greens = new IntList();
		IntList blues = new IntList();
		
		for(int col : cols) {
			reds.add(redFromColor(col));
			greens.add(greenFromColor(col));
			blues.add(blueFromColor(col));
		}
		
		return new Color(reds.average(), greens.average(), blues.average()).getRGB();
	}
	
	public static int averageColors(int col1, int col2) {
		int r1 = ColorHelper.redFromColor(col1);
		int g1 = ColorHelper.greenFromColor(col1);
		int b1 = ColorHelper.blueFromColor(col1);
		int r2 = ColorHelper.redFromColor(col2);
		int g2 = ColorHelper.greenFromColor(col2);
		int b2 = ColorHelper.blueFromColor(col2);
		return new Color((r1 + r2) / 2, (g1 + g2) / 2, (b1 + b2) / 2).getRGB();
	}
	
	public static int averageColors(int col1, int col2, double pFirst) {
		int r1 = ColorHelper.redFromColor(col1);
		int g1 = ColorHelper.greenFromColor(col1);
		int b1 = ColorHelper.blueFromColor(col1);
		int r2 = ColorHelper.redFromColor(col2);
		int g2 = ColorHelper.greenFromColor(col2);
		int b2 = ColorHelper.blueFromColor(col2);
		double pSecond = 1 - pFirst;
		return new Color((int)(r1 * pFirst + r2 * pSecond), (int)(g1 * pFirst + g2 * pSecond), (int)(b1 * pFirst + b2 * pSecond)).getRGB();
	}
	
	public static EnumChatFormatting darkenFormatting(EnumChatFormatting col) {
		if(col == EnumChatFormatting.AQUA) return EnumChatFormatting.DARK_AQUA;
		if(col == EnumChatFormatting.BLUE) return EnumChatFormatting.DARK_BLUE;
		if(col == EnumChatFormatting.GRAY) return EnumChatFormatting.DARK_GRAY;
		if(col == EnumChatFormatting.GREEN) return EnumChatFormatting.DARK_GREEN;
		if(col == EnumChatFormatting.LIGHT_PURPLE) return EnumChatFormatting.DARK_PURPLE;
		if(col == EnumChatFormatting.RED) return EnumChatFormatting.DARK_RED;
		if(col == EnumChatFormatting.YELLOW) return EnumChatFormatting.GOLD;
		if(col == EnumChatFormatting.DARK_GRAY) return EnumChatFormatting.BLACK;
		if(col == EnumChatFormatting.WHITE) return EnumChatFormatting.GRAY;
		return col;
	}

}
