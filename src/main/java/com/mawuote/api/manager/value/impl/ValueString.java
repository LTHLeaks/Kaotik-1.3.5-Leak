package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ValueString extends Value
{
    private String value;
    
    public ValueString(final String name, final String tag, final String description, final String value) {
        super(name, tag, description);
        this.value = value;
    }
    
    public String getValue() {
        return this.value;
    }
    
    public void setValue(final String value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
}
