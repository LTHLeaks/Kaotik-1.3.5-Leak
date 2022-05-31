package com.mawuote.api.manager.event.impl.player;

import com.mawuote.api.manager.event.*;

public class EventKey extends Event
{
    public boolean info;
    public boolean pressed;
    
    public EventKey(final boolean info, final boolean pressed) {
        this.info = info;
        this.pressed = pressed;
    }
}
