package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private final GooseGame parent;
    private SpriteBatch batch;
    private Event event1;
    private Event event2;
    private Texture backgroundImage;
    private Player userSprite;
    private FitViewport viewport;
    private ArrayList<Goose> activeGeese;
    private Goose newGoose;
    private BitmapFont font;

    public GameScreen(GooseGame parentClass) {
        parent = parentClass;
    }

    /**
     * Initialises the map, user sprite, items, events, window and starting geese
     *
     * @param delta
     */
    @Override
    public void show() {
        // Creates the background, player and the window
        batch = new SpriteBatch();
        
        backgroundImage = new Texture("background.png");

        userSprite = new Player();

        viewport = new FitViewport(8, 5);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight())*1.5f);
        font.setColor(Color.BLACK);


        // Initialise 2 hidden events, and create rectangles
        event1 = new Event(1, 1, 5, 5, "lecture");
        event2 = new Event(1, 1, 5, 5, "lakeGeese");

        //Initialise 5 geese on the map with different starting locations
 
        float[][] geeseLocations = new float[][] {{1, 2, 0.5f, 0.5f}, 
                                                {0, 4, 0.5f, 0.5f},
                                                {1, 1, 0.5f, 0.5f},
                                                {2, 2, 0.5f, 0.5f},
                                                {3, 5, 0.5f, 0.5f},
                                                {2.5f, 2.5f, 0.5f, 0.5f},
                                            };


        activeGeese = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            newGoose = new Goose("goose.png", (int) geeseLocations[i][0], 
                                                        (int) geeseLocations[i][1],
                                                        geeseLocations[i][2], 
                                                        geeseLocations[i][3]);
            
            activeGeese.add(newGoose);
        }

    }

    /**
     * @param delta
     */
    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    /**
     * Checks the player's keyboard inputs each frame, and responds accordingly
     * Controls movement, item use activation and updating players movement each
     * movement
     *
     * @param delta
     */
    private void input() {
        // Triggers the player movement when the appropriate keys have been pressed
        userSprite.playerInput();

        // Checks if the key to trigger any items have been activated
        userSprite.getInventory().itemInput(userSprite, activeGeese);

        // Add code to display pause / resume game

    }

    /**
     * Updates the game states and checks if anything needs changing after
     * proccessing input
     * Controls how long an item is in for, if the player is at a map boundary, it a
     * hidden event
     * should be activated and the direction of geese movement once the player has
     * moved
     *
     * Also will check if the win / loss conditions have been met and if so with
     * change to the appropriate screen
     *
     * @param delta
     */
    private void logic() {
        // Checks if the user stops the player going out of bounds
        userSprite.playerLogic(viewport);


        //Checks if the user is in an area that triggers a hidden event to occur
        if (userSprite.getPlayerRectangle().overlaps(event1.getRectangle())){
            if (event1.getEventActivationStatus() == false){
                event1.setEventActivationStatus(true);
                userSprite.increaseEventActiveCounter();
                // Action for event 2
            }
        }

        if (userSprite.getPlayerRectangle().overlaps(event2.getRectangle())){
            if (event2.getEventActivationStatus() == false){
                event2.setEventActivationStatus(true);
                userSprite.increaseEventActiveCounter();
                // Action for event 2
            }
        }

        // Triggers the change in direction of each goose's direction of movement
        // depending on position of the player
        for (int i = 0; i < activeGeese.size(); i++) {
            activeGeese.get(i).gooseMovement(userSprite);
        }

        // Add a trigger for overlap with end goal area and when triggered:
        // IF WIN: parent.changeScreen(GooseGame.WINGAME, userSprite);
        // IF LOSS: parent.changeScreen(GooseGame.LOSSGAME, userSprite);

    }

    /**
     * Clears the screen, then renders the background, user, items and geese onto
     * the screen
     *
     * @param delta
     */
    private void draw() {
        // Displays the background and player on the screen
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);

        batch.begin();

        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        font.draw(batch, "Timer", 6.9f, 4.5f);
        font.draw(batch, "Items Used: " + userSprite.getItemUsedCounter(), 6.9f, 4.3f);
        font.draw(batch, "Events Activated: " + userSprite.getEventActiveCounter(), 6.9f, 4.1f);

        userSprite.drawPlayer(batch);
        userSprite.getInventory().drawItems(batch);

        // Add code to display geese
        for (int i = 0; i < activeGeese.size(); i++) {
            if (activeGeese.get(i).getActiveStatus() == true){
                activeGeese.get(i).getGoose().draw(batch);
            }
        }

        batch.end();

    }

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        font.dispose();
    }
}
