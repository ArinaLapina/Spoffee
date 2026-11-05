package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;


public class Player {
    private float speed = 0.5f;
    private Sprite playerSprite;
    private int itemsUsed = 0;
    private int hiddenEventsActivated = 0;
    private Texture spriteImage;
    private Inventory playerInventory = new Inventory();
    private Rectangle playerRectangle = new Rectangle(8f, 28f, 3.5f, 2f);

    public Player(int xPos, int yPos){
        spriteImage = new Texture("playerSprite.png");
        playerSprite = new Sprite(spriteImage);
        playerSprite.setSize(4f, 3f);
        playerSprite.setPosition(8f, 28f);
    }


    public void drawPlayer(SpriteBatch batch){
        playerSprite.draw(batch);
    }

    /** 
     * Checks for player input for movement based of arrow / wasd keys, and changes direction
     */
    public void playerInput(){
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            playerSprite.translateX(speed * delta);
            setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            playerSprite.translateX(-speed * delta);
            setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            playerSprite.translateY(speed * delta);
            setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            playerSprite.translateY(-speed * delta);
            setRectangle();
        }
    }

    /** 
     * Checks if the user in the out of bounds areas and if so stops them
     */
    public void playerLogic(ArrayList<Rectangle> collisionAreas){
        //Stops the player going into certain areas of the map (such as buildings or lakes)
        for (Rectangle solid : collisionAreas) {
                Rectangle intersection = new Rectangle();
                if (Intersector.intersectRectangles(playerRectangle, solid, intersection)) {
                    if (intersection.getWidth() < intersection.getHeight()) {
                        if (playerRectangle.getX() < solid.getX()) {
                            playerSprite.setX(playerSprite.getX() - intersection.getWidth()); // Push left
                        } else {
                            playerSprite.setX(playerSprite.getX() + intersection.getWidth()); // Push right
                        }
                    } else {
                        if (playerSprite.getY() < solid.getY()) {
                            playerSprite.setY(playerSprite.getY() - intersection.getHeight()); // Push down
                        } else {
                            playerSprite.setY(playerSprite.getY() + intersection.getHeight()); // Push up
                        }
                    }
                }
            }

        //Stops the player from going outside of the map by only allowing a certain range of X, Y values
        float worldWidth = 80f;
        float worldHeight = 50f;

        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, worldWidth - playerSprite.getWidth()));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(), 0, worldHeight - playerSprite.getHeight()));
        setRectangle();
    }

    
    public Rectangle getPlayerRectangle(){
        return playerRectangle;
    }


    public void setRectangle(){
        playerRectangle.set(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }


    public float getSpeed(){
        return speed;
    }


    public void setSpeed(float newSpeed){
        speed = newSpeed;
    }


    public Sprite getPSprite(){
        return playerSprite;
    }


    public int getItemUsedCounter(){
        return itemsUsed;
    }


    public void increaseItemUsedCounter(){
        itemsUsed += 1;
    }


    public int getEventActiveCounter(){
        return hiddenEventsActivated;
    }


    public void increaseEventActiveCounter(){
        hiddenEventsActivated += 1;
    }


    public Inventory getInventory(){
        return playerInventory;
    }
}
