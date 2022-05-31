package com.mawuote.client.commands;

import com.mawuote.api.manager.command.*;
import com.mawuote.*;
import com.mawuote.api.manager.misc.*;

public class CommandPrefix extends Command
{
    public CommandPrefix() {
        super("prefix", "Let's you change the prefix using commands.", "prefix <input>", new String[] { "cmdprefix", "commandprefix", "cmdp", "commandp" });
    }
    
    @Override
    public void onCommand(final String[] args) {
        Kaotik.COMMAND_MANAGER.setPrefix(args[0]);
        ChatManager.printChatNotifyClient("Prefix has been set to " + args[0]);
    }
}
