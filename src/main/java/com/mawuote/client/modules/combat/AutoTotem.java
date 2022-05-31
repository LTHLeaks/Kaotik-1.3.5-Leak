package com.mawuote.client.modules.combat;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.mawuote.api.utilities.entity.*;

public class AutoTotem extends Module
{
    public static ValueNumber health;
    public static ValueBoolean swordGap;
    public static ValueEnum mode;
    
    public AutoTotem() {
        super("AutoTotem", "AutoTotem", "makes it so you dont die", ModuleCategory.PLAYER);
    }
    
    @Override
    public void onUpdate() {
        final float hp = AutoTotem.mc.field_71439_g.func_110143_aJ() + AutoTotem.mc.field_71439_g.func_110139_bj();
        if (hp > AutoTotem.health.getValue().floatValue() && AutoTotem.mc.field_71439_g.field_70143_R != 5.0f) {
            if (AutoTotem.swordGap.getValue() && AutoTotem.mc.field_71439_g.func_184614_ca().func_77973_b() instanceof ItemSword && AutoTotem.mc.field_71474_y.field_74313_G.func_151470_d()) {
                InventoryUtil.swapItemsOffhand(InventoryUtil.getItemSlot(Items.field_151153_ao));
            }
            else {
                if (hp <= AutoTotem.health.getValue().floatValue()) {
                    InventoryUtil.swapItemsOffhand(InventoryUtil.getItemSlot(Items.field_190929_cY));
                }
                if (AutoTotem.mode.getValue().equals(modes.Totem) && hp > AutoTotem.health.getValue().floatValue()) {
                    InventoryUtil.swapItemsOffhand(InventoryUtil.getItemSlot(Items.field_190929_cY));
                }
                else if (AutoTotem.mode.getValue().equals(modes.Gapple) && hp > AutoTotem.health.getValue().floatValue()) {
                    InventoryUtil.swapItemsOffhand(InventoryUtil.getItemSlot(Items.field_151153_ao));
                }
                else if (AutoTotem.mode.getValue().equals(modes.Crystal) && hp > AutoTotem.health.getValue().floatValue()) {
                    InventoryUtil.swapItemsOffhand(InventoryUtil.getItemSlot(Items.field_185158_cP));
                }
            }
        }
    }
    
    static {
        AutoTotem.health = new ValueNumber("Health", "Health", "health for swap", 13, 0, 36);
        AutoTotem.swordGap = new ValueBoolean("SwordGap", "SwordGap", "eating with sword", true);
        AutoTotem.mode = new ValueEnum("Mode", "Mode", "", modes.Crystal);
    }
    
    public enum modes
    {
        Totem, 
        Crystal, 
        Gapple;
    }
}
