package io.github.Spoffee;

import java.util.ArrayList;
import java.util.Iterator;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class GameScreen implements Screen {
    private final GooseGame parent;
    private SpriteBatch batch1;
    private SpriteBatch batch2;
    private Player userSprite;
    private FitViewport viewport;
    private FitViewport hudViewport;
    private ArrayList<Goose> activeGeese = new ArrayList<>();;
    private BitmapFont font;
    private Timer timerForGame;
    private MapLayer playerCollisionLayer;
    private ArrayList<Rectangle> playerCollisonAreas = new ArrayList<Rectangle>();
    private MapLayer gooseCollisionLayer;
    private ArrayList<Rectangle> gooseCollisonAreas = new ArrayList<Rectangle>();
    private MapLayer eventsLayer;
    private ArrayList<EventParentClass> activeEvents = new ArrayList<EventParentClass>();
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private static final float UNIT_SCALE = 1f / 32f;
    private Rectangle endGoalRectangle;

    public GameScreen(GooseGame parentClass) {
        parent = parentClass;
    }

    /**
     * Initialises the game setup
     * Set up of: Sprite batches, map -including areas that the player cannot go through, player,
     * events, inventory, main game timer, font, geese
     */
    @Override
    public void show() {
        // Creates the batches for player, items and text to be displayed on
        batch1 = new SpriteBatch();
        batch2 = new SpriteBatch();

        //Loads the map and sets it up
        map = new TmxMapLoader().load("MapGraphics/map2.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map, UNIT_SCALE);


        //Gets the collision and event layers from the map
        playerCollisionLayer = map.getLayers().get("playerCollisionLayer");
        gooseCollisionLayer = map.getLayers().get("gooseCollisionLayer");
        eventsLayer = map.getLayers().get("eventLayer");

        //Generates overlap rectangles for the out of bounds area for the player
        for (MapObject object : playerCollisionLayer.getObjects()) {
            Rectangle tiledRectangle = ((RectangleMapObject) object).getRectangle();

            Rectangle worldRectangle = new Rectangle(
                        tiledRectangle.x * UNIT_SCALE,
                        tiledRectangle.y * UNIT_SCALE,
                        tiledRectangle.width * UNIT_SCALE,
                        tiledRectangle.height * UNIT_SCALE);

            playerCollisonAreas.add(worldRectangle);
            
        }

        //Generates overlap rectangles for the out of bounds area for the geese
        for (MapObject object : gooseCollisionLayer.getObjects()) {
            Rectangle tiledRectangle = ((RectangleMapObject) object).getRectangle();

            Rectangle worldRectangle = new Rectangle(
                        tiledRectangle.x * UNIT_SCALE,
                        tiledRectangle.y * UNIT_SCALE,
                        tiledRectangle.width * UNIT_SCALE,
                        tiledRectangle.height * UNIT_SCALE);

            gooseCollisonAreas.add(worldRectangle);
            
        }

        //Generates overlap rectangles for the active events, and initialises all events as objects
        for (MapObject object : eventsLayer.getObjects()) {
            Rectangle newRect = ((RectangleMapObject) object).getRectangle();
            Rectangle worldRectangle = new Rectangle(
                        newRect.x * UNIT_SCALE,
                        newRect.y * UNIT_SCALE,
                        newRect.width * UNIT_SCALE,
                        newRect.height * UNIT_SCALE);

            Integer eventID = object.getProperties().get("eventID", Integer.class);
            EventParentClass event;
            
            switch (eventID) {
                case 1:
                    event = new LakeGeese(worldRectangle);
                    break;
                case 2:
                    event = new Lecture(worldRectangle);
                    break;
                case 0:
                    endGoalRectangle = worldRectangle;
                default:
                    continue;
            }
            
            activeEvents.add(event);
        }

        //Initialises the player and 5 minute timer
        userSprite = new Player();
        timerForGame = new Timer(300000);

        //Sets up the camera and display for the screen
        viewport = new FitViewport(20, 15f);
        hudViewport = new FitViewport(20, 15f);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        //Creates the font with correct style to display text
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight())*2f);
        font.setColor(Color.WHITE);


        //Initialise 5 geese on the map with different starting locations
        float[][] geeseLocations = new float[][] {{0, 2, 1f, 1f}, 
                                                {2, 1, 1f, 1f},
                                                {3, 3, 1f, 1f},
                                                {3, 2, 1f, 1f},
                                                {4, 0, 1f, 1f},
                                                {5f, 2.5f, 1f, 1f},
                                            };


        for (int i = 0; i < 6; i++) {
            Goose newGoose = new Goose("goose.png", (int) geeseLocations[i][0], 
                                                        (int) geeseLocations[i][1],
                                                        geeseLocations[i][2], 
                                                        geeseLocations[i][3]);
            
            activeGeese.add(newGoose);
        }

    }

    /**
     * Changes the position of the camera, depending on the location of the player on the map
     */
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

    
    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    /** 
     * Checks for player input for movement and item use
     */
    private void input() {
        // Triggers the player movement when the appropriate keys have been pressed
        userSprite.playerInput();

        // Checks if the key to trigger any items have been activated
        userSprite.getInventory().itemInput(userSprite, activeGeese);

    }


    /**
     * Updates the game states and checks if anything needs changing after
     * proccessing input
     * 
     */
    private void logic() {
        // Stops the user going out of bounds, or through certain areas of the map
        userSprite.playerLogic(playerCollisonAreas);

        //Checks if any item timers have expired and if so reverts the item power
        userSprite.getInventory().itemLogic(userSprite);

        // Controls the geese to always follow the player, and stay in the boundaries of the map
        for (int i = 0; i < activeGeese.size(); i++) {
            activeGeese.get(i).gooseMovement(userSprite, gooseCollisonAreas, i);
        }

        //Changes the camera position if neccessary after player movement
        updateCamera();


        //Checks if any events need to be activated and runs their ongoing logic.
        //The activation flag prevents an event from being triggered more than once.
        Iterator<EventParentClass> eventIterator = activeEvents.iterator();
        while (eventIterator.hasNext()){
            EventParentClass event = eventIterator.next();
            if (userSprite.getPlayerRectangle().overlaps(event.getRectangle())){
                event.activateEvent(userSprite, activeGeese);
            }

            if (event.getEventActivationStatus()){
                event.eventLogic(userSprite);
            }
        }

        //Checks if the timer has expired, and if so switches to the loss endgame screen
        if (timerForGame.toString().equals("0:00")){
            parent.changeScreen(GooseGame.LOSSGAME, userSprite);
        }

        // Add a trigger for overlap with end goal area (from Events Layer) and switch to win endgame screen
        if (userSprite.getPlayerRectangle().overlaps(endGoalRectangle)){
            parent.changeScreen(GooseGame.WINGAME, userSprite);
        }


    }

    /**
     * Displays everything onto the screen in the current positions
     * Draws user, geese, items, timer and counters onto the screen
     */
    private void draw() {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();

        mapRenderer.setView((OrthographicCamera) viewport.getCamera());
        mapRenderer.render();

        batch1.setProjectionMatrix(viewport.getCamera().combined);            
        batch1.begin();

        userSprite.drawPlayer(batch1);

        for (Goose goose : activeGeese) {
            if (goose.getActiveStatus()) {
                goose.getGoose().draw(batch1);
            }
        }
        batch1.end();

        hudViewport.apply();
        batch2.setProjectionMatrix(hudViewport.getCamera().combined);
        batch2.begin();

        userSprite.getInventory().drawItems(batch2, 20, 14.5f);
        font.draw(batch2, "Timer" + timerForGame.toString(), 14.7f, 14.8f);
        font.draw(batch2, "Items Used: " + userSprite.getItemUsedCounter(), 14.7f, 14.3f);
        font.draw(batch2, "Events Activated: " + userSprite.getEventActiveCounter(), 14.7f, 13.8f);

        batch2.end();

    
}

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
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
        batch1.dispose();
        batch2.dispose();
        font.dispose();
    }
}
