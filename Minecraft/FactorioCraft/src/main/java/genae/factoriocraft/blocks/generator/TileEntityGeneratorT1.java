package genae.factoriocraft.blocks.generator;

import genae.factoriocraft.energy.CustomEnergyStorage;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;


public class TileEntityGeneratorT1 extends TileEntity implements ITickable {

    public static final int SIZE = 1;


    public ItemStackHandler itemStackHandler = new ItemStackHandler(SIZE);
    public CustomEnergyStorage energyStorage = new CustomEnergyStorage(10000, 0, 128);
    public int cookTime;
    public int cookTimeMax;
    public boolean cooking;


    @Override
    public NBTTagCompound getUpdateTag() {
        // getUpdateTag() is called whenever the chunkdata is sent to the
        // client. In contrast getUpdatePacket() is called when the tile entity
        // itself wants to sync to the client. In many cases you want to send
        // over the same information in getUpdateTag() as in getUpdatePacket().
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        // Prepare a packet for syncing our TE to the client. Since we only have to sync the stack
        // and that's all we have we just write our entire NBT here. If you have a complex
        // tile entity that doesn't need to have all information on the client you can write
        // a more optimal NBT here.
        NBTTagCompound nbtTag = new NBTTagCompound();
        this.writeToNBT(nbtTag);
        return new SPacketUpdateTileEntity(getPos(), 1, nbtTag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity packet) {
        // Here we get the packet from the server and read it into our client side tile entity
        this.readFromNBT(packet.getNbtCompound());
    }

    public void getActualState() {

    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey("items")) {
            itemStackHandler.deserializeNBT((NBTTagCompound) compound.getTag("items"));
        }
        this.cookTime = compound.getInteger("cookTime");
        this.energyStorage.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        compound.setTag("items", itemStackHandler.serializeNBT());
        compound.setInteger("cookTime", cookTime);
        this.energyStorage.writeToNBT(compound);
        return compound;
    }

    public boolean canInteractWith(EntityPlayer playerIn) {
        // If we are too far away from this tile entity you cannot use it
        return !isInvalid() && playerIn.getDistanceSq(pos.add(0.5D, 0.5D, 0.5D)) <= 64D;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return true;
        }
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Override
    public <T> T getCapability(Capability<T> capability, EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(itemStackHandler);
        }
        if(capability == CapabilityEnergy.ENERGY){
            return (T) this.energyStorage;
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void update() {
        transmitEnergy();
        boolean isPowered = getWorld().isBlockIndirectlyGettingPowered(pos) > 0;
        boolean isFull = this.energyStorage.getMaxEnergyStored() == this.energyStorage.getEnergyStored();
        ItemStack burningItem = itemStackHandler.getStackInSlot(0);
        int generationPerTick = 25;
        if(isFull || !isPowered || (burningItem.isEmpty() && cookTime == 0) || (!isItemFuel(burningItem) && cookTime == 0))
        {
            if(cooking)
                BlockGeneratorT1.setBlockState(isPowered, cooking = false, isFull, getWorld(), pos);
            return;
        }
        if(cookTime == 0){
            cookTime = getFuelValue(burningItem)/generationPerTick;
            cookTimeMax = getFuelValue(burningItem)/generationPerTick;
            burningItem.shrink(1);
        }
        if(!cooking)
            BlockGeneratorT1.setBlockState(isPowered, cooking = true, isFull, getWorld(), pos);
        cookTime--;
        this.energyStorage.recieveEnergyInternal(generationPerTick);
    }

    private int getFuelValue(ItemStack burningItem) {
        if(burningItem.getItem() == Items.COAL)
            return 300;
        return 0;
    }

    private boolean isItemFuel(ItemStack burningItem) {
        return getFuelValue(burningItem) > 0;
    }

    public int getBlockState() {
        boolean isPowered = getWorld().isBlockIndirectlyGettingPowered(pos) > 0;
        boolean isFull = this.energyStorage.getMaxEnergyStored() == this.energyStorage.getEnergyStored();
        return cooking ? 2 : (isFull && isPowered ? 3 : (isFull ? 4 : (isPowered ? 1 : 0)));
    }

    private void transmitEnergy() {
        for (int i = 0; i < 6; i++) {
            BlockPos recieverPos = new BlockPos(this.pos.getX() + EnumFacing.getFront(i).getFrontOffsetX(),this.pos.getY() + EnumFacing.getFront(i).getFrontOffsetY(),this.pos.getZ() + EnumFacing.getFront(i).getFrontOffsetZ());

            final TileEntity recieverTileEntity = this.world.getTileEntity(recieverPos);
            if (recieverTileEntity != null) {
                final IEnergyStorage recieverStorage = recieverTileEntity.getCapability(CapabilityEnergy.ENERGY, EnumFacing.getFront(i).getOpposite());
                if(recieverStorage != null && recieverStorage.canReceive()){
                    energyStorage.extractEnergy(recieverStorage.receiveEnergy(Math.min(energyStorage.getMaxExtract(),energyStorage.getEnergyStored()), false), false);
                }
            }
        }
    }
}
