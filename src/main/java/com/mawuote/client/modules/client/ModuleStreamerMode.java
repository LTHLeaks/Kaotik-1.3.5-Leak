package com.mawuote.client.modules.client;

import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraft.network.play.server.*;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.eventhandler.*;
import net.minecraft.entity.player.*;
import com.mawuote.*;
import com.mawuote.api.manager.misc.*;
import java.util.*;
import net.minecraftforge.client.event.*;
import net.minecraftforge.fml.client.*;
import net.minecraft.client.network.*;
import net.minecraft.scoreboard.*;

public class ModuleStreamerMode extends Module
{
    public static ValueBoolean hideYou;
    public static ValueString yourName;
    public static ValueBoolean hideName;
    public static ValueString otherName;
    public static ValueBoolean hideF3;
    
    public ModuleStreamerMode() {
        super("StreamerMode", "Streamer Mode", "", ModuleCategory.CLIENT);
    }
    
    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketChat) {
            final SPacketChat packet = (SPacketChat)event.getPacket();
            if (packet.func_192590_c() != ChatType.GAME_INFO && this.getChatNames(packet.func_148915_c().func_150254_d(), packet.func_148915_c().func_150260_c())) {
                event.setCancelled(true);
            }
        }
    }
    
    private boolean getChatNames(final String message, final String unformatted) {
        String out = message;
        if (ModuleStreamerMode.hideName.getValue()) {
            if (ModuleStreamerMode.mc.field_71439_g == null) {
                return false;
            }
            for (final Object o : ModuleStreamerMode.mc.field_71441_e.field_73010_i) {
                if (o instanceof EntityPlayer && o != ModuleStreamerMode.mc.field_71439_g) {
                    final EntityPlayer ent = (EntityPlayer)o;
                    if (!Kaotik.FRIEND_MANAGER.isFriend(ent.func_70005_c_())) {
                        if (!out.contains(ent.func_70005_c_())) {
                            continue;
                        }
                        out = out.replaceAll(ent.func_70005_c_(), ModuleStreamerMode.otherName.getValue());
                    }
                    else {
                        if (!Kaotik.FRIEND_MANAGER.isFriend(ent.func_70005_c_()) || !out.contains(ent.func_70005_c_())) {
                            continue;
                        }
                        out = out.replaceAll(ent.func_70005_c_(), "Friend");
                    }
                }
            }
        }
        if (ModuleStreamerMode.hideYou.getValue()) {
            if (ModuleStreamerMode.mc.field_71439_g == null) {
                return false;
            }
            out = out.replace(ModuleStreamerMode.mc.field_71439_g.func_70005_c_(), ModuleStreamerMode.yourName.getValue());
        }
        ChatManager.sendRawMessage(out);
        return true;
    }
    
    @SubscribeEvent
    public void renderOverlayEvent(final RenderGameOverlayEvent.Text event) {
        if (FMLClientHandler.instance().getClient().field_71439_g.field_71075_bZ.field_75098_d) {
            return;
        }
        if (!ModuleStreamerMode.hideF3.getValue()) {
            return;
        }
        final Iterator<String> it = event.getLeft().listIterator();
        while (it.hasNext()) {
            final String value = it.next();
            if ((value != null && value.startsWith("XYZ:")) || value.startsWith("Looking at:") || value.startsWith("Block:") || value.startsWith("Facing:")) {
                it.remove();
            }
        }
    }
    
    public static String getPlayerName(final NetworkPlayerInfo networkPlayerInfoIn) {
        String dname = (networkPlayerInfoIn.func_178854_k() != null) ? networkPlayerInfoIn.func_178854_k().func_150254_d() : ScorePlayerTeam.func_96667_a((Team)networkPlayerInfoIn.func_178850_i(), networkPlayerInfoIn.func_178845_a().getName());
        dname = dname.replace(ModuleStreamerMode.mc.field_71439_g.func_70005_c_(), ModuleStreamerMode.yourName.getValue());
        return dname;
    }
    
    static {
        ModuleStreamerMode.hideYou = new ValueBoolean("HideIGN", "HideIGN", "", false);
        ModuleStreamerMode.yourName = new ValueString("YourName", "YourName", "", "You");
        ModuleStreamerMode.hideName = new ValueBoolean("HideOthers", "HideOthers", "", false);
        ModuleStreamerMode.otherName = new ValueString("OthersName", "OthersName", "", "Enemy");
        ModuleStreamerMode.hideF3 = new ValueBoolean("HideF3", "HideF3", "", false);
    }
}
