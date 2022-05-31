package com.mawuote.api.manager.misc;

import club.minnced.discord.rpc.*;
import com.mawuote.*;

public class DiscordPresence
{
    private static String discordID;
    private static DiscordRichPresence discordRichPresence;
    private static DiscordRPC discordRPC;
    private static String clientVersion;
    
    public static void startRPC() {
        final DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        eventHandlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + var1 + ", var2: " + var2));
        DiscordPresence.discordRPC.Discord_Initialize(DiscordPresence.discordID, eventHandlers, true, null);
        DiscordPresence.discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.discordRichPresence.details = "Kaotik Leaked by SomeSadKid_";
        DiscordPresence.discordRichPresence.largeImageKey = "kaotik";
        DiscordPresence.discordRichPresence.largeImageText = DiscordPresence.clientVersion;
        DiscordPresence.discordRichPresence.state = null;
        DiscordPresence.discordRPC.Discord_UpdatePresence(DiscordPresence.discordRichPresence);
    }
    
    public static void stopRPC() {
        DiscordPresence.discordRPC.Discord_Shutdown();
        DiscordPresence.discordRPC.Discord_ClearPresence();
    }
    
    static {
        DiscordPresence.discordID = "977960534399914075";
        DiscordPresence.discordRichPresence = new DiscordRichPresence();
        DiscordPresence.discordRPC = DiscordRPC.INSTANCE;
        final StringBuilder append = new StringBuilder().append("Kaotik ");
        final Kaotik instance = Kaotik.INSTANCE;
        DiscordPresence.clientVersion = append.append("1.3.5").toString();
    }
}
