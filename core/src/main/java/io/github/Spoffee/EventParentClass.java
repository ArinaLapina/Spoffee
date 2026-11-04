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
    
    public abstract void activateEvent(Player userSprite, ArrayList<Goose> activeGeese);

    public abstract void eventLogic(Player userSprite);

}

class Lecture extends EventParentClass{
    public Lecture(Rectangle eventRectangle){
        super(eventRectangle);
    }

    @Override
    public void activateEvent(Player userSprite, ArrayList<Goose> activeGeese){
        if (getEventActivationStatus()){
            return;
        }
        if (this.getTimer() == null){
            setTimer(new Timer(10000));
        }
        userSprite.setSpeed(0f);
    }

    @Override
    public void eventLogic(Player userSprite) {
        if (this.getTimer().toString().equals("0:00")){
            markAsUsed(userSprite);
            userSprite.setSpeed(0.5f);
        }
        
    }
}

class LakeGeese extends EventParentClass{
    public LakeGeese(Rectangle eventRectangle){
        super(eventRectangle);
    }

    @Override
    public void activateEvent(Player userSprite, ArrayList<Goose> activeGeese){
        if (getEventActivationStatus()){
            return;
        }

        markAsUsed(userSprite);

        //Change locations of added geese so they are from the lake edge

        float[][] geeseLocations = new float[][] {{0, 2, 1f, 1f}, 
                                                {2, 1, 1f, 1f},
                                                {3, 3, 1f, 1f},
                                                {3, 2, 1f, 1f},
                                                {4, 0, 1f, 1f},
                                                {5f, 2.5f, 1f, 1f},
                                            };


        for (int i = 0; i < 6; i++) {
            Goose newGoose = new Goose("goose.png", (int) geeseLocations[i][0], 
                                                        (int) geeseLocations[i][1],
                                                        geeseLocations[i][2], 
                                                        geeseLocations[i][3]);
            
            activeGeese.add(newGoose);
        }
    }

    @Override
    public void eventLogic(Player userSprite) {
    }

    
}