package io.github.Spoffee;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Inventory {
    private ArrayList<ItemParentClass> allItems = new ArrayList<>();
    private ArrayList<ItemParentClass> playerInventory = new ArrayList<>();
    
    public Inventory(){
        //Create objects of each item that could be used by a player
        EnergyDrink item1 = new EnergyDrink("coffeeItem.png", 0);
        Foghorn item2 = new Foghorn("foghornItem.png", 3);

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
            batch.draw(playerInventory.get(0).getTexture(),0f, heightMap-1, 1.75f, 1.5f);
        }
   
        if (playerInventory.get(1).getItemActivationStatus() == false){
            batch.draw(playerInventory.get(1).getTexture(),2f, heightMap-1, 1.75f, 1.5f);
        }
    }

    /** 
     * Checks if the input trigger for item use has been activated, and if so calls the item use
     * @param userSprite
     * @param activeGeese
     */
    public void  itemInput(Player userSprite, ArrayList<Goose> activeGeese){
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            playerInventory.get(0).useItem(userSprite, activeGeese);
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            playerInventory.get(1).useItem(userSprite, activeGeese);
        }
    }

    public void itemLogic(Player userSprite){
        for (int i = 0; i < allItems.size(); i++) {
            if (allItems.get(i).getItemActivationStatus()){
                allItems.get(i).itemLogic(userSprite);
            }
        }
    }

    public ArrayList<ItemParentClass> getInventory(){
        return playerInventory;
    }


}
