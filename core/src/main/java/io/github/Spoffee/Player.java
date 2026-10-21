package io.github.Spoffee;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import java.util.ArrayList;


public class Player {
    private float energyLeft;
    public Sprite playerSprite;
    private int itemsUsed = 0;
    private int hiddenEventsActivated = 0;
    private Texture spriteImage;
    private ArrayList<Item> inventory = new ArrayList<Item>();

    public Player(){
        spriteImage = new Texture("person.png");
        playerSprite = new Sprite(spriteImage);
        playerSprite.setSize(0.75f, 1f);
        playerSprite.setPosition(0, 0);
    }


}
