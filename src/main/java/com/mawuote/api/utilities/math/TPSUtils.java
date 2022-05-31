package com.mawuote.api.utilities.math;

import java.util.*;
import net.minecraftforge.common.*;
import net.minecraft.util.math.*;
import com.mawuote.api.manager.event.impl.network.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class TPSUtils
{
    private static final float[] tickRates;
    private int nextIndex;
    private long timeLastTimeUpdate;
    
    public TPSUtils() {
        this.nextIndex = 0;
        this.timeLastTimeUpdate = -1L;
        Arrays.fill(TPSUtils.tickRates, 0.0f);
        MinecraftForge.EVENT_BUS.register((Object)this);
    }
    
    public static float getTickRate() {
        float numTicks = 0.0f;
        float sumTickRates = 0.0f;
        for (final float tickRate : TPSUtils.tickRates) {
            if (tickRate > 0.0f) {
                sumTickRates += tickRate;
                ++numTicks;
            }
        }
        return MathHelper.func_76131_a(sumTickRates / numTicks, 0.0f, 20.0f);
    }
    
    public static float getTpsFactor() {
        final float TPS = getTickRate();
        return 20.0f / TPS;
    }
    
    private void onTimeUpdate() {
        if (this.timeLastTimeUpdate != -1L) {
            final float timeElapsed = (System.currentTimeMillis() - this.timeLastTimeUpdate) / 1000.0f;
            TPSUtils.tickRates[this.nextIndex % TPSUtils.tickRates.length] = MathHelper.func_76131_a(20.0f / timeElapsed, 0.0f, 20.0f);
            ++this.nextIndex;
        }
        this.timeLastTimeUpdate = System.currentTimeMillis();
    }
    
    @SubscribeEvent
    public void onUpdate(final EventPacket.Receive event) {
        if (event.getPacket() instanceof SPacketTimeUpdate) {
            this.onTimeUpdate();
        }
    }
    
    static {
        tickRates = new float[20];
    }
}
