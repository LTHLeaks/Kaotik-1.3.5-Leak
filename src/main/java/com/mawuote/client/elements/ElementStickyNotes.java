package com.mawuote.client.elements;

import com.mawuote.api.manager.element.*;
import com.mawuote.api.manager.value.impl.*;
import com.mawuote.api.manager.event.impl.render.*;
import com.mawuote.*;
import com.mawuote.client.modules.client.*;

public class ElementStickyNotes extends Element
{
    public static ValueEnum lines;
    public static ValueString lineOne;
    public static ValueString lineTwo;
    public static ValueString lineThree;
    public static ValueString lineFour;
    
    public ElementStickyNotes() {
        super("StickyNotes", "Sticky Notes", "Let's you write custom stuff on the screen.");
    }
    
    @Override
    public void onRender2D(final EventRender2D event) {
        super.onRender2D(event);
        this.frame.setWidth(Kaotik.FONT_MANAGER.getStringWidth(ElementStickyNotes.lineOne.getValue()));
        this.frame.setHeight(Kaotik.FONT_MANAGER.getHeight() * this.getMultiplier() + this.getMultiplier());
        for (int i = 0; i <= this.getMultiplier() - 1; ++i) {
            Kaotik.FONT_MANAGER.drawString((i == 1) ? ElementStickyNotes.lineTwo.getValue() : ((i == 2) ? ElementStickyNotes.lineThree.getValue() : ((i == 3) ? ElementStickyNotes.lineFour.getValue() : ElementStickyNotes.lineOne.getValue())), this.frame.getX(), this.frame.getY() + (Kaotik.FONT_MANAGER.getHeight() + 1.0f) * i, ModuleColor.getActualColor());
        }
    }
    
    public int getMultiplier() {
        switch (ElementStickyNotes.lines.getValue()) {
            case Two: {
                return 2;
            }
            case Three: {
                return 3;
            }
            case Four: {
                return 4;
            }
            default: {
                return 1;
            }
        }
    }
    
    static {
        ElementStickyNotes.lines = new ValueEnum("Lines", "Lines", "The amount of lines that should be rendered.", LinesAmount.One);
        ElementStickyNotes.lineOne = new ValueString("LineOne", "LineOne", "The first line.", "Placeholder");
        ElementStickyNotes.lineTwo = new ValueString("LineTwo", "LineTwo", "The second line.", "Placeholder");
        ElementStickyNotes.lineThree = new ValueString("LineThree", "LineThree", "The third line.", "Placeholder");
        ElementStickyNotes.lineFour = new ValueString("LineFour", "LineFour", "The fourth line.", "Placeholder");
    }
    
    public enum LinesAmount
    {
        One, 
        Two, 
        Three, 
        Four;
    }
}
