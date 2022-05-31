package com.mawuote.api.manager.event.impl.render;

import com.mawuote.api.manager.event.*;
import net.minecraft.util.*;

public class EventHandSide extends Event
{
    private final EnumHandSide handSide;
    
    public EventHandSide(final EnumHandSide handSide) {
        this.handSide = handSide;
    }
    
    public EnumHandSide getHandSide() {
        return this.handSide;
    }
}
