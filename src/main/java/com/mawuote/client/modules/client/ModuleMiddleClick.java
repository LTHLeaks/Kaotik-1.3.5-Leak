package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import net.minecraft.init.*;
import com.mawuote.api.utilities.entity.*;
import org.lwjgl.input.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.math.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import com.mawuote.*;
import com.mojang.realmsclient.gui.*;
import com.mawuote.api.manager.misc.*;
import net.minecraft.entity.*;

public class ModuleMiddleClick extends Module
{
    ValueEnum mode;
    int oldSlot;
    private int delay;
    
    public ModuleMiddleClick() {
        super("MiddleClick", "Middle Click", "", ModuleCategory.CLIENT);
        this.mode = new ValueEnum("Mode", "Mode", "", modes.XP);
        this.oldSlot = -1;
        this.delay = 0;
    }
    
    @Override
    public void onMotionUpdate() {
        this.oldSlot = ModuleMiddleClick.mc.field_71439_g.field_71071_by.field_70461_c;
        final int pearlSlot = InventoryUtils.getHotbarItemSlot(Items.field_151079_bi);
        if (this.mode.getValue().equals(modes.XP)) {
            if (Mouse.isButtonDown(2) && this.hotbarXP() != -1) {
                ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.hotbarXP()));
                ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.oldSlot));
            }
        }
        else if (this.mode.getValue().equals(modes.Pearl) && Mouse.isButtonDown(2) && pearlSlot != -1) {
            ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(pearlSlot));
            ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            ModuleMiddleClick.mc.field_71439_g.field_71174_a.func_147297_a((Packet)new CPacketHeldItemChange(this.oldSlot));
        }
    }
    
    @Override
    public void onUpdate() {
        if (this.mode.getValue().equals(modes.MCF)) {
            ++this.delay;
            final RayTraceResult object = ModuleMiddleClick.mc.field_71476_x;
            if (object == null) {
                return;
            }
            if (object.field_72313_a == RayTraceResult.Type.ENTITY) {
                final Entity entity = object.field_72308_g;
                if (entity instanceof EntityPlayer && !(entity instanceof EntityArmorStand) && !ModuleMiddleClick.mc.field_71439_g.field_70128_L && ModuleMiddleClick.mc.field_71439_g.func_70685_l(entity)) {
                    final EntityPlayer player = (EntityPlayer)entity;
                    final String ID = entity.func_70005_c_();
                    if (Mouse.isButtonDown(2) && ModuleMiddleClick.mc.field_71462_r == null && !Kaotik.FRIEND_MANAGER.isFriend(ID) && this.delay > 10) {
                        Kaotik.FRIEND_MANAGER.addFriend(ID);
                        ChatManager.printChatNotifyClient("" + ChatFormatting.GREEN + ChatFormatting.BOLD + "Added " + ChatFormatting.RESET + ChatFormatting.WHITE + ID + " as friend");
                        this.delay = 0;
                    }
                    if (Mouse.isButtonDown(2) && ModuleMiddleClick.mc.field_71462_r == null && Kaotik.FRIEND_MANAGER.isFriend(ID) && this.delay > 10) {
                        Kaotik.FRIEND_MANAGER.removeFriend(ID);
                        ChatManager.printChatNotifyClient("" + ChatFormatting.RED + ChatFormatting.BOLD + "Removed " + ChatFormatting.RESET + ChatFormatting.WHITE + ID + " as friend");
                        this.delay = 0;
                    }
                }
            }
        }
    }
    
    private int hotbarXP() {
        for (int i = 0; i < 9; ++i) {
            if (ModuleMiddleClick.mc.field_71439_g.field_71071_by.func_70301_a(i).func_77973_b() == Items.field_151062_by) {
                return i;
            }
        }
        return -1;
    }
    
    public enum modes
    {
        MCF, 
        XP, 
        Pearl;
    }
}
