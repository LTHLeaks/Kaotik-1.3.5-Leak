package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;

public class EventRender2D extends Event
{
    private float partialTicks;
    
    public EventRender2D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
