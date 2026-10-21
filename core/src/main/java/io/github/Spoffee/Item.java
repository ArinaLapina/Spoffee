package io.github.Spoffee;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private Texture itemTexture;
    private boolean itemUsed = false;
    private int geeseDispered;
    private Event linkedEvent;

    public Item(String filePath, int geeseDisperalNum, Event eventLinked){
        itemTexture = new Texture(filePath);
        geeseDispered = geeseDisperalNum;
        linkedEvent = eventLinked;
    }

    private void useItem(){
        //Code that triggers the item to be used
    }

    public Texture getTexture(){
        return itemTexture;
    }

    public boolean  getItemActivationStatus(){
        return itemUsed;
    }

    public void setItemActivationStatus(boolean varToSet){
        itemUsed = varToSet;
    }

    public int getGeeseDispered(){
        return geeseDispered;
    }

    public Event getLinkedEvent(){
        return linkedEvent;
    }
}
