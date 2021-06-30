package com.impact.mods.gregtech.tileentities.multi.units;

import static gregtech.api.enums.GT_Values.VN;

import com.impact.util.string.MultiBlockTooltipBuilder;
import gregtech.api.enums.ItemList;
import gregtech.api.enums.Materials;
import gregtech.api.gui.GT_GUIContainer_MultiMachine;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_Utility;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

public class GTMTE_DrillerWater extends GTMTE_DrillerBase {

  private int mtier = 0;


  public GTMTE_DrillerWater(int aID, String aName, String aNameRegional) {
    super(aID, aName, aNameRegional);
  }

  public GTMTE_DrillerWater(String aName) {
    super(aName);
  }

  @Override
  public String[] getDescription() {
    final MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder();
    b
        .addInfo("ewp.info.0")
		.addTypeMachine("ewp.name")
        .addInfo("ewp.info.1")
        .addInfo("ewp.info.2")
        .addSeparator()
        .addinfoB("bwp.info.5")
        .addinfoBTab("bwp.info.6")
        .addinfoBTab("bwp.info.7")
        .addinfoBTab("bwp.info.8")
        .addinfoBTab("bwp.info.9")
        .addinfoBTab("bwp.info.10")
        .addinfoBTab("bwp.info.11")
        .addinfoBTab("bwp.info.12")
        .addinfoBTab("bwp.info.13")
        .addEnergyHatch("ewp.hatches")
        .addMaintenanceHatch("ewp.hatches")
        .addInputBus("ewp.hatches")
        .addOutputHatch("ewp.hatches")
        .addCasingInfo("ewp.case")
        .signAndFinalize();
    if (Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
      return b.getControlInfo();
    }
    if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
      return b.getStructureInformation();
    }
    return b.getInformation();
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_DrillerWater(mName);
  }


  @Override
  public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory,
      IGregTechTileEntity aBaseMetaTileEntity) {
    return new GT_GUIContainer_MultiMachine(aPlayerInventory, aBaseMetaTileEntity, getLocalName(),
        "DrillingRig.png");
  }

  @Override
  protected boolean checkHatches() {
    return !mMaintenanceHatches.isEmpty() && !mOutputHatches.isEmpty() && !mEnergyHatches.isEmpty();
  }

  @Override
  protected void setElectricityStats() {
    this.mEfficiencyIncrease = 10000;
    this.mEfficiency = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
    this.mtier = Math.max(0, getTierEnergyHatch());
    this.mEUt = -7 << (this.mtier
        << 1);//(1/4) A of current tier when at bottom (7/8) A of current tier while mining
    this.mMaxProgresstime = 20;
  }

  @Override
  protected boolean workingAtBottom(ItemStack aStack, int xDrill, int yDrill, int zDrill, int xPipe,
      int zPipe, int yHead, int oldYHead) {
    switch (tryLowerPipeState(true)) {
      case 0:
        workState = STATE_DOWNWARD;
        if (this.mEfficiency > 0) {
          setElectricityStats();
        }
        return true;
      case 3:
        workState = STATE_UPWARD;
        return true;
    }

    if (reachingVoidOrBedrock()) {
      addOutput(GT_ModHandler.getWater((((1 << this.mtier) * 2) * getWaterInBiomes() * 2) * 20));
    }
    if (mEfficiency == 10000) {
      workState = STATE_AT_BOTTOM;
    } else {
      workState = STATE_UPWARD;
    }
    return true;
  }

  @Override
  protected ItemList getCasingBlockItem() {
    return ItemList.Casing_SolidSteel;
  }

  @Override
  protected Materials getFrameMaterial() {
    return Materials.Steel;
  }

  @Override
  protected int getCasingTextureIndex() {
    return 16;
  }

  @Override
  protected int getMinTier() {
    return 1;
  }

  @Override
  public String[] getInfoData() {
    EnumChatFormatting GREEN = EnumChatFormatting.GREEN;
    EnumChatFormatting RESET = EnumChatFormatting.RESET;
    EnumChatFormatting YELLOW = EnumChatFormatting.YELLOW;
    EnumChatFormatting RED = EnumChatFormatting.RED;
    return new String[]{
        "Water Drilling: " + YELLOW + (((1 << this.mtier) * 2) * getWaterInBiomes()) + RESET
            + " L/t",
        "Biome Coefficient: " + GREEN + getWaterInBiomes() + RESET + " L/t",
        "Usage Energy: " + RED + -mEUt + RESET + " EU/t",
        "Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t " + YELLOW + VN[GT_Utility
            .getTier(getMaxInputVoltage())] + RESET,
        "Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus())
            ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET
            : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
    };
  }

}

