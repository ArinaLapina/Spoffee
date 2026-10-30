package io.github.Spoffee;

import com.badlogic.gdx.math.Rectangle;

public class Event {
    private boolean eventActivated = false;
    private Rectangle eventRectangle = new Rectangle();
    private String eventName;

    public Event(Rectangle evRectangle, String nameOfEvent){
        eventRectangle = evRectangle;
        eventName = nameOfEvent;
    }

    
    /** Triggers the event action, increases the players event used counter and changes it's state to tru
     * @param userSprite
     */
    public void triggerEvent( Player userSprite){
        userSprite.increaseEventActiveCounter();
        eventActivated = true;

        if (eventName.equals("lecture")){

        }

        if (eventName.equals("lakeGeese")){
            
        }
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
