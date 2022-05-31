package com.mawuote.api.utilities.entity;

import com.mawuote.api.utilities.*;
import net.minecraft.init.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import net.minecraft.item.*;
import java.util.concurrent.atomic.*;
import net.minecraft.client.gui.inventory.*;
import java.util.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class InventoryUtils implements IMinecraft
{
    public static int getCombatBlock(final String input) {
        final int obsidianSlot = findBlock(Blocks.field_150343_Z, 0, 9);
        final int chestSlot = findBlock(Blocks.field_150477_bB, 0, 9);
        if (obsidianSlot == -1 && chestSlot == -1) {
            return -1;
        }
        if (obsidianSlot != -1 && chestSlot == -1) {
            return obsidianSlot;
        }
        if (obsidianSlot == -1) {
            return chestSlot;
        }
        if (input.equals("Obsidian")) {
            return obsidianSlot;
        }
        return chestSlot;
    }
    
    public static void switchSlot(final int slot, final boolean silent) {
        if (silent) {
            InventoryUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
        }
        else {
            InventoryUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
            InventoryUtils.mc.field_71439_g.field_71071_by.field_70461_c = slot;
        }
    }
    
    public static int findItem(final Item item, final int minimum, final int maximum) {
        for (int i = minimum; i <= maximum; ++i) {
            final ItemStack stack = InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() == item) {
                return i;
            }
        }
        return -1;
    }
    
    public static int findBlock(final Block block, final int minimum, final int maximum) {
        for (int i = minimum; i <= maximum; ++i) {
            final ItemStack stack = InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack.func_77973_b() instanceof ItemBlock) {
                final ItemBlock item = (ItemBlock)stack.func_77973_b();
                if (item.func_179223_d() == block) {
                    return i;
                }
            }
        }
        return -1;
    }
    
    public static void switchToSlot(final Class<? extends Item> clazz) {
        if (InventoryUtils.mc.field_71439_g.func_184614_ca().func_77973_b().getClass().isAssignableFrom(clazz)) {
            return;
        }
        final int slot = getHotbarItemSlot(clazz);
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.field_71439_g.field_71071_by.field_70461_c = slot;
    }
    
    public static void switchToSlot(final Item item) {
        if (InventoryUtils.mc.field_71439_g.func_184614_ca().func_77973_b() == item) {
            return;
        }
        final int slot = getHotbarItemSlot(item.getClass());
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.field_71439_g.field_71071_by.field_70461_c = slot;
    }
    
    public static void switchToPacketSlot(final Item item) {
        if (InventoryUtils.mc.field_71439_g.func_184614_ca().func_77973_b() == item) {
            return;
        }
        final int slot = getHotbarItemSlot(item.getClass());
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
    }
    
    public static void switchToPacketSlot(final Class<? extends Item> clazz) {
        if (InventoryUtils.mc.field_71439_g.func_184614_ca().func_77973_b().getClass().isAssignableFrom(clazz)) {
            return;
        }
        final int slot = getHotbarItemSlot(clazz);
        if (slot == -1) {
            return;
        }
        InventoryUtils.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
    }
    
    public static int findItemInventorySlot(final Item item, final boolean offHand) {
        final AtomicInteger slot = new AtomicInteger();
        slot.set(-1);
        for (final Map.Entry<Integer, ItemStack> entry : getInventoryAndHotbarSlots().entrySet()) {
            if (entry.getValue().func_77973_b() == item) {
                if (entry.getKey() == 45 && !offHand) {
                    continue;
                }
                slot.set(entry.getKey());
                return slot.get();
            }
        }
        return slot.get();
    }
    
    public static Map<Integer, ItemStack> getInventoryAndHotbarSlots() {
        if (InventoryUtils.mc.field_71462_r instanceof GuiCrafting) {
            return getOtherSlot(10, 45);
        }
        return getInventorySlots(9, 44);
    }
    
    private static Map<Integer, ItemStack> getInventorySlots(final int currentI, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        for (int current = currentI; current <= last; ++current) {
            fullInventorySlots.put(current, (ItemStack)InventoryUtils.mc.field_71439_g.field_71069_bz.func_75138_a().get(current));
        }
        return fullInventorySlots;
    }
    
    private static Map<Integer, ItemStack> getOtherSlot(final int currentI, final int last) {
        final HashMap<Integer, ItemStack> fullInventorySlots = new HashMap<Integer, ItemStack>();
        for (int current = currentI; current <= last; ++current) {
            fullInventorySlots.put(current, (ItemStack)InventoryUtils.mc.field_71439_g.field_71070_bA.func_75138_a().get(current));
        }
        return fullInventorySlots;
    }
    
    public static void offhandItem(final Item item) {
        final int slot = findItemInventorySlot(item, false);
        if (slot != -1) {
            InventoryUtils.mc.field_71442_b.func_187098_a(InventoryUtils.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.field_71439_g);
            InventoryUtils.mc.field_71442_b.func_187098_a(InventoryUtils.mc.field_71439_g.field_71069_bz.field_75152_c, 45, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.field_71439_g);
            InventoryUtils.mc.field_71442_b.func_187098_a(InventoryUtils.mc.field_71439_g.field_71069_bz.field_75152_c, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtils.mc.field_71439_g);
            InventoryUtils.mc.field_71442_b.func_78765_e();
        }
    }
    
    public static int getHotbarItemSlot(final Class<? extends Item> item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            if (InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b().getClass().isAssignableFrom(item)) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int getHotbarBlockSlot(final Block block) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item item = InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item instanceof ItemBlock && ((ItemBlock)item).func_179223_d().equals(block)) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int getHotbarItemSlot(final Item item) {
        int slot = -1;
        for (int i = 0; i < 9; ++i) {
            final Item selection = InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (selection.equals(item)) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    private static int getInventoryItemSlot(final Item item) {
        for (int i = 0; i < 36; ++i) {
            final Item cacheItem = InventoryUtils.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (cacheItem == item) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public boolean isHotbar(final int slot) {
        return slot == 0 || slot == 1 || slot == 2 || slot == 3 || slot == 4 || slot == 5 || slot == 6 || slot == 7 || slot == 8;
    }
    
    public enum Items
    {
        Obsidian, 
        Chest;
    }
    
    public enum SwitchModes
    {
        Normal, 
        Silent, 
        Strict;
    }
}
