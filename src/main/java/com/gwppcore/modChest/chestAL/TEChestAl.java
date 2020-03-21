package com.gwppcore.modChest.chestAL;

import com.gwppcore.modChest.BASE.TE_BaseChest;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;

public final class TEChestAl extends TE_BaseChest
{
	public static ResourceLocation MODEL_CHEST = new ResourceLocation("gwppcore", "textures/entity/chestAl.png");

	public TEChestAl() {
		super(64, 81);
	}

	@Override
	public final String getInventoryName()
	{
		return "Aluminium Chest";
	}

	@Override
	public int invSize(){
		return 81;
	}



	@Override
	@SideOnly(Side.CLIENT)
	@Nonnull
	protected ResourceLocation getTexture()
	{
		return MODEL_CHEST;
	}
}