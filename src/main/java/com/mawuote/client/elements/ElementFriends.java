package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import java.util.stream.*;
import net.minecraft.entity.player.*;
import java.util.function.*;
import com.mawuote.client.modules.client.*;
import java.util.*;
import com.mojang.realmsclient.gui.*;

public class ElementFriends extends Element
{
    public static ValueString name;
    public static ValueEnum color;
    
    public ElementFriends() {
        super("Friends", "Gives you a list of friends in your chunk distance.");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        final ArrayList<EntityPlayer> friends = (ArrayList<EntityPlayer>)ElementFriends.mc.field_71441_e.field_73010_i.stream().filter(p -> Kaotik.FRIEND_MANAGER.isFriend(p.func_70005_c_())).collect(Collectors.toCollection(ArrayList::new));
        friends.sort(Comparator.comparing((Function<? super EntityPlayer, ? extends Comparable>)EntityPlayer::func_70005_c_));
        this.frame.setWidth(friends.isEmpty() ? Kaotik.FONT_MANAGER.getStringWidth(ElementFriends.name.getValue()) : Kaotik.FONT_MANAGER.getStringWidth(friends.get(0).func_70005_c_()));
        this.frame.setHeight(Kaotik.FONT_MANAGER.getHeight() + (friends.isEmpty() ? 0.0f : (1.0f + (Kaotik.FONT_MANAGER.getHeight() + 1.0f) * (friends.size() + 1))));
        Kaotik.FONT_MANAGER.drawString(ElementFriends.name.getValue(), this.frame.getX(), this.frame.getY(), ModuleColor.getActualColor());
        int index = 10;
        for (final EntityPlayer player : friends) {
            Kaotik.FONT_MANAGER.drawString(this.getColor() + player.func_70005_c_(), this.frame.getX(), this.frame.getY() + index, ModuleColor.getActualColor());
            index += 10;
        }
    }
    
    private ChatFormatting getColor() {
        if (ElementFriends.color.getValue().equals(Colors.White)) {
            return ChatFormatting.WHITE;
        }
        if (ElementFriends.color.getValue().equals(Colors.Gray)) {
            return ChatFormatting.GRAY;
        }
        return ChatFormatting.RESET;
    }
    
    static {
        ElementFriends.name = new ValueString("Name", "Name", "The name for the group of friends.", "The Goons");
        ElementFriends.color = new ValueEnum("Color", "Color", "The color for the friend names.", Colors.White);
    }
    
    public enum Colors
    {
        Normal, 
        White, 
        Gray;
    }
}
