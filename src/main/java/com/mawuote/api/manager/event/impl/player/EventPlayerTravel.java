package com.mawuote.api.manager.event.impl.player;

import com.mawuote.api.manager.event.*;

public class EventPlayerTravel extends Event
{
    public float strafe;
    public float vertical;
    public float forward;
    
    public EventPlayerTravel(final float strafe, final float vertical, final float forward) {
        this.strafe = strafe;
        this.vertical = vertical;
        this.forward = forward;
    }
}
