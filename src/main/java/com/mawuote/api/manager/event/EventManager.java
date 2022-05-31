package com.mawuote.api.manager.event;

import net.minecraft.client.*;
import com.mawuote.api.manager.command.*;
import com.google.common.collect.*;
import net.minecraftforge.common.*;
import net.minecraftforge.client.event.*;
import java.awt.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.mawuote.api.manager.event.impl.network.*;
import java.util.*;
import net.minecraft.entity.player.*;
import java.net.*;
import org.apache.commons.io.*;
import org.json.simple.*;
import java.io.*;
import org.json.simple.parser.*;
import net.minecraft.entity.*;
import net.minecraft.util.math.*;

public class EventManager
{
    private static final Minecraft mc;
    private int colorRGBEffectRed;
    private int colorRGBEffectGreen;
    private int colorRGBEffectBlue;
    CommandManager commandManager;
    private final Map<String, String> uuidNameCache;
    
    public EventManager() {
        this.commandManager = new CommandManager();
        this.uuidNameCache = (Map<String, String>)Maps.newConcurrentMap();
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    @SubscribeEvent
    public void onWorldRender(final RenderWorldLastEvent event) {
        if (event.isCanceled()) {
            return;
        }
        final float[] tick_color = { System.currentTimeMillis() % 11520L / 11520.0f };
        final int colorInterpolated = Color.HSBtoRGB(tick_color[0], 1.0f, 1.0f);
        this.colorRGBEffectRed = (colorInterpolated >> 16 & 0xFF);
        this.colorRGBEffectGreen = (colorInterpolated >> 8 & 0xFF);
        this.colorRGBEffectBlue = (colorInterpolated & 0xFF);
    }
    
    @SubscribeEvent
    public void onReceive(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketPlayerListItem) {
            final SPacketPlayerListItem packet = (SPacketPlayerListItem)event.getPacket();
            if (packet.func_179768_b() == SPacketPlayerListItem.Action.ADD_PLAYER) {
                for (final SPacketPlayerListItem.AddPlayerData playerData : packet.func_179767_a()) {
                    if (playerData.func_179962_a().getId() != EventManager.mc.field_71449_j.func_148256_e().getId()) {
                        new Thread(() -> {
                            final UUID id = playerData.func_179962_a().getId();
                            final String name = this.resolveName(playerData.func_179962_a().getId().toString());
                            if (name != null && EventManager.mc.field_71439_g != null && EventManager.mc.field_71439_g.field_70173_aa >= 1000) {
                                MinecraftForge.EVENT_BUS.post((Event)new EventPlayerJoin(name, id));
                            }
                            return;
                        }).start();
                    }
                }
            }
            if (packet.func_179768_b() == SPacketPlayerListItem.Action.REMOVE_PLAYER) {
                for (final SPacketPlayerListItem.AddPlayerData playerData : packet.func_179767_a()) {
                    if (playerData.func_179962_a().getId() != EventManager.mc.field_71449_j.func_148256_e().getId()) {
                        new Thread(() -> {
                            final UUID id2 = playerData.func_179962_a().getId();
                            final EntityPlayer entity = EventManager.mc.field_71441_e.func_152378_a(id2);
                            final String name2 = this.resolveName(playerData.func_179962_a().getId().toString());
                            if (name2 != null && EventManager.mc.field_71439_g != null && EventManager.mc.field_71439_g.field_70173_aa >= 1000) {
                                MinecraftForge.EVENT_BUS.post((Event)new EventPlayerLeave(name2, id2, entity));
                            }
                        }).start();
                    }
                }
            }
        }
    }
    
    public String resolveName(String uuid) {
        uuid = uuid.replace("-", "");
        if (this.uuidNameCache.containsKey(uuid)) {
            return this.uuidNameCache.get(uuid);
        }
        final String url = "https://api.mojang.com/user/profiles/" + uuid + "/names";
        try {
            final String nameJson = IOUtils.toString(new URL(url));
            if (nameJson != null && nameJson.length() > 0) {
                final JSONArray jsonArray = (JSONArray)JSONValue.parseWithException(nameJson);
                if (jsonArray != null) {
                    final JSONObject latestName = jsonArray.get(jsonArray.size() - 1);
                    if (latestName != null) {
                        return latestName.get("name").toString();
                    }
                }
            }
        }
        catch (final IOException | ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public int[] getRGB() {
        return new int[] { this.colorRGBEffectRed, this.colorRGBEffectGreen, this.colorRGBEffectBlue };
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double ticks) {
        return getInterpolatedAmount(entity, ticks, ticks, ticks);
    }
    
    public static Vec3d getInterpolatedAmount(final Entity entity, final double x, final double y, final double z) {
        return new Vec3d((entity.field_70165_t - entity.field_70142_S) * x, (entity.field_70163_u - entity.field_70137_T) * y, (entity.field_70161_v - entity.field_70136_U) * z);
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
