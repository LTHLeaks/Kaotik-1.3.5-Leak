package com.mawuote.api.manager.event.impl.player;

import com.mawuote.api.manager.event.*;

public class EventMotionUpdate extends Event
{
    public int stage;
    
    public EventMotionUpdate(final int stage) {
        this.stage = stage;
    }
}
