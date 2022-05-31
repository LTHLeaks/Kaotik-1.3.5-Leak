package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;

public class EventRenderPutColorMultiplier extends Event
{
    private float _opacity;
    
    public void setOpacity(final float opacity) {
        this._opacity = opacity;
    }
    
    public float getOpacity() {
        return this._opacity;
    }
}
