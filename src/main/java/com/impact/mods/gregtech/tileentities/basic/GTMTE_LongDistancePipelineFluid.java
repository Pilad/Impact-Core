package com.impact.mods.gregtech.tileentities.basic;

import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_PIPELINE_FLUID_BACK;
import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_PIPELINE_FLUID_FRONT;
import static com.impact.mods.gregtech.enums.Texture.Icons.OVERLAY_PIPELINE_FLUID_SIDE;

import com.impact.util.string.Language;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.objects.GT_RenderedTexture;
import gregtech.api.util.GT_Utility;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Inspired/ported from GregTech 6 under the LGPL license
 * <p>
 * Copyright (c) 2020 GregTech-6 Team
 * <p>
 * This file is part of GregTech.
 * <p>
 * GregTech is free software: you can redistribute it and/or modify it under the terms of the GNU
 * Lesser General Public License as published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * <p>
 * GregTech is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public License along with GregTech. If
 * not, see <http://www.gnu.org/licenses/>.
 */

public class GTMTE_LongDistancePipelineFluid extends GTMTE_LongDistancePipelineBase {

  final static FluidTankInfo[] emptyTank = {new FluidTankInfo(null, Integer.MAX_VALUE)};

  public GTMTE_LongDistancePipelineFluid(int aID, String aName, String aNameRegional,
      int aTier) {
    super(aID, aName, aNameRegional, aTier,Language.transDesc("pipeline.fluid", "Sends fluids over long distances"));
  }

  public GTMTE_LongDistancePipelineFluid(String aName, int aTier, String aDescription,
      ITexture[][][] aTextures) {
    super(aName, aTier, aDescription, aTextures);
  }

  @Override
  public boolean isSameClass(GTMTE_LongDistancePipelineBase other) {
    return other instanceof GTMTE_LongDistancePipelineFluid;
  }

  public IFluidHandler getTank() {
    final IGregTechTileEntity tTile = mTarget.getBaseMetaTileEntity();
    TileEntity tankTile = tTile.getTileEntityAtSide(tTile.getBackFacing());
    if (tankTile instanceof IFluidHandler) {
      return (IFluidHandler) tankTile;
    } else {
      return null;
    }
  }

  @Override
  public FluidTankInfo[] getTankInfo(ForgeDirection aSide) {
    if (checkTarget()) {
      final IFluidHandler tankTile = getTank();
      if (tankTile != null) {
        return tankTile.getTankInfo(aSide);
      }

    }

    return emptyTank;
  }

  public int getPipeMeta() {
    return 0;
  }

  @Override
  public int fill(ForgeDirection aSide, FluidStack aFluid, boolean aDoFill) {
    if (checkTarget()) {
      final IGregTechTileEntity tTile = mTarget.getBaseMetaTileEntity();
      final IFluidHandler tankTile = getTank();
      if (tankTile != null) {
        return tankTile
            .fill(ForgeDirection.getOrientation(tTile.getFrontFacing()), aFluid, aDoFill);
      }
    }
    return 0;
  }

  @Override
  public FluidStack drain(ForgeDirection aSide, FluidStack aFluid, boolean aDoDrain) {
    return null;
  }

  @Override
  public FluidStack drain(ForgeDirection aSide, int aMaxDrain, boolean aDoDrain) {
    return null;
  }

  @Override
  public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
    return new GTMTE_LongDistancePipelineFluid(mName, mTier, mDescription, mTextures);
  }

  @Override
  public ITexture[][][] getTextureSet(ITexture[] aTextures) {
    return new ITexture[0][0][0];
  }

  @Override
  public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing,
      byte aColorIndex, boolean aActive, boolean aRedstone) {
    if (aSide == aFacing) {
      return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
          new GT_RenderedTexture(OVERLAY_PIPELINE_FLUID_FRONT)};
    } else if (aSide == GT_Utility.getOppositeSide(aFacing)) {
      return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
          new GT_RenderedTexture(OVERLAY_PIPELINE_FLUID_BACK)};
    } else {
      return new ITexture[]{Textures.BlockIcons.MACHINE_CASINGS[mTier][aColorIndex + 1],
          new GT_RenderedTexture(OVERLAY_PIPELINE_FLUID_SIDE)};
    }
  }
}
