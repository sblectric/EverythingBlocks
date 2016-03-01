package com.EverythingBlocks.tiles;

import com.EverythingBlocks.blocks.EBBlocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TileEntityBlockEverything extends TileEntity {
	
	public ItemStack contains;
	
	public TileEntityBlockEverything() {}
	
	/** Load tile entity data */
	@Override
    public void readFromNBT(NBTTagCompound tag) {
    	super.readFromNBT(tag);
    	this.contains = ItemStack.loadItemStackFromNBT(tag.getCompoundTag("contains"));
    }
    
	/** Save tile entity data */
    @Override
    public void writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	if(this.contains != null) {
	    	NBTTagCompound c = new NBTTagCompound();
	    	this.contains.writeToNBT(c);
	    	tag.setTag("contains", c);
    	}
    }
    
	@Override
	public Packet getDescriptionPacket() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		writeToNBT(tagCompound);
		return new S35PacketUpdateTileEntity(pos, 0, tagCompound);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
	}

}
