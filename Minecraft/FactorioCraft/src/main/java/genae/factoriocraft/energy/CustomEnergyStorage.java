package genae.factoriocraft.energy;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

public class CustomEnergyStorage extends EnergyStorage {

    public CustomEnergyStorage(int capacity)
    {

        this(capacity, capacity, capacity, 0);
    }
    public CustomEnergyStorage(int capacity, int maxTransfer)
    {

        this(capacity, maxTransfer, maxTransfer, 0);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract)
    {
        this(capacity, maxReceive, maxExtract, 0);
    }

    public CustomEnergyStorage(int capacity, int maxReceive, int maxExtract, int energy)
    {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void readFromNBT(NBTTagCompound compound){
        this.energy = compound.getInteger("energy");

    }

    public void recieveEnergyInternal(int energy){
        this.energy += Math.min(energy, getMaxEnergyStored() - getEnergyStored());
    }

    public void writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", this.energy);
    }

    public int getMaxExtract() {
        return maxExtract;
    }
    public int getMaxRecieve() {
        return maxReceive;
    }
}
