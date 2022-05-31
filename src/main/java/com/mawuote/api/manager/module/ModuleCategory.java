package com.mawuote.api.manager.module;

public enum ModuleCategory
{
    COMBAT("Combat"), 
    PLAYER("Player"), 
    MISC("Misc"), 
    MOVEMENT("Movement"), 
    RENDER("Render"), 
    CLIENT("Client"), 
    HUD("HUD");
    
    private final String name;
    
    private ModuleCategory(final String name) {
        this.name = name;
    }
    
    public String getName() {
        return this.name;
    }
}
