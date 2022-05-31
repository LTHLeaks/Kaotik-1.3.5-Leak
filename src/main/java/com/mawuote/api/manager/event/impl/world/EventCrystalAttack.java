package com.mawuote.api.manager.event.impl.world;

import com.mawuote.api.manager.event.*;

public class EventCrystalAttack extends Event
{
    private final int entityId;
    
    public EventCrystalAttack(final int entityId) {
        this.entityId = entityId;
    }
    
    public int getEntityID() {
        return this.entityId;
    }
}
