package com.impact.mods.GregTech.GTregister;

import gregtech.api.enums.*;
import gregtech.api.interfaces.IMaterialHandler;

public class GT_Materials implements IMaterialHandler {

    /** 1 = Dusts of all kinds.
    *   2 = Dusts, Ingots, Plates, Rods/Sticks, Machine Components and other Metal specific things.
    *   4 = Dusts, Gems, Plates, Lenses (if transparent).
    *   8 = Dusts, Impure Dusts, crushed Ores, purified Ores, centrifuged Ores etc.
    *   16 = Cells
    *   32 = Plasma Cells
    *   64 = Tool Heads
    *   128 = Gears
    *   256 = Designates something as empty (only used for the Empty material)/

    /** MATERIALS> */
    public static Materials Trinium = new Materials( 7, TextureSet.SET_SHINY,128.0F,51200,8,1|2|8|64|128, 200,200,210,0,"Trinium","Trinium",0,0,7200,7200,true, false,4,1,1,Dyes.dyeLightGray).disableAutoGeneratedBlastFurnaceRecipes();
    public static Materials HastelloyC276 = new Materials( 15, TextureSet.SET_SHINY,128.0F,51200,8,2, 20,20,20,0,"HastelloyC276","HastelloyC276",0,0,10000,7200,false, false,4,1,1, Dyes.dyeRed).disableAutoGeneratedBlastFurnaceRecipes().setHasCorrespondingMoltenHot(true);


    /** FLUID */
    public static Materials SupercriticalSteam = new MaterialBuilder(11, TextureSet.SET_FLUID, "Supercritical Steam").addCell().addFluid().setRGB(32, 32, 32).setColor(Dyes.dyeGray).setFuelType(MaterialBuilder.NUKE).setFuelPower(16).constructMaterial();



    public GT_Materials() {
        Materials.add(this);
    }

    @Override
    public void onMaterialsInit() {
        /** This is just left here as an example of how to add new materials. **/


        /*
        int i = 0;
        for (int j = GregTech_API.sMaterialProperties.get("general", "AmountOfCustomMaterialSlots", 16); i < j; i++) {
            String aID = (i < 10 ? "0" : "") + i;
            new Materials(-1, TextureSet.SET_METALLIC, 1.0F, 0, 0, 0, 255, 255, 255, 0, "CustomMat" + aID, "CustomMat" + aID, 0, 0, 0, 0, false, false, 1, 1, 1, Dyes._NULL, "custom", true, aID);
        }
        */
    }

    @Override
    public void onComponentInit() {
        for(OrePrefixes ore:OrePrefixes.values()){

            //ore.enableComponent(SomeMaterial);
        }

        /** This is just left here as an example of how to add components. **/
        /*
        //Enabling specific components:
        OrePrefixes.spring.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.ingotQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDouble.enableComponent(Materials.Cobalt);
        OrePrefixes.plateTriple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuadruple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateQuintuple.enableComponent(Materials.Cobalt);
        OrePrefixes.plateDense.enableComponent(Materials.Cobalt);
        */
    }

    @Override
    public void onComponentIteration(Materials aMaterial) {
        /** This is just left here as an example of how to add components. **/
        /*
        //Enabling/Disabling components depending on the current Materials values:
        if ((aMaterial.mTypes & 0x40) != 0) {                     //This material can be made into tool heads
            OrePrefixes.plateQuadruple.mDisabledItems.remove(aMaterial);
        }
        */
    }
}