package io.github.Spoffee;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private Texture itemTexture;
    private boolean itemUsed = false;
    private int geeseDispersed;
    private Event linkedEvent;

    public Item(String filePath, int geeseDispersalNum, Event eventLinked){
        itemTexture = new Texture(filePath);
        geeseDispersed = geeseDispersalNum;
        linkedEvent = eventLinked;
    }

    /** Causes the action based off the item to trigger
     * @param delta
     */
    private void useItem(){
    }

    /**
     * @return Texture
     */
    public Texture getTexture(){
        return itemTexture;
    }

    /**
     * @return boolean
     */
    public boolean  getItemActivationStatus(){
        return itemUsed;
    }

    /**
     * @param varToSet
     */
    public void setItemActivationStatus(boolean varToSet){
        itemUsed = varToSet;
    }

    /**
     * @return int
     */
    public int getGeeseDispersed(){
        return geeseDispersed;
    }

    /**
     * @return Event
     */
    public Event getLinkedEvent(){
        return linkedEvent;
    }
}
