package com.mawuote.api.manager.event.impl.network;

import com.mawuote.api.manager.event.*;
import java.util.*;

public class EventPlayerJoin extends Event
{
    private final String name;
    private final UUID uuid;
    
    public EventPlayerJoin(final String n, final UUID id) {
        this.name = n;
        this.uuid = id;
    }
    
    public String getName() {
        return this.name;
    }
    
    public UUID getUuid() {
        return this.uuid;
    }
}
