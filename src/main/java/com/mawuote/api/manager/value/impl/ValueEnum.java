package com.mawuote.api.manager.value.impl;

import com.mawuote.api.manager.value.*;
import net.minecraftforge.common.*;
import com.mawuote.api.manager.event.impl.client.*;
import net.minecraftforge.fml.common.eventhandler.*;
import java.util.*;

public class ValueEnum extends Value
{
    private Enum value;
    
    public ValueEnum(final String name, final String tag, final String description, final Enum value) {
        super(name, tag, description);
        this.value = value;
    }
    
    public Enum getValue() {
        return this.value;
    }
    
    public void setValue(final Enum value) {
        MinecraftForge.EVENT_BUS.post((Event)new EventClient(this));
        this.value = value;
    }
    
    public Enum getEnumByName(final String name) {
        Enum enumRequested = null;
        for (final Enum enums : this.getValues()) {
            if (enums.name().equals(name)) {
                enumRequested = enums;
                break;
            }
        }
        return enumRequested;
    }
    
    public ArrayList<Enum> getValues() {
        final ArrayList<Enum> enumList = new ArrayList<Enum>();
        for (final Enum enums : (Enum[])this.value.getClass().getEnumConstants()) {
            enumList.add(enums);
        }
        return enumList;
    }
}
