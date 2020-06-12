package com.impact.recipes;

import gregtech.api.enums.GT_Values;
import gregtech.api.enums.Materials;
import gregtech.api.enums.OrePrefixes;
import gregtech.api.util.GT_ModHandler;
import gregtech.api.util.GT_OreDictUnificator;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import static gregtech.api.util.GT_ModHandler.*;

public class AfterGregTechPostLoadRecipes implements Runnable {
    @Override
    public void run() {

        //removeRecipe(ItemStack aInput);

        //removeFurnaceSmelting(ItemStack aInput)
        removeFurnaceSmelting(GT_ModHandler.getModItem("TConstruct", "CraftedSoil", 1L, 1));
        removeFurnaceSmelting(new ItemStack(Items.clay_ball, 1, 0));

        //removeRecipeByOutput();
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoHelmet", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBodyarmor", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoLeggings", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("nanoBoots", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumHelmet", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBodyarmor", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumLeggings", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getIC2Item("quantumBoots", 1, GT_Values.W));
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "blockTelePad", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "blockTransceiver", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockController", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockChest", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockDrive", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockCraftingUnit", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockMolecularAssembler", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumRing", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockQuantumLinkChamber", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialPylon", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockSpatialIOPort", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockInterface", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "tile.BlockEnergyAcceptor", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 500), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 260), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 240), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 220), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 460), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 140), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 35), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 36), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 37), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 38), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 32), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 33), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 34), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiMaterial", 1L, 39), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.1k", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.4k", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.16k", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemBasicStorageCell.64k", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.2Cubed", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.16Cubed", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemSpatialStorageCell.128Cubed", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemViewCell", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("appliedenergistics2", "item.ItemMultiPart", 1L, 180), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "part.base", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 3), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 6), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 7), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 8), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 9), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.component", 1L, 10), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.casing", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.casing", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 3), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.fluid", 1L, 6), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("extracells", "storage.physical", 1L, 3), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "enderQuarry", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "endMarker", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("ExtraUtilities", "dark_portal", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockGenerator", 1L, 9), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("IC2", "blockKineticGenerator", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 3), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 6), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 7), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 8), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 9), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 10), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("compactkineticgenerators", "BlockCkg", 1L, 11), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "graviChestPlate", 1L, GT_Values.W), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("GraviSuite", "kpChestPlate", 1L, GT_Values.W), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockMachineManagerName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableBreakerName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableSignName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableClusterName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableClusterName", 1L, 8), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableOutputName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableInputName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableRelayName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableRelayName", 1L, 8), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableBUDName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableCamouflageName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableCamouflageName", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableCamouflageName", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableIntakeName", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("StevesFactoryManager", "BlockCableIntakeName", 1L, 8), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 15), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 1), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 2), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 3), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 4), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 5), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 11), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "logisticsSolidBlock", 1L, 12), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicTransport", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeItemsBasicLogistics", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeFluidBasic", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("LogisticsPipes", "item.PipeBlockRequestTable", 1L, 0), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "blockInventoryPanel", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "itemFunctionUpgrade", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "itemItemConduit", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "itemLiquidConduit", 1L, 0), true, false, false);
        removeRecipeByOutput(GT_ModHandler.getModItem("EnderIO", "itemYetaWrench", 1L, 0), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("StorageDrawers", "controller", 1L, 0), true, false, false);

        removeRecipeByOutput(GT_ModHandler.getModItem("minecraft", "crafting_table", 1L, 0), true, false, false);

        removeRecipeByOutput(GT_OreDictUnificator.get(OrePrefixes.dust, Materials.CokeCoal, 1), true, false, false);
    }
}
