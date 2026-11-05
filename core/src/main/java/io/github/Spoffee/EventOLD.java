package io.github.Spoffee;

import com.badlogic.gdx.math.Rectangle;

public class EventOLD {
    private boolean eventActivated = false;
    private Rectangle eventRectangle = new Rectangle();

    public EventOLD(Rectangle evRectangle){
        eventRectangle = evRectangle;
    }

    
    /** Triggers the event action, increases the players event used counter and changes it's state to tru
     * @param userSprite
     */
    public void triggerEvent( Player userSprite){
        userSprite.increaseEventActiveCounter();
        eventActivated = true;
    }


    public Rectangle getRectangle(){
        return eventRectangle;
    }


    public boolean getEventActivationStatus(){
        return eventActivated;
    }


    public void setEventActivationStatus(boolean newVal){
        eventActivated = newVal;
    }
}
