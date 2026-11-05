package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public class ItemOLD{
    private Texture itemTexture;
    public String itemName;
    private boolean itemUsed = false;
    private int geeseDispered;
    private EventOLD linkedEvent;
    private Timer timerForItem;

    public ItemOLD(String filePath, int geeseDisperalNum, EventOLD eventLinked, String itemNameInput){
        itemTexture = new Texture(filePath);
        geeseDispered = geeseDisperalNum;
        linkedEvent = eventLinked;
        itemName = itemNameInput;
    }

    /** Checks if the relavent item has been used and if not triggers it's action
     * @param delta
     */
    public void useItem(ItemOLD itemToUse, Player userSprite, ArrayList<Goose> activeGeese){
        if ("energyDrink".equals(itemName) && itemToUse.getItemActivationStatus() == false){
            //The energy drink item increases the players speed
            timerForItem = new Timer(10000);
            itemUsed = true;
            userSprite.increaseItemUsedCounter();
            userSprite.setSpeed(1f);
        }

        if ("speaker".equals(itemName)&& itemToUse.getItemActivationStatus() == false){
            //The speaker item causes 3 random geese to disperse from the game
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


    public EventOLD getLinkedEvent(){
        return linkedEvent;
    }

    public Timer getTimer(){
        return timerForItem;
    }
}
