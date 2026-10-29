package io.github.Spoffee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.viewport.FitViewport;


public class Player {
    private float speed = 0.5f;
    private Sprite playerSprite;
    private int itemsUsed = 0;
    private int hiddenEventsActivated = 0;
    private Texture spriteImage;
    private Inventory playerInventory = new Inventory();
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

    public void drawPlayer(SpriteBatch batch){
        playerSprite.draw(batch);
    }

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

    public void playerLogic(FitViewport viewport, GameScreen display){
        float worldWidth = 80f;
        float worldHeight = 50f;

        
        playerSprite.setX(MathUtils.clamp(playerSprite.getX(), 0, worldWidth - playerSprite.getWidth()));
        playerSprite.setY(MathUtils.clamp(playerSprite.getY(), 0, worldHeight - playerSprite.getHeight()));
        
        
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

    public Inventory getInventory(){
        return playerInventory;
    }
}
