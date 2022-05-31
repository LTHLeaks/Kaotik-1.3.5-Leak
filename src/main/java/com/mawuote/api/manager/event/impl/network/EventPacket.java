package com.mawuote.api.manager.event.impl.network;

import com.mawuote.api.manager.event.*;
import net.minecraft.network.*;

public class EventPacket extends Event
{
    private Packet packet;
    
    public EventPacket(final Stage stage, final Packet packet) {
        super(stage);
        this.packet = packet;
    }
    
    private void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public static class Send extends EventPacket
    {
        public Send(final Stage stage, final Packet packet) {
            super(stage, packet);
        }
    }
    
    public static class Receive extends EventPacket
    {
        public Receive(final Stage stage, final Packet packet) {
            super(stage, packet);
        }
    }
}
