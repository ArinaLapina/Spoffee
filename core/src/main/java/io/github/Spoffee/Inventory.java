package io.github.Spoffee;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory {
    private ArrayList<Item> allItems = new ArrayList<>();
    private ArrayList<Item> playerInventory = new ArrayList<>();
    
    public Inventory(){
        //Create objects of each item that could be used by a player
        Item item1 = new Item("energydrink.png", 0, null, "energyDrink");
        Item item2 = new Item("foghorn.png", 3, null, "speaker");

        allItems.add(item1);
        allItems.add(item2);

        //Adds a random item to the player's inventory
        int firstRandomNum = (int) (Math.random() * allItems.size());
        playerInventory.add(allItems.get(firstRandomNum));

        //Adds a different random item to the player's inventory, while making sure they are different
        while (playerInventory.size() != 2){
            int secondRandomNum = (int) (Math.random() * (allItems.size()));

            if (firstRandomNum != secondRandomNum){
                playerInventory.add(allItems.get(secondRandomNum));
            }
        }
    }

    /** 
     * If the item is in the player's inventory and hasn't been used then it will be displayed on the screen
     * @param batch
     */
    public void drawItems(SpriteBatch batch, float widthMap, float heightMap){
        if (playerInventory.get(0).getItemActivationStatus() == false){
            batch.draw(playerInventory.get(0).getTexture(),0f, heightMap-1, 1.5f, 1.5f);
        }
   
        if (playerInventory.get(1).getItemActivationStatus() == false){
            batch.draw(playerInventory.get(1).getTexture(),1.5f, heightMap-1, 1.5f, 1.5f);
        }
    }

    /** 
     * Checks if the input trigger for item use has been activated, and if so calls the item use
     * @param userSprite
     * @param activeGeese
     */
    public void  itemInput(Player userSprite, ArrayList<Goose> activeGeese){
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            playerInventory.get(0).useItem(playerInventory.get(0), userSprite, activeGeese);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            playerInventory.get(1).useItem(playerInventory.get(1), userSprite, activeGeese);            // Code for activation event2
        }
    }


    public ArrayList<Item> getInventory(){
        return playerInventory;
    }


}
