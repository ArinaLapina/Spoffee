package io.github.Spoffee;

import com.badlogic.gdx.math.Rectangle;

public class Event {
    private boolean eventActivated = false;
    private float xPos;
    private float yPos;
    private float width;
    private float height;
    private Rectangle eventRectangle = new Rectangle();

    public Event(int x, int y, int widthOfEvent, int heightOfEvent){
        xPos = x;
        yPos = y;
        width = widthOfEvent;
        height = heightOfEvent;
        eventRectangle.set(xPos, yPos, widthOfEvent, heightOfEvent);
    }

    private void triggerEvent(){
        //Code that causes something to happen from the event
    }

    public Rectangle getRectangle(){
        return eventRectangle;
    }

    public boolean getEventActivationStatus(){
        return eventActivated;
    }

    public float getXpos(){
        return xPos;
    }

    public float getYpos(){
        return yPos;
    }

    public float getWidth(){
        return width;
    }

    public float getHeight(){
        return height;
    }
}
