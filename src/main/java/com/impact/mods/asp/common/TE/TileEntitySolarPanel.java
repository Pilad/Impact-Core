package com.impact.mods.asp.common.te;

import com.impact.mods.asp.common.ContainerAdvSolarPanel;
import galaxyspace.core.configs.GSConfigDimensions;
import gregtech.api.GregTech_API;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import ic2.api.energy.event.EnergyTileLoadEvent;
import ic2.api.energy.event.EnergyTileUnloadEvent;
import ic2.api.energy.tile.IEnergySource;
import ic2.api.energy.tile.IEnergyTile;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.network.INetworkDataProvider;
import ic2.api.network.INetworkUpdateListener;
import ic2.api.tile.IWrenchable;
import micdoodle8.mods.galacticraft.planets.asteroids.ConfigManagerAsteroids;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class TileEntitySolarPanel extends TileEntityBase implements IEnergyTile, IWrenchable,
        IEnergySource, IInventory, INetworkDataProvider, INetworkUpdateListener {

    public static Random randomizer = new Random();
    public static int tooltipsGeneration;
    private static final List<String> fields = Arrays.asList();
    public int ticker;
    public int generating;
    public int genDay;
    public int genNight;
    public boolean initialized;
    public boolean sunIsUp;
    public boolean skyIsVisible;
    public boolean addedToEnergyNet;
    public int fuel;
    public int storage;
    public String panelName;
    public int production;
    public int maxStorage;
    public boolean loaded = false;
    private short facing = 2;
    private boolean noSunWorld;
    private boolean wetBiome;
    private final int machineTire;
    private final boolean created = false;
    private ItemStack[] chargeSlots;
    private int lastX;
    private int lastY;
    private int lastZ;
    private final int solarType;

    public TileEntitySolarPanel(String gName, int typeSolar, int generation) {
        this.solarType = typeSolar;
        this.genDay = generation;
        this.genNight = generation / 8;
        this.storage = 0;
        this.panelName = gName;
        this.sunIsUp = false;
        this.skyIsVisible = false;
        this.maxStorage = generation * 1000 * 4;
        this.chargeSlots = new ItemStack[4];
        this.initialized = false;
        this.production = generation;
        this.ticker = randomizer.nextInt(this.tickRate());
        this.lastX = this.xCoord;
        this.lastY = this.yCoord;
        this.lastZ = this.zCoord;
        this.machineTire = 2147483647;
        tooltipsGeneration = generation;
    }

    public void validate() {
        super.validate();
        if (!this.isInvalid() && this.worldObj.blockExists(this.xCoord, this.yCoord, this.zCoord)) {
            this.onLoaded();
        }
    }

    public void invalidate() {
        if (this.loaded) {
            this.onUnloaded();
        }

        super.invalidate();
    }

    public void onLoaded() {
        if (!this.worldObj.isRemote) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileLoadEvent(this));
            this.addedToEnergyNet = true;
        }

        this.loaded = true;
    }

    public void onChunkUnload() {
        if (this.loaded) {
            this.onUnloaded();
        }

        super.onChunkUnload();
    }

    public void onUnloaded() {
        if (!this.worldObj.isRemote && this.addedToEnergyNet) {
            MinecraftForge.EVENT_BUS.post(new EnergyTileUnloadEvent(this));
            this.addedToEnergyNet = false;
        }

        this.loaded = false;
    }

    public void intialize() {
        this.wetBiome = this.worldObj.getWorldChunkManager().getBiomeGenAt(this.xCoord, this.zCoord)
                .getIntRainfall() > 0;
        this.noSunWorld = this.worldObj.provider.hasNoSky;
        this.updateVisibility();
        this.initialized = true;
        if (!this.addedToEnergyNet) {
            this.onLoaded();
        }

    }

    private boolean toGTSolar() {
        int meta = 14595 + solarType - 1;
        worldObj.setBlock(xCoord, yCoord, zCoord, GregTech_API.sBlockMachines, GregTech_API.METATILEENTITIES[meta].getTileEntityBaseType(), 3);
        TileEntity tile = worldObj.getTileEntity(xCoord, yCoord, zCoord);
        ((IGregTechTileEntity) tile).setInitialValuesAsNBT(null, (short) meta);
        return true;
    }

    public void updateEntity() {
        super.updateEntity();
        if (!this.initialized && this.worldObj != null) {
            this.intialize();
        }

        if (!this.worldObj.isRemote) {
            if (this.lastX != this.xCoord || this.lastZ != this.zCoord || this.lastY != this.yCoord) {
                this.lastX = this.xCoord;
                this.lastY = this.yCoord;
                this.lastZ = this.zCoord;
                this.onUnloaded();
                this.intialize();
            }

            if (toGTSolar()) return;

            this.gainFuel();
            if (this.generating > 0) {
                if (this.storage + this.generating <= this.maxStorage) {
                    this.storage += this.generating;
                } else {
                    this.storage = this.maxStorage;
                }
            }

            boolean needInvUpdate = false;
            double sentPacket = 0.0D;

            for (int i = 0; i < this.chargeSlots.length; ++i) {
                if (this.chargeSlots[i] != null && this.chargeSlots[i].getItem() instanceof IElectricItem
                        && this.storage > 0) {
                    sentPacket = ElectricItem.manager
                            .charge(this.chargeSlots[i], this.storage, 2147483647, false, false);
                    if (sentPacket > 0.0D) {
                        needInvUpdate = true;
                    }

                    this.storage = (int) ((double) this.storage - sentPacket);
                }
            }

            if (needInvUpdate) {
                super.markDirty();
            }

        }
    }

    public int gainFuel() {
        if (this.ticker++ % this.tickRate() == 0) {
            this.updateVisibility();
        }

        if (this.sunIsUp && this.skyIsVisible) {
            this.generating = 0 + this.genDay;
            return this.generating;
        } else if (this.skyIsVisible) {
            this.generating = 0 + this.genNight;
            return this.generating;
        } else {
            this.generating = 0;
            return this.generating;
        }
    }

    public void updateVisibility() {
        World world = this.worldObj;
        boolean rainWeather = this.wetBiome && (world.isRaining() || world.isThundering());
//        try {
//            int Asteroids = ConfigManagerAsteroids.dimensionIDAsteroids;
//            int KuiperBelt = GSConfigDimensions.dimensionIDKuiperBelt;
//
//
//            if (world.provider.dimensionId == Asteroids || world.provider.dimensionId == KuiperBelt) {
//                this.sunIsUp = world.isDaytime();
//                this.skyIsVisible = world.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord);
//                return;
//            }
//        } catch (Exception ignored) {}

        this.sunIsUp = world.isDaytime() && !rainWeather;
        this.skyIsVisible = world.canBlockSeeTheSky(this.xCoord, this.yCoord + 1, this.zCoord) && !this.noSunWorld;
    }

    public void readFromNBT(NBTTagCompound nbttagcompound) {
        super.readFromNBT(nbttagcompound);
        this.storage = nbttagcompound.getInteger("storage");
        this.lastX = nbttagcompound.getInteger("lastX");
        this.lastY = nbttagcompound.getInteger("lastY");
        this.lastZ = nbttagcompound.getInteger("lastZ");
        NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
        this.chargeSlots = new ItemStack[this.getSizeInventory()];

        for (int i = 0; i < nbttaglist.tagCount(); ++i) {
            NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
            int j = nbttagcompound1.getByte("Slot") & 255;
            if (j >= 0 && j < this.chargeSlots.length) {
                this.chargeSlots[j] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
            }
        }

    }

    public void writeToNBT(NBTTagCompound nbttagcompound) {
        super.writeToNBT(nbttagcompound);
        NBTTagList nbttaglist = new NBTTagList();
        nbttagcompound.setInteger("storage", this.storage);
        nbttagcompound.setInteger("lastX", this.lastX);
        nbttagcompound.setInteger("lastY", this.lastY);
        nbttagcompound.setInteger("lastZ", this.lastZ);

        for (int i = 0; i < this.chargeSlots.length; ++i) {
            if (this.chargeSlots[i] != null) {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte) i);
                this.chargeSlots[i].writeToNBT(nbttagcompound1);
                nbttaglist.appendTag(nbttagcompound1);
            }
        }

        nbttagcompound.setTag("Items", nbttaglist);
    }

    public double gaugeEnergyScaled(double i) {
        return (double) this.storage * i / (double) this.maxStorage;
    }

    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return entityplayer.getDistance((double) this.xCoord + 0.5D, (double) this.yCoord + 0.5D,
                (double) this.zCoord + 0.5D) <= 64.0D;
    }

    public void openInventory() {
    }

    public void closeInventory() {
    }

    public int tickRate() {
        return 128;
    }

    public short getFacing() {
        return this.facing;
    }

    public void setFacing(short facing) {
        this.facing = facing;
    }

    public boolean wrenchCanSetFacing(EntityPlayer entityplayer, int i) {
        return false;
    }

    public boolean wrenchCanRemove(EntityPlayer entityplayer) {
        return true;
    }

    public float getWrenchDropRate() {
        return 1.0F;
    }

    public ItemStack getWrenchDrop(EntityPlayer entityPlayer) {
        return new ItemStack(this.worldObj.getBlock(this.xCoord, this.yCoord, this.zCoord), 1,
                this.worldObj.getBlockMetadata(this.xCoord, this.yCoord, this.zCoord));
    }

    public int getSizeInventory() {
        return 4;
    }

    public ItemStack getStackInSlot(int i) {
        return this.chargeSlots[i];
    }

    public ItemStack decrStackSize(int i, int j) {
        if (this.chargeSlots[i] != null) {
            ItemStack itemstack1;
            if (this.chargeSlots[i].stackSize <= j) {
                itemstack1 = this.chargeSlots[i];
                this.chargeSlots[i] = null;
                return itemstack1;
            } else {
                itemstack1 = this.chargeSlots[i].splitStack(j);
                if (this.chargeSlots[i].stackSize == 0) {
                    this.chargeSlots[i] = null;
                }

                return itemstack1;
            }
        } else {
            return null;
        }
    }

    public void setInventorySlotContents(int i, ItemStack itemstack) {
        this.chargeSlots[i] = itemstack;
        if (itemstack != null && itemstack.stackSize > this.getInventoryStackLimit()) {
            itemstack.stackSize = this.getInventoryStackLimit();
        }

    }

    public String getInventoryName() {
        return "Impact Solar Panel";
    }

    public boolean hasCustomInventoryName() {
        return false;
    }

    public int getInventoryStackLimit() {
        return 64;
    }

    public Container getGuiContainer(InventoryPlayer inventoryplayer) {
        return new ContainerAdvSolarPanel(inventoryplayer, this);
    }

    public ItemStack getStackInSlotOnClosing(int var1) {
        if (this.chargeSlots[var1] != null) {
            ItemStack var2 = this.chargeSlots[var1];
            this.chargeSlots[var1] = null;
            return var2;
        } else {
            return null;
        }
    }

    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    public void onNetworkUpdate(String field) {
    }

    public List<String> getNetworkedFields() {
        return fields;
    }

    public boolean emitsEnergyTo(TileEntity receiver, ForgeDirection direction) {
        return true;
    }

    public double getOfferedEnergy() {
        return Math.min(this.production, this.storage);
    }

    public void drawEnergy(double amount) {
        this.storage = (int) ((double) this.storage - amount);
    }

    public int getSourceTier() {
        return this.machineTire;
    }
}
