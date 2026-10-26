package io.github.Spoffee;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

public class Goose {
    private Sprite gooseSprite;
    private Texture gooseTexture;
    private Vector2 goosePos;
    private Vector2 playerPos;
    private Vector2 direction;
    private boolean active = true;
    

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
    public void gooseMovement(Player player){
        Vector2 goosePos = new Vector2(gooseSprite.getX(), gooseSprite.getY());
        Vector2 playerPos = new Vector2(player.getPSprite().getX(), player.getPSprite().getY());
        Vector2 direction = new Vector2();

        direction.x = (playerPos.x + 40) - (goosePos.x + 40);
        direction.y = (playerPos.y + 40) - (goosePos.y + 40);

        gooseSprite.translateX(direction.x*0.01f);
        gooseSprite.translateY(direction.y*0.01f);
    }

    public boolean getActiveStatus(){
        return active;
    }

}
