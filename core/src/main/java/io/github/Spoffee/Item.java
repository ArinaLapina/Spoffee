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

    public void useItem(){
        //Code that triggers the item to be used
    }
}
