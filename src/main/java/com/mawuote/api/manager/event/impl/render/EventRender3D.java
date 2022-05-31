package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;

public class EventRender3D extends Event
{
    private float partialTicks;
    
    public EventRender3D(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public void setPartialTicks(final float partialTicks) {
        this.partialTicks = partialTicks;
    }
    
    public float getPartialTicks() {
        return this.partialTicks;
    }
}
