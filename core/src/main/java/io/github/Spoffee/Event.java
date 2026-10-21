package io.github.Spoffee;

public class Event {
    private boolean eventActivated = false;
    private int xPos;
    private int yPos;
    private int width;
    private int height;

    public Event(int x, int y, int widthOfEvent, int heightOfEvent){
        xPos = x;
        yPos = y;
        width = widthOfEvent;
        height = heightOfEvent;
    }

    private void triggerEvent(){
        //Code that causes something to happen from the event
    }
}
