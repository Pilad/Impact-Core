package com.impact.mods.gregtech.tileentities.multi.parallelsystem;

import com.impact.mods.gregtech.blocks.Casing_Helper;
import com.impact.mods.gregtech.gui.parallelcomputer.Container_SuperParallelComputer;
import com.impact.mods.gregtech.gui.parallelcomputer.GUI_SuperParallelComputer;
import com.impact.mods.gregtech.tileentities.multi.implement.GT_MetaTileEntity_MultiParallelBlockBase;
import com.impact.util.string.MultiBlockTooltipBuilder;
import com.impact.util.vector.Vector3i;
import com.impact.util.vector.Vector3ic;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import net.minecraft.block.Block;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;
import org.lwjgl.input.Keyboard;
import space.impact.api.multiblocks.structure.IStructureDefinition;
import space.impact.api.multiblocks.structure.StructureDefinition;

import static com.impact.loader.ItemRegistery.InsideBlock;
import static net.minecraft.util.EnumChatFormatting.*;
import static space.impact.api.multiblocks.structure.StructureUtility.ofBlock;

public class GTMTE_ParallelComputer extends GT_MetaTileEntity_MultiParallelBlockBase<GTMTE_ParallelComputer> {
	
	public static Block CASING = Casing_Helper.sCasePage8_3;
	public static byte CASING_META = 7;
	public static ITexture INDEX_CASE = Textures.BlockIcons.casingTexturePages[8][CASING_META + 64];
	public static int CASING_TEXTURE_ID = CASING_META + 64 + 128 * 8;
	public int mMaxCapacityPP = 0;
	public int mCurrentCapacityPP = 0;
	static IStructureDefinition<GTMTE_ParallelComputer> definition =
			StructureDefinition.<GTMTE_ParallelComputer>builder()
					.addShape("main", new String[][]{
							{"AA", "AA", "~A", "AA"},
							{"AA", "BB", "BB", "AA"},
							{"AA", "BB", "BB", "AA"},
							{"AA", "AA", "AA", "AA"},
					})
					.addElement('A', ofBlock(CASING, CASING_META))
					.addElement('B', ofBlock(InsideBlock, 2))
					.build();
	
	@Override
	public void construct(ItemStack itemStack, boolean b) {
		buildPiece(itemStack, b, 0, 2, 0);
	}
	
	@Override
	public IStructureDefinition<GTMTE_ParallelComputer> getStructureDefinition() {
		return definition;
	}
	
	//region Register
	public GTMTE_ParallelComputer(int aID, String aNameRegional) {
		super(aID, "impact.multis.parallelcomputer", aNameRegional);
	}
	
	public GTMTE_ParallelComputer(String aName) {
		super(aName);
	}
	
	
	@Override
	public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
		return new GTMTE_ParallelComputer(this.mName);
	}
	//endregion
	
	@Override
	public ITexture[] getTexture(final IGregTechTileEntity aBaseMetaTileEntity, final byte aSide, final byte aFacing, final byte aColorIndex, final boolean aActive, final boolean aRedstone) {
		return aSide == aFacing ? new ITexture[]{INDEX_CASE, new GT_RenderedTexture(aActive ? Textures.BlockIcons.MP1a : Textures.BlockIcons.MP1)} : new ITexture[]{INDEX_CASE};
	}
	
	@Override
	public Object getClientGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new GUI_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, getLocalName());
	}
	
	@Override
	public Object getServerGUI(int aID, InventoryPlayer aPlayerInventory, IGregTechTileEntity aBaseMetaTileEntity) {
		return new Container_SuperParallelComputer(aPlayerInventory, aBaseMetaTileEntity, this);
	}
	
	@Override
	protected MultiBlockTooltipBuilder createTooltip() {
		MultiBlockTooltipBuilder b = new MultiBlockTooltipBuilder("psc");
		b
				.addTypeMachine("name", "Super Parallel Computer")
				.addSeparator()
				.addController()
				.addEnergyHatch(1)
				.addMaintenanceHatch()
				.addOtherStructurePart("psc.other.0", "Computer Rack", "psc.other.1", "Empty Rack Casing, Right Side")
				.addOtherStructurePart("psc.other.2", "Parallel Hatch Out", "psc.other.3", "Empty Rack Casing, Left Side")
				.addOtherStructurePart("psc.other.4", "Empty Rack Casing", "psc.other.5", "inside")
				.addCasingInfo("psc.case", "Computer Casing", 13)
				.signAndFinalize();
		return b;
	}
	
	@Override
	public void onPostTick(IGregTechTileEntity aBaseMetaTileEntity, long aTick) {
		super.onPostTick(aBaseMetaTileEntity, aTick);
		if (aBaseMetaTileEntity.isServerSide() && aTick % 20 == 0) {
			connect(aBaseMetaTileEntity);
			int maxCur = 0;
			boolean isActive = false;
			for (GTMTE_ComputerRack rack : sComputerRack) {
				if (aBaseMetaTileEntity.isActive()) {
					maxCur += rack.mCapacityP;
					isActive = true;
				}
				rack.getBaseMetaTileEntity().setActive(isActive);
			}
			setMaxCapacityPP(maxCur);
			for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
				boolean checkSum = mCurrentCapacityPP - ph.mMaxParallel >= 0;
				if (checkSum && ph.getBaseMetaTileEntity().isAllowedToWork()) {
					mCurrentCapacityPP -= ph.mMaxParallel;
				}
				ph.mIsTrueRecipe = checkSum;
			}
		}
	}
	
	public void connect(IGregTechTileEntity aBaseMetaTileEntity) {
		boolean isActive = false;
		for (GTMTE_ParallelHatch_Output ph : sParallHatchesOut) {
			if (aBaseMetaTileEntity.isActive()) {
				if (ph.getBaseMetaTileEntity().isAllowedToWork() && mIsConnect) {
					isActive = true;
				}
			}
			ph.setRecipe(isActive);
		}
	}
	
	@Override
	public boolean checkRecipe(ItemStack aStack) {
		this.mMaxProgresstime    = 10;
		this.mEfficiency         = (10000 - (this.getIdealStatus() - this.getRepairStatus()) * 1000);
		this.mEfficiencyIncrease = 10000;
		this.mEUt                = 8192 + mMaxCapacityPP;
		this.mEUt                = -this.mEUt;
		return true;
	}
	
	
	public int getCurrentCapacityPP() {
		return mCurrentCapacityPP;
	}
	
	public void setCurrentCapacityPP(int curCaPP) {
		mCurrentCapacityPP = curCaPP;
	}
	
	public int getMaxCapacityPP() {
		return mMaxCapacityPP;
	}
	
	public void setMaxCapacityPP(int setMax) {
		setCurrentCapacityPP(setMax);
		mMaxCapacityPP = setMax;
		mParallel      = setMax;
	}
	
	@Override
	public boolean machineStructure(IGregTechTileEntity thisController) {
		//region Structure
		final Vector3ic forgeDirection = new Vector3i(
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetX,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetY,
				ForgeDirection.getOrientation(thisController.getBackFacing()).offsetZ
		);
		boolean formationChecklist = true;
		for (int X = 0; X <= 1; X++) {
			for (int Y = -1; Y <= 2; Y++) {
				if (X == 0 && Y == 0) {
					continue;
				}
				final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, 0);
				IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
				if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
						&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
					if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
							&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
					} else {
						formationChecklist = false;
					}
				}
			}
		}
		int additionalZ = -4;
		int ParallelsZ = 0;
		int RacksZ = 0;
		for (int X = 0; X <= 1; X++) {
			for (int Y = -1; Y <= 2; Y++) {
				for (int Z = -1; Z >= -17; Z--) {
					final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
					IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
					
					//Racks
					if (X == 1 && (Y == 0 || Y == 1)) {
						if (!super.addRackHatch(currentTE, CASING_TEXTURE_ID)) {
							if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING
									&& thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META) {
								RacksZ = Z;
								break;
							} else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock
									&& thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2) {
							} else {
								formationChecklist = false;
							}
						}
					}
					//Parallels
					if (X == 0 && (Y == 0 || Y == 1)) {
						if (!super.addParallHatchToMachineList(currentTE, CASING_TEXTURE_ID)) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
								ParallelsZ = Z;
								break;
							} else if (thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == InsideBlock
									&& thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == 2) {
							} else {
								formationChecklist = false;
							}
						}
					}
				}
			}
		}
		if (ParallelsZ < RacksZ || RacksZ < ParallelsZ) {
			formationChecklist = false;
		} else {
			additionalZ = RacksZ;
		}
		for (int X = 0; X <= 1; X++) {
			for (int Y = -1; Y <= 2; Y++) {
				for (int Z = -1; Z >= additionalZ; Z--) {
					if (Y == -1 || Y == 2) {
						final Vector3ic offset = rotateOffsetVector(forgeDirection, X, Y, Z);
						IGregTechTileEntity currentTE = thisController.getIGregTechTileEntityOffset(offset.x(), offset.y(), offset.z());
						if (!super.addMaintenanceToMachineList(currentTE, CASING_TEXTURE_ID)
								&& !super.addEnergyInputToMachineList(currentTE, CASING_TEXTURE_ID)
								&& !super.addOutputToMachineList(currentTE, CASING_TEXTURE_ID)) {
							if ((thisController.getBlockOffset(offset.x(), offset.y(), offset.z()) == CASING)
									&& (thisController.getMetaIDOffset(offset.x(), offset.y(), offset.z()) == CASING_META)) {
							} else {
								formationChecklist = false;
							}
						}
					}
				}
			}
		}
		if (this.mEnergyHatches.size() > 1) {
			formationChecklist = false;
		}
		if (this.mMaintenanceHatches.size() != 1) {
			formationChecklist = false;
		}
		return formationChecklist;
	}
	
	@Override
	public void saveNBTData(NBTTagCompound aNBT) {
		super.saveNBTData(aNBT);
		aNBT.setInteger("mMaxCapacityPP", mMaxCapacityPP);
		aNBT.setInteger("mCurrentCapacityPP", mCurrentCapacityPP);
	}
	
	@Override
	public void loadNBTData(NBTTagCompound aNBT) {
		super.loadNBTData(aNBT);
		mMaxCapacityPP     = aNBT.getInteger("mMaxCapacityPP");
		mCurrentCapacityPP = aNBT.getInteger("mCurrentCapacityPP");
	}
	
	@Override
	public int getParallelCurrent() {
		if (getMaxCapacityPP() < 4) {
			return -1;
		}
		return getMaxCapacityPP();
	}
	
	@Override
	public int getPollutionPerTick(ItemStack aStack) {
		return 0;
	}
	
	
	public String[] getInfoData() {
		return new String[]{
				"Storage PP: " + GREEN + getMaxCapacityPP() + RESET + " / " + YELLOW + getCurrentCapacityPP() + "PP",
				"Usage Energy: " + RED + -mEUt + RESET + " EU/t",
				"Max Voltage: " + YELLOW + getMaxInputVoltage() + RESET + " EU/t ",
				"Maintenance: " + ((super.getRepairStatus() == super.getIdealStatus()) ? GREEN + "Good " + YELLOW + mEfficiency / 100.0F + " %" + RESET : RED + "Has Problems " + mEfficiency / 100.0F + " %" + RESET),
		};
	}
}