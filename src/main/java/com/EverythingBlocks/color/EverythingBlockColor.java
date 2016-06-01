package com.EverythingBlocks.color;

import java.awt.Color;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import com.EverythingBlocks.util.EBUtils;

/** Everything Block coloring class */
@SideOnly(Side.CLIENT)
public class EverythingBlockColor implements IBlockColor {
	
	/** The default color */
	private static final int DEFAULT = new Color(128, 128, 128).getRGB();

	@Override
    public int colorMultiplier(IBlockState state, IBlockAccess world, BlockPos pos, int renderPass) {
		if(pos != null) {
			return EBUtils.colorMultiplier(world, pos);
		} else {
			return DEFAULT;
		}
    }

}
