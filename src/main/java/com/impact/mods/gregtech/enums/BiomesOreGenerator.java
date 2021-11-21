package com.impact.mods.gregtech.enums;

import com.impact.core.Impact_API;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.item.ItemStack;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.impact.util.Utilits.equalsIntArray;
import static gregtech.api.enums.Materials.*;
import static gregtech.api.enums.OrePrefixes.crushed;
import static gregtech.api.enums.OrePrefixes.dust;

// TODO: 21.11.2021 add Veins
public enum BiomesOreGenerator {
	
	IRON_BASIC_VEIN("Iron Basic", BiomeGenBase.desert, 0, Iron),
	COPPER_BASIC_VEIN("Copper Basic", BiomeGenBase.desert, 0, Copper),
	TIN_BASIC_VEIN("Tin Basic", null, 0, Tin),
	COAL_BASIC_VEIN("Coal Basic", null, 0, Coal, Lignite),
	REDSTONE_BASIC_VEIN("Redstone Basic", null, 0, Redstone),

//	MINERALS_BASIC_VEIN("Minerals Basic", null, 0, Gypsum, Calcite), ДОБЫЧА С ПОРОДЫ
	
	ALMANDINE_VEIN("Almandine", null, 1, Almandine, Pyrope, Sapphire, GreenSapphire),
	APATITE_VEIN("Apatite", null, 1, Apatite, Apatite, Phosphorus, Pyrochlore),
	BENTONITE_VEIN("Bentonite", null, 1, Bentonite, Magnesite, Olivine, Glauconite),
	BERYLLIUM_VEIN("Beryllium", null, 1, Beryllium, Beryllium, Emerald, Thorium),
	LIMONITE_VEIN("Limonite", null, 1, BrownLimonite, YellowLimonite, BandedIron, Malachite),
	CHALCOPYRITE_VEIN("Chalcopyrite", null, 1, Chalcopyrite, Iron, Pyrite, Copper),
	COAL_VEIN("Coal", null, 1, Coal, Lignite, Coal, Lignite),
	GALENA_VEIN("Galena", null, 1, Galena, Galena, Silver, Lead),
	GARNIERITE_VEIN("Garnierite", null, 1, Garnierite, Nickel, Cobaltite, Pentlandite),
	GRAPHITE_VEIN("Graphite", null, 1, Graphite, Graphite, Diamond, Coal),
	GROSSULAR_VEIN("Grossular", null, 1, Grossular, Spessartine, Pyrolusite, Tantalite),
	LAZURITE_VEIN("Lazurite", null, 1, Lazurite, Sodalite, Lapis, Calcite),
	MAGNETITE_VEIN("Magnetite", null, 1, Magnetite, Iron, VanadiumMagnetite, Gold),
	REDSTONE_VEIN("Redstone", null, 1, Redstone, Redstone, Ruby, Cinnabar),
	ROCK_SALT_VEIN("Rock Salt", null, 1, RockSalt, Salt, Lepidolite, Spodumene),
	SOAPSTONE_VEIN("Soapstone", null, 1, Soapstone, Talc, Glauconite, Pentlandite),
	TETRAHEDRITE_VEIN("Tetrahedrite", null, 1, Tetrahedrite, Tetrahedrite, Copper, Stibnite),
	TIN_VEIN("Tin", null, 1, Tin, Tin, Cassiterite, Tin),
	WULFENITE_VEIN("Wulfenite", null, 1, Wulfenite, Molybdenite, Molybdenum, Powellite),
	OILSANDS_VEIN("Oilsands", null, 1, is(dust, Oilsands), is(crushed, Sulfur), is(crushed, Sulfur), is(crushed, Sulfur)),
	BAUXITE_VEIN("Bauxite", null, 1, Bauxite, Aluminium, Ilmenite, Ilmenite),
	
	
	NONE("Empty", null, -1, Empty);
	
	private final BiomeGenBase mBiome;
	private final List<ItemStack> mOre;
	private final String mName;
	private final int mTier;
	private final short[] mChance;
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, Materials... materials) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{90, 60, 60, 30};
				break;
			default:
				mChance = new short[materials.length];
				Arrays.fill(this.mChance, (short) 100);
		}
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, ItemStack... stacks) {
		this.mBiome = biomes;
		this.mName  = name + " Vein";
		this.mTier  = tier;
		this.mOre   = new ArrayList<>();
		switch (tier) {
			case 1:
			case 2:
			case 3:
				this.mChance = new short[]{90, 60, 60, 30};
				break;
			default:
				mChance = new short[stacks.length];
				Arrays.fill(this.mChance, (short) 100);
		}
		this.mOre.addAll(Arrays.asList(stacks));
	}
	
	BiomesOreGenerator(String name, BiomeGenBase biomes, int tier, short[] chance, Materials... materials) {
		this.mBiome  = biomes;
		this.mName   = name + " Vein";
		this.mTier   = tier;
		this.mChance = chance;
		this.mOre    = new ArrayList<>();
		Stream.of(materials).forEach(material -> this.mOre.add(GT_OreDictUnificator.get(crushed, material, 1)));
	}
	
	private static ItemStack is(OrePrefixes prefixes, Materials materials) {
		return GT_OreDictUnificator.get(prefixes, materials, 1);
	}
	
	public static BiomesOreGenerator getFromName(String name) {
		for (BiomesOreGenerator b : BiomesOreGenerator.values()) {
			if (b.name().equals(name)) {
				return b;
			}
		}
		return NONE;
	}
	
	public static BiomesOreGenerator generatedOres(World w, int x, int z, int tier) {
		Chunk chunk = w.getChunkFromBlockCoords(x, z);
		ChunkPosition chunkPosition = chunk.getChunkCoordIntPair().func_151349_a(0);
		int xx = chunkPosition.chunkPosX - 8;
		int zz = chunkPosition.chunkPosZ - 8;
		int dim = w.provider.dimensionId;
		startGenerateOres(w, xx, zz);
		int[] m = {dim, xx, zz, tier};
		for (int[] i : Impact_API.sOreInChunk.keySet()) {
			if (equalsIntArray(i, m)) {
				return getFromName(Impact_API.sOreInChunk.get(i));
			}
		}
		return NONE;
	}
	
	public static void startGenerateOres(World w, int x, int z) {
		generate(w, x, z);
	}
	
	private static void generate(World w, int x, int z) {
		BiomeGenBase biomes = w.getBiomeGenForCoords(x, z);
		int dim = w.provider.dimensionId;
		for (BiomesOreGenerator biomesOreGenerator : BiomesOreGenerator.values()) {
			int[] m = {dim, x, z, biomesOreGenerator.mTier};
			for (int[] i : Impact_API.sOreInChunk.keySet()) {
				if (equalsIntArray(i, m)) {
					return;
				}
			}
			if (biomesOreGenerator != NONE && biomes.biomeName.equals(biomesOreGenerator.mBiome.biomeName)) {
				Impact_API.sOreInChunk.put(m, biomesOreGenerator.name());
			}
		}
	}
}