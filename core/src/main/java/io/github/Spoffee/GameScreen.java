package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private final GooseGame parent;
    private SpriteBatch batch;
    private Event event1;
    private Event event2;
    private Texture backgroundImg;
    private Player userSprite;
    private FitViewport viewport;
    private ArrayList<Goose> activeGeese;
    private Goose newGoose;
    private BitmapFont font;
    private Timer timerForGame;

    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float UNIT_SCALE = 1f / 32f;

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
        
        backgroundImg = new Texture("background.png");

        map = new TmxMapLoader().load("maps/map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);


        userSprite = new Player();
        timerForGame = new Timer(3000000);

        viewport = new FitViewport(10, 7.5f);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight())*2f);
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

    private void updateCamera(){
        float mapWidth = map.getProperties().get("width", Integer.class)
            * map.getProperties().get("tilewidth", Integer.class)
            * UNIT_SCALE;
        
        float mapHeight = map.getProperties().get("height", Integer.class)
            * map.getProperties().get("tileheight", Integer.class)
            * UNIT_SCALE;
        
        float halfViewportWidth = viewport.getWorldWidth() / 2f;
        float halfViewportHeight = viewport.getWorldHeight() / 2f;
    
        float cameraX = MathUtils.clamp(
            userSprite.getPSprite().getX() + userSprite.getPSprite().getWidth() / 2f,
            halfViewportWidth,
            mapWidth - halfViewportWidth
        );

        float cameraY = MathUtils.clamp(
                userSprite.getPSprite().getY() + userSprite.getPSprite().getHeight() / 2f,
                halfViewportHeight,
                mapHeight - halfViewportHeight
        );

        viewport.getCamera().position.set(cameraX, cameraY, 0);
        viewport.getCamera().update();
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
        userSprite.playerLogic(viewport, this);

        updateCamera();


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

        if (timerForGame.toString().equals("0:00")){
            parent.changeScreen(GooseGame.LOSSGAME, userSprite);
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
    ScreenUtils.clear(Color.BLACK);

    viewport.apply();

    mapRenderer.setView((OrthographicCamera) viewport.getCamera());
    mapRenderer.render();

    batch.setProjectionMatrix(viewport.getCamera().combined);
    batch.begin();

    userSprite.drawPlayer(batch);

    for (Goose goose : activeGeese) {
        if (goose.getActiveStatus()) {
            goose.getGoose().draw(batch);
        }
    }

    batch.end();
}

//font.draw(batch, "Timer" + timerForGame.toString(), 6f, 4.5f);
//font.draw(batch, "Items Used: " + userSprite.getItemUsedCounter(), 6f, 4.3f);
//font.draw(batch, "Events Activated: " + userSprite.getEventActiveCounter(), 6f, 4.1f);

    /**
     * @param width
     * @param height
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }


    public ArrayList<Goose> getActiveGeese(){
        return activeGeese;
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
        font.dispose();
    }
}
