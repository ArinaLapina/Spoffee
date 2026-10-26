package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private final GooseGame parent;
    private SpriteBatch batch;
    private Item item1;
    private Event event1;
    private Event event2;
    private Texture backgroundImage;
    private Player userSprite;
    private FitViewport viewport;
    private ArrayList<Goose> activeGeese;
    private Goose newGoose;
    private float boostTimer = 0f;

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

        // Initialise 2 items and add to Player inventory
        item1 = new Item("energydrink.png", 0, null);
        userSprite.getInventory().pickUpItem(item1);

        // Initialise 2 hidden events, and create rectangles
        event1 = new Event(1, 1, 5, 5);
        event2 = new Event(1, 1, 5, 5);

        // Initialise 10 geese on the map
        // Need to work out a way to add different size, position and looks of geese
        activeGeese = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            newGoose = new Goose("goose.png", 1, 1, 0.5f, 0.5f);
            activeGeese.add(newGoose);
        }

        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);
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
        float speed = userSprite.getSpeed();
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            userSprite.getPSprite().translateX(speed * delta);
            userSprite.setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            userSprite.getPSprite().translateX(-speed * delta);
            userSprite.setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            userSprite.getPSprite().translateY(speed * delta);
            userSprite.setRectangle();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            userSprite.getPSprite().translateY(-speed * delta);
            userSprite.setRectangle();
        }

        // Checks if the key to trigger item 1 has been activated
        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_1)) {
            if (boostTimer <= 0f && !item1.getItemActivationStatus()) {
                item1.setItemActivationStatus(true);
// coding note: commented out 26/10 as functionality moved into Inventory when it was made
//            userSprite.increaseItemUsedCounter();
                userSprite.setSpeed(1f);
                boostTimer = 10f;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.NUM_2)) {
            // Code for activation event2
        }

        // Add code to display pause / resume game

    }

    /**
     * Updates the game states and checks if anything needs changing after
     * processing input
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
        // Checks if the timer for item 1 has expired and if so changes the speed back
        // to normal
        if (boostTimer > 0f) {
            boostTimer -= Gdx.graphics.getDeltaTime();

            if (boostTimer <= 0f) {
                boostTimer = 0f;
                userSprite.setSpeed(0.5f);
            }
        }

        // Checks if the user stops the player going out of bounds
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float spriteWidth = userSprite.getPSprite().getWidth();
        float spriteHeight = userSprite.getPSprite().getHeight();

        userSprite.getPSprite().setX(MathUtils.clamp(userSprite.getPSprite().getX(), 0, worldWidth - spriteWidth));
        userSprite.getPSprite().setY(MathUtils.clamp(userSprite.getPSprite().getY(), 0, worldHeight - spriteHeight));

        // Checks if the user is in an area that triggers a hidden event to occur
        if (userSprite.getPlayerRectangle().overlaps(event1.getRectangle())) {
            userSprite.increaseEventActiveCounter();
            // Action for event 1
        }

        if (userSprite.getPlayerRectangle().overlaps(event2.getRectangle())) {
            userSprite.increaseEventActiveCounter();
            // Action for event 2
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

        userSprite.getPSprite().draw(batch);

        // Displays the items that the player has in their inventory, if they can still
        // be used
        if (!item1.getItemActivationStatus()) {
            batch.draw(item1.getTexture(), 0f, 4.5f, 0.5f, 0.5f);
        }

        // Add code to display geese
        for (int i = 0; i < activeGeese.size(); i++) {
            activeGeese.get(i).getGoose().draw(batch);
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
    }
}
