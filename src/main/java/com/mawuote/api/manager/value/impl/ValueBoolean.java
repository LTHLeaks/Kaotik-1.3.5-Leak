package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ValueBoolean extends Value
{
    private boolean value;
    
    public ValueBoolean(final String name, final String tag, final String description, final boolean value) {
        super(name, tag, description);
        this.value = value;
    }
    
    public boolean getValue() {
        return this.value;
    }
    
    public void setValue(final boolean value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
}
