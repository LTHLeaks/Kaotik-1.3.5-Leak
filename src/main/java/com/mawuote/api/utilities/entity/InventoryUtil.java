package com.mawuote.api.utilities.entity;

import com.mawuote.api.utilities.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.item.*;
import net.minecraft.inventory.*;
import net.minecraft.entity.player.*;

public class InventoryUtil implements IMinecraft
{
    public static void switchToHotbarSlot(final int slot, final boolean silent) {
        if (InventoryUtil.mc.field_71439_g.field_71071_by.field_70461_c == slot || slot < 0) {
            return;
        }
        if (silent) {
            InventoryUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.field_71442_b.func_78765_e();
        }
        else {
            InventoryUtil.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(slot));
            InventoryUtil.mc.field_71439_g.field_71071_by.field_70461_c = slot;
            InventoryUtil.mc.field_71442_b.func_78765_e();
        }
    }
    
    public static int findHotbarBlock(final Class clazz) {
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = InventoryUtil.mc.field_71439_g.field_71071_by.func_70301_a(i);
            if (stack != ItemStack.field_190927_a) {
                if (clazz.isInstance(stack.func_77973_b())) {
                    return i;
                }
                if (stack.func_77973_b() instanceof ItemBlock) {
                    final Block block;
                    if (clazz.isInstance(block = ((ItemBlock)stack.func_77973_b()).func_179223_d())) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }
    
    public static int findObsidianSlot(final boolean offHandActived, final boolean activeBefore) {
        int slot = -1;
        final List<ItemStack> mainInventory = (List<ItemStack>)InventoryUtil.mc.field_71439_g.field_71071_by.field_70462_a;
        if (offHandActived) {
            return 9;
        }
        for (int i = 0; i < 9; ++i) {
            final ItemStack stack = mainInventory.get(i);
            if (stack != ItemStack.field_190927_a) {
                if (stack.func_77973_b() instanceof ItemBlock) {
                    final Block block = ((ItemBlock)stack.func_77973_b()).func_179223_d();
                    if (block instanceof BlockObsidian) {
                        slot = i;
                        break;
                    }
                }
            }
        }
        return slot;
    }
    
    public static int getItemFromHotbar(final Item item) {
        int slot = -1;
        for (int i = 8; i >= 0; --i) {
            if (InventoryUtil.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == item) {
                slot = i;
                break;
            }
        }
        return slot;
    }
    
    public static int getItemSlot(final Item input) {
        if (input == InventoryUtil.mc.field_71439_g.func_184592_cb().func_77973_b()) {
            return -1;
        }
        for (int i = 36; i >= 0; --i) {
            final Item item = InventoryUtil.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b();
            if (item == input) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return -1;
    }
    
    public static void swapItemsOffhand(final int slot) {
        if (slot == -1) {
            return;
        }
        InventoryUtil.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.field_71439_g);
        InventoryUtil.mc.field_71442_b.func_187098_a(0, 45, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.field_71439_g);
        InventoryUtil.mc.field_71442_b.func_187098_a(0, slot, 0, ClickType.PICKUP, (EntityPlayer)InventoryUtil.mc.field_71439_g);
        InventoryUtil.mc.field_71442_b.func_78765_e();
    }
}
