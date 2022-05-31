package com.mawuote.api.manager.command;

import net.minecraft.client.*;
import net.minecraftforge.common.*;
import com.mawuote.client.commands.*;
import net.minecraftforge.client.event.*;
import com.mawuote.api.manager.misc.*;
import java.util.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class CommandManager
{
    protected static final Minecraft mc;
    private String prefix;
    private ArrayList<Command> commands;
    
    public CommandManager() {
        this.prefix = ";";
        MinecraftForge.EVENT_BUS.register((Object)this);
        this.commands = new ArrayList<Command>();
        this.register(new CommandBind());
        this.register(new CommandFriend());
        this.register(new CommandPrefix());
        this.register(new CommandValue());
    }
    
    @SubscribeEvent
    public void onChatSent(final ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(this.getPrefix())) {
            event.setCanceled(true);
            message = message.substring(this.getPrefix().length());
            if (message.split(" ").length > 0) {
                final String name = message.split(" ")[0];
                boolean found = false;
                for (final Command command : this.getCommands()) {
                    if (command.getAliases().contains(name.toLowerCase()) || command.getName().equalsIgnoreCase(name)) {
                        CommandManager.mc.field_71456_v.func_146158_b().func_146239_a(event.getMessage());
                        command.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length));
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    ChatManager.printChatNotifyClient("Command could not be found.");
                }
            }
        }
    }
    
    public void register(final Command command) {
        this.commands.add(command);
    }
    
    public ArrayList<Command> getCommands() {
        return this.commands;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    static {
        mc = Minecraft.func_71410_x();
    }
}
