package com.mawuote.api.manager.command;

import net.minecraft.client.*;
import java.util.*;

public abstract class Command
{
    protected static final Minecraft mc;
    private final String name;
    private final String description;
    private final String syntax;
    private final List<String> aliases;
    
    public Command(final String name, final String description, final String syntax, final String... aliases) {
        this.name = name;
        this.description = description;
        this.syntax = syntax;
        this.aliases = Arrays.asList(aliases);
    }
    
    public abstract void onCommand(final String[] p0);
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
    
    public List<String> getAliases() {
        return this.aliases;
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
