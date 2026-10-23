package io.github.Spoffee;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Goose {
    private Sprite gooseSprite;
    private Texture gooseTexture;

    public Goose(String filePath, int xPos, int yPos, float width, float height){
        gooseTexture = new Texture(filePath);
        gooseSprite = new Sprite(gooseTexture);
        gooseSprite.setSize(width, height);
        gooseSprite.setPosition(xPos, yPos);
    }

    /** 
     * @return Sprite
     */
    public Sprite getGoose(){
        return gooseSprite;
    }

    /** Changes the direction of movement for the goose, according to the player's position
     * @param playerSprite
     */
    public void gooseMovement(Player playerSprite){

    }

}
