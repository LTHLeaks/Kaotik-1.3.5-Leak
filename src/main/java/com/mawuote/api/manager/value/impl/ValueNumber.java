package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class ValueNumber extends Value
{
    public static final int INTEGER = 1;
    public static final int DOUBLE = 2;
    public static final int FLOAT = 3;
    private Number value;
    private Number minimum;
    private Number maximum;
    
    public ValueNumber(final String name, final String tag, final String description, final Number value, final Number minimum, final Number maximum) {
        super(name, tag, description);
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;
    }
    
    public Number getValue() {
        return this.value;
    }
    
    public void setValue(final Number value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
    
    public Number getMinimum() {
        return this.minimum;
    }
    
    public Number getMaximum() {
        return this.maximum;
    }
    
    public int getType() {
        if (this.value.getClass() == Integer.class) {
            return 1;
        }
        if (this.value.getClass() == Double.class) {
            return 2;
        }
        if (this.value.getClass() == Float.class) {
            return 3;
        }
        return -1;
    }
}
