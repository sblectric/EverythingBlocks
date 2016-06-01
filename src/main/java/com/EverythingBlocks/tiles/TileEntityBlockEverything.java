package com.EverythingBlocks.tiles;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    	super.writeToNBT(tag);
    	if(this.contains != null) {
	    	NBTTagCompound c = new NBTTagCompound();
	    	this.contains.writeToNBT(c);
	    	tag.setTag("contains", c);
    	}
    	return tag;
    }
    
	@Override
	public NBTTagCompound getUpdateTag() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		return this.writeToNBT(tagCompound);
	}
 
	@Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.getPos(), 0, getUpdateTag());
    }
	
    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		this.readFromNBT(pkt.getNbtCompound());
    }

}
