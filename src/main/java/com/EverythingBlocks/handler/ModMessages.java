package com.EverythingBlocks.handler;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms.IMCMessage;

import com.EverythingBlocks.crafting.CraftableToBlock;
import com.EverythingBlocks.render.EverythingColor;

public class ModMessages {
	
	/** Handle mod messages of all types */
	public static void handleMessage(IMCEvent event) {
		for(IMCMessage msg : event.getMessages()) {
			
			// remove an Everything Block of this item stack
			if(msg.key.equals("stackRemoveBlock")) {
				ItemStack s = ItemStack.loadItemStackFromNBT(msg.getNBTValue());
				CraftableToBlock.blacklistStack(s);
			
			// override stack color
			} else if(msg.key.equals("stackColorOverride")) {
				ItemStack s = ItemStack.loadItemStackFromNBT(msg.getNBTValue());
				int colorOverride = msg.getNBTValue().getInteger("colorOverride");
				EverythingColor.addStackToColorCache(s, colorOverride);
			}
			
			
		}
	}

}
