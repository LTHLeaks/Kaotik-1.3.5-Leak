package com.mawuote.api.manager.event.impl.client;

import com.mawuote.api.manager.event.*;
import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.value.*;

public class EventClient extends Event
{
    private Module module;
    private Value setting;
    private int stage;
    
    public EventClient(final Value setting) {
        this.setting = setting;
    }
    
    public EventClient(final int stage) {
        this.stage = stage;
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public Value getSetting() {
        return this.setting;
    }
}
