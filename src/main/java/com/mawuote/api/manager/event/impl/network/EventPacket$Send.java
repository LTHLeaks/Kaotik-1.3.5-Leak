package com.mawuote.api.manager.event.impl.network;

import com.mawuote.api.manager.event.*;
import net.minecraft.network.*;

public static class Send extends EventPacket
{
    public Send(final Stage stage, final Packet packet) {
        super(stage, packet);
    }
}
