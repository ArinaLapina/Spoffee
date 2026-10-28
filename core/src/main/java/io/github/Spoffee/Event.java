package io.github.Spoffee;

import com.badlogic.gdx.math.Rectangle;

public class Event {
    private boolean eventActivated = false;
    private float xPos;
    private float yPos;
    private float width;
    private float height;
    private Rectangle eventRectangle = new Rectangle();
    private String eventName;

    public Event(int x, int y, int widthOfEvent, int heightOfEvent, String nameOfEvent){
        xPos = x;
        yPos = y;
        width = widthOfEvent;
        height = heightOfEvent;
        eventRectangle.set(xPos, yPos, widthOfEvent, heightOfEvent);
        eventName = nameOfEvent;
    }

    /** Causes the event action to trigger
     * @param delta
     */
    private void triggerEvent(){
    }

    /** 
     * @return Rectangle
     */
    public Rectangle getRectangle(){
        return eventRectangle;
    }

    /** 
     * @return boolean
     */
    public boolean getEventActivationStatus(){
        return eventActivated;
    }

    public void setEventActivationStatus(boolean newVal){
        eventActivated = newVal;
    }

    /** 
     * @return float
     */
    public float getXpos(){
        return xPos;
    }

    /** 
     * @return float
     */
    public float getYpos(){
        return yPos;
    }

    /** 
     * @return float
     */
    public float getWidth(){
        return width;
    }

    /** 
     * @return float
     */
    public float getHeight(){
        return height;
    }
}
