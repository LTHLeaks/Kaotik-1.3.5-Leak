package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ValueBind extends Value
{
    private int value;
    
    public ValueBind(final String name, final String tag, final String description, final int value) {
        super(name, tag, description);
        this.value = value;
    }
    
    public int getValue() {
        return this.value;
    }
    
    public void setValue(final int value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
}
