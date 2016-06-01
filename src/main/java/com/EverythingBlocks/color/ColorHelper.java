package com.EverythingBlocks.color;

import java.awt.Color;
import java.util.List;

import com.EverythingBlocks.util.IntList;

import net.minecraft.util.text.TextFormatting;

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
	
	public static int subtractMixColors(int baseColor, int clampColor) {
		int baseRed = redFromColor(baseColor);
		int baseGreen = greenFromColor(baseColor);
		int baseBlue = blueFromColor(baseColor);
		int clampRed = redFromColor(clampColor);
		int clampGreen = greenFromColor(clampColor);
		int clampBlue = blueFromColor(clampColor);
		
		return new Color(baseRed * clampRed / 255, baseGreen * clampGreen / 255, baseBlue * clampBlue / 255).getRGB();
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
	
	public static TextFormatting darkenFormatting(TextFormatting col) {
		if(col == TextFormatting.AQUA) return TextFormatting.DARK_AQUA;
		if(col == TextFormatting.BLUE) return TextFormatting.DARK_BLUE;
		if(col == TextFormatting.GRAY) return TextFormatting.DARK_GRAY;
		if(col == TextFormatting.GREEN) return TextFormatting.DARK_GREEN;
		if(col == TextFormatting.LIGHT_PURPLE) return TextFormatting.DARK_PURPLE;
		if(col == TextFormatting.RED) return TextFormatting.DARK_RED;
		if(col == TextFormatting.YELLOW) return TextFormatting.GOLD;
		if(col == TextFormatting.DARK_GRAY) return TextFormatting.BLACK;
		if(col == TextFormatting.WHITE) return TextFormatting.GRAY;
		return col;
	}

}
