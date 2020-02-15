package com.gwppcore.item;

import com.gwppcore.creativetab.ModTabList;
import com.gwppcore.item.Circuit_Programmer.CircuitProgrammer;
import com.gwppcore.lib.Refstrings;
import com.gwppcore.gwppcore;
import eu.usrv.yamcore.items.ModItemManager;
import eu.usrv.yamcore.items.ModSimpleBaseItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public enum ItemList {

    Coin(new ModSimpleBaseItem("Coin",ModTabList.GWppTab)),


	// Do not delete this
    EndOfList(null);
    // ################################################################################


    public ModSimpleBaseItem Item;

    ItemList(ModSimpleBaseItem pItem)
    {
        Item = pItem;
        if (Item != null) {
            Item.setModIDName(Refstrings.MODID);
        }
    }

    public static boolean AddToItemManager(ModItemManager pItemManager)
    {
        boolean tResult = true;
        for (ItemList il : ItemList.values())
        {
            if (il.Item != null) {
                if (!pItemManager.AddItemToManagedRegistry(il.Item)) {
                    gwppcore.Logger.error(String.format("Item [%s] failed to register", il.toString()));
                    tResult = false;
                }
            }
        }

        return tResult;
    }

    public ItemStack getIS()
    {
        return new ItemStack(Item.getConstructedItem(), 1);
    }
    
    public ItemStack getIS(int amount)
    {
        return new ItemStack(Item.getConstructedItem(), amount);
    }

    public static final net.minecraft.item.Item CIRCUIT_PROGRAMMER = new CircuitProgrammer();
}

