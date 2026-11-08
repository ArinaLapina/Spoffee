package io.github.Spoffee;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class Goose {
    private Sprite gooseSprite;
    private Rectangle gooseRectangle;
    private Texture gooseTexture;
    private Vector2 goosePos;
    private Vector2 playerPos;
    private Vector2 direction;
    private boolean active = true;
    private float speed = 1.5f;
    

    public Goose(String filePath, int xPos, int yPos, float width, float height){
        gooseTexture = new Texture(filePath);
        gooseSprite = new Sprite(gooseTexture);
        gooseSprite.setSize(width, height);
        gooseSprite.setPosition(xPos, yPos);
        gooseRectangle = new Rectangle(xPos, yPos, width, height);
    }

    /** Changes the direction of movement for the goose, according to the player's position
     * Stops the geese going into the out of bounds areas
     * @param playerSprite - the sprite object of the player, so location can be found
     * @param collisionAreas - list of objects for out of bound areas
     */
    public void gooseMovement(Player player, ArrayList<Rectangle> collisionAreas, int offsetVal){
        //Changes the goose direction, so that they follow the player
        Vector2 goosePos = new Vector2(gooseSprite.getX(), gooseSprite.getY());
        Vector2 playerPos = new Vector2(player.getPSprite().getX(), player.getPSprite().getY());
        Vector2 direction = new Vector2();

        float delta = Gdx.graphics.getDeltaTime();

        //Work out how to use the offSet value to get the goose in slightly different positions
        float angle = offsetVal * (MathUtils.PI2 / 6f);
        float followDistance = 2.25f;

        float targetX = playerPos.x + MathUtils.cos(angle) * followDistance;
        float targetY = playerPos.y + MathUtils.sin(angle) * followDistance;

        direction.x = targetX - goosePos.x;
        direction.y = targetY - goosePos.y;
        gooseSprite.translateX(speed * delta * direction.x*0.1f);
        gooseSprite.translateY(speed * delta * direction.y*0.1f);

        gooseRectangle.set(gooseSprite.getX(), gooseSprite.getY(), gooseSprite.getWidth(), gooseSprite.getHeight());

        //Controls the area's the geese can and cannot go (eg. buildings)
        for (Rectangle solid : collisionAreas) {
                Rectangle intersection = new Rectangle();
                if (Intersector.intersectRectangles(gooseRectangle, solid, intersection)) {
                    if (intersection.getWidth() < intersection.getHeight()) {
                        if (gooseRectangle.getX() < solid.getX()) {
                            gooseSprite.setX(gooseSprite.getX() - intersection.getWidth()); // Push left
                        } else {
                            gooseSprite.setX(gooseSprite.getX() + intersection.getWidth()); // Push right
                        }
                    } else {
                        if (gooseSprite.getY() < solid.getY()) {
                            gooseSprite.setY(gooseSprite.getY() - intersection.getHeight()); // Push down
                        } else {
                            gooseSprite.setY(gooseSprite.getY() + intersection.getHeight()); // Push up
                        }
                    }
                }
            }
    }

    
    public boolean getActiveStatus(){
        return active;
    }


    public Sprite getGoose(){
        return gooseSprite;
    }


    public void disperseGoose(){
        active = false;
    }

    public Rectangle getGooseRectangle(){
        return gooseRectangle;
    }

}
