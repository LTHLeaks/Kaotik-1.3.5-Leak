package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;

public class ElementPlayerViewer extends Element
{
    public static ValueBoolean viewTarget;
    public static ValueEnum lookMode;
    public static ValueNumber scale;
    
    public ElementPlayerViewer() {
        super("PlayerViewer", "Player Viewer", "Renders yourself on the screen.");
    }
    
    static {
        ElementPlayerViewer.viewTarget = new ValueBoolean("ViewTarget", "ViewTarget", "Views the nearest player, and when there are no players it renders you.", false);
        ElementPlayerViewer.lookMode = new ValueEnum("Look", "Look", "The mode for the player's looking.", LookModes.None);
        ElementPlayerViewer.scale = new ValueNumber("Scale", "Scale", "The scale for the player.", 3, 1, 10);
    }
    
    public enum LookModes
    {
        None, 
        Free, 
        Mouse;
    }
}
