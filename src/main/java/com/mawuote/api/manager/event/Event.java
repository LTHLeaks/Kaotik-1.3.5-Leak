package com.mawuote.api.manager.event;

public class Event extends net.minecraftforge.fml.common.eventhandler.Event
{
    private boolean cancelled;
    private Stage stage;
    
    public Event() {
    }
    
    public Event(final Stage stage) {
        this.stage = stage;
    }
    
    public boolean isCancelled() {
        return this.cancelled;
    }
    
    public void setCancelled(final boolean cancel) {
        this.cancelled = cancel;
    }
    
    public Stage getStage() {
        return this.stage;
    }
    
    public boolean isPre() {
        return this.stage == Stage.PRE;
    }
    
    public boolean isPost() {
        return this.stage == Stage.POST;
    }
    
    public enum Stage
    {
        PRE, 
        POST;
    }
}
