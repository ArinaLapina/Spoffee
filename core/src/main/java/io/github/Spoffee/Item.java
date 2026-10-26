package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private Texture itemTexture;
    public String itemName;
    private boolean itemUsed = false;
    private int geeseDispered;
    private Event linkedEvent;

    public Item(String filePath, int geeseDisperalNum, Event eventLinked, String itemNameInput){
        itemTexture = new Texture(filePath);
        geeseDispered = geeseDisperalNum;
        linkedEvent = eventLinked;
        itemName = itemNameInput;
    }

    /** Causes the action based off the item to trigger
     * @param delta
     */
    public void useItem(Item itemToUse, Player userSprite, ArrayList<Goose> activeGeese){
        if ("energyDrink".equals(itemName)){
            itemUsed = true;
            userSprite.increaseItemUsedCounter();
            userSprite.setSpeed(1f);
        }

        if ("speaker".equals(itemName)){
            itemUsed = true;
            userSprite.increaseItemUsedCounter();
            int geeseToDisperse = Math.min(geeseDispered, activeGeese.size());
            int geeseRemoved = 0;

            while (geeseRemoved != geeseToDisperse){
                int randomIndex = (int) (Math.random() * (activeGeese.size()));

                if (activeGeese.get(randomIndex).getActiveStatus()){
                    activeGeese.get(randomIndex).disperseGoose();
                    geeseRemoved += 1;
                }
            }
        }
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
    public int getGeeseDispered(){
        return geeseDispered;
    }

    /** 
     * @return Event
     */
    public Event getLinkedEvent(){
        return linkedEvent;
    }
}
