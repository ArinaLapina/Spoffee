package io.github.Spoffee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;
import com.badlogic.gdx.math.Rectangle;


public class Player {
    private float speed = 0.5f;
    private Sprite playerSprite;
    private int itemsUsed = 0;
    private int hiddenEventsActivated = 0;
    private Texture spriteImage;
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Rectangle playerRectangle = new Rectangle(0, 0, 0.75f, 1f);

    public Player(){
        spriteImage = new Texture("person.png");
        playerSprite = new Sprite(spriteImage);
        playerSprite.setSize(0.75f, 1f);
        playerSprite.setPosition(0, 0);
    }

    /** 
     * @return Rectangle
     */
    public Rectangle getPlayerRectangle(){
        return playerRectangle;
    }

    /** 
     * @param delta
     */
    public void setRectangle(){
        playerRectangle.set(playerSprite.getX(), playerSprite.getY(), playerSprite.getWidth(), playerSprite.getHeight());
    }

    /** 
     * @return float
     */
    public float getSpeed(){
        return speed;
    }

    /** 
     * @param newSpeed
     */
    public void setSpeed(float newSpeed){
        speed = newSpeed;
    }

    /** 
     * @return Sprite
     */
    public Sprite getPSprite(){
        return playerSprite;
    }

    /** 
     * @return int
     */
    public int getItemUsedCounter(){
        return itemsUsed;
    }

    public void increaseItemUsedCounter(){
        itemsUsed += 1;
    }

    /** 
     * @return int
     */
    public int getEventActiveCounter(){
        return hiddenEventsActivated;
    }

    public void increaseEventActiveCounter(){
        hiddenEventsActivated += 1;
    }

}
