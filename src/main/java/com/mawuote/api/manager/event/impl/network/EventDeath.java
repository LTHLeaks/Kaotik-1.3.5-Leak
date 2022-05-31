package com.mawuote.api.manager.event.impl.network;

import com.mawuote.api.manager.event.*;
import net.minecraft.entity.player.*;

public class EventDeath extends Event
{
    public EntityPlayer player;
    
    public EventDeath(final EntityPlayer player) {
        this.player = player;
    }
}
