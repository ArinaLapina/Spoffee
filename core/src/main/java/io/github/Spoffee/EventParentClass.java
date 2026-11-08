package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.math.Rectangle;

public abstract class EventParentClass{
    private boolean eventActivated = false;
    private Rectangle eventRectangle = new Rectangle();
    private Timer eventTimer;

    public EventParentClass(Rectangle evRectangle){
        eventRectangle = evRectangle;
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

    protected void markAsUsed(Player userSprite){
        eventActivated = true;
        userSprite.increaseEventActiveCounter();
    }


    protected void setTimer(Timer timer){
        eventTimer = timer;
    }

    
    public Timer getTimer(){
        return eventTimer;
    }

    public String getDisplayText(){
        return null;
    }
    
    public abstract void activateEvent(Player userSprite, ArrayList<Goose> activeGeese);

    public abstract void eventLogic(Player userSprite);

}

class Lecture extends EventParentClass{
    private boolean speedRestored = false;

    public Lecture(Rectangle eventRectangle){
        super(eventRectangle);
    }

    /** 
     * Causes the activation of the Lecture event, so the playing stops moving and starts a 7.5s timer
     * @param userSprite
     * @param activeGeese
     */
    @Override
    public void activateEvent(Player userSprite, ArrayList<Goose> activeGeese){
        if (getEventActivationStatus()){
            return;
        }
        if (this.getTimer() == null){
            setTimer(new Timer(7500));
        }
        markAsUsed(userSprite);
        speedRestored = false;
        userSprite.setSpeed(0f);
    }

    /** 
     * Checks the value of the timer and if it's reached 0, sets the speed back to normal
     * Changes the variable speedRestored so other things affecting speed can be used after this event
     * @param userSprite
=     */
    @Override
    public void eventLogic(Player userSprite) {
        if (!speedRestored && this.getTimer().toString().equals("0:00")){
            userSprite.setSpeed(0.5f);
            speedRestored = true;
        }
    }

    /** 
     * For the duration of the active event it returns text to display on the screen
     * @return textToDispaly
     */

    @Override
    public String getDisplayText(){
        if (getTimer() != null && !speedRestored
                && !getTimer().toString().equals("0:00")){
            return "You've been stopped by a lecture";
        }
        return null;
    }
}

class LakeGeese extends EventParentClass{
    public LakeGeese(Rectangle eventRectangle){
        super(eventRectangle);
    }

    /** 
     * Causes the activation of the event by the lake, meaning that 3 to 6 more geese appear from the lake
     * @param userSprite
     * @param activeGeese
     */
    @Override
    public void activateEvent(Player userSprite, ArrayList<Goose> activeGeese){
        if (getEventActivationStatus()){
            return;
        }

        markAsUsed(userSprite);

        //Adds 3 to 6 geese on the map in a 6x2 area

        for (int i = 0; i < (int) (Math.random() * 4) + 3; i++) {
            Goose newGoose = new Goose("gooseSprite.png", ((int) userSprite.getPSprite().getX()-(int) (Math.random() * 7)-10) , 
                                                    ((int) userSprite.getPSprite().getY()-(int) (Math.random() * 3)),
                                                    3f, 2f);
            activeGeese.add(newGoose);
        }
    }

    @Override
    public void eventLogic(Player userSprite) {
    }

    
}