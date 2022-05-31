package com.mawuote.api.manager.event.impl.network;

import com.mawuote.api.manager.event.*;
import java.util.*;
import net.minecraft.entity.player.*;

public class EventPlayerLeave extends Event
{
    private final String name;
    private final UUID uuid;
    private final EntityPlayer entity;
    
    public EventPlayerLeave(final String n, final UUID id, final EntityPlayer ent) {
        this.name = n;
        this.uuid = id;
        this.entity = ent;
    }
    
    public String getName() {
        return this.name;
    }
    
    public EntityPlayer getEntity() {
        return this.entity;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
