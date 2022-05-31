package com.mawuote.client.modules.client;

import com.mawuote.api.manager.module.*;
import com.mawuote.api.manager.misc.*;

public class ModuleDiscordPresence extends Module
{
    public ModuleDiscordPresence() {
        super("Discord Presence", "Discord Presence", "Makes your Discord profile have a Rich Presence.", ModuleCategory.CLIENT);
    }
    
    @Override
    public void onEnable() {
        DiscordPresence.startRPC();
    }
    
    @Override
    public void onDisable() {
        DiscordPresence.stopRPC();
    }
}
