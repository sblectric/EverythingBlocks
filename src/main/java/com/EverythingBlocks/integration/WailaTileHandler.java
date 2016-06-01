package com.EverythingBlocks.integration;

import java.util.List;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

import com.EverythingBlocks.api.IBlockEverything;
import com.EverythingBlocks.tiles.TileEntityBlockEverything;

@Optional.Interface(iface = "mcp.mobius.waila.api.IWailaDataProvider", modid = "Waila")
public class WailaTileHandler implements IWailaDataProvider {
	
	public static final String callbackRegister = "com.EverythingBlocks.integration.WailaTileHandler.callbackRegister";
	
	/** Perform the registrations */
	@Optional.Method(modid = "Waila")
	public static void callbackRegister(IWailaRegistrar register) {
		WailaTileHandler instance = new WailaTileHandler();
		
		// register providers for the blocks needed
		register.registerStackProvider(instance, IBlockEverything.class);
		register.registerBodyProvider(instance, IBlockEverything.class);
		register.registerNBTProvider(instance, IBlockEverything.class);
	}

	/** Change tooltip item stack */
	@Override
	@Optional.Method(modid = "Waila")
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		
		// get the icon from the tile entity
		TileEntity te = accessor.getTileEntity();
		if(te instanceof TileEntityBlockEverything) {
			TileEntityBlockEverything tile = (TileEntityBlockEverything)te;
			if(tile.contains == null) return accessor.getStack();
			ItemStack stack = new ItemStack(accessor.getBlock());
			stack.setTagCompound(new NBTTagCompound());
			NBTTagCompound tags = new NBTTagCompound();
			tile.contains.writeToNBT(tags);
			stack.getTagCompound().setTag("contains", tags);
			return stack;
		} else {
			return accessor.getStack();
		}
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaHead(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaBody(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		itemStack.getItem().addInformation(itemStack, accessor.getPlayer(), currenttip, false);
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public List<String> getWailaTail(ItemStack itemStack, List<String> currenttip, IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return currenttip;
	}

	@Override
	@Optional.Method(modid = "Waila")
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity te, NBTTagCompound tag, World world, BlockPos pos) {
		if (te != null) te.writeToNBT(tag);
        return tag;
	}

}
