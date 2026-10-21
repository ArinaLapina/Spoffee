package io.github.Spoffee;

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

public class GameScreen implements Screen{
    private final GooseGame parent;
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Player userSprite;
    private FitViewport viewport;

    public GameScreen(GooseGame parentClass){
        parent = parentClass;
    }

    @Override 
    public void show(){
        //Creates the background, player and the window
        batch = new SpriteBatch();
        
        backgroundImage = new Texture("background.png");

        userSprite = new Player();

        viewport = new FitViewport(8, 5);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        //Initialise 2 items and add to Player inventory
        //Initialise 2 hidden events, and create rectangles

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
    }

    private void input(){
        //This is so that the player can move, with the arrow buttons
        float speed = 1f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            userSprite.playerSprite.translateX(speed * delta);
        } 
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            userSprite.playerSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            userSprite.playerSprite.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            userSprite.playerSprite.translateY(-speed * delta);
        }

        //Add triggers for when items are used
    }

    private void logic(){
        //Code that stops the player going out of bounds
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float spriteWidth = userSprite.playerSprite.getWidth();
        float spriteHeight = userSprite.playerSprite.getHeight();

        userSprite.playerSprite.setX(MathUtils.clamp(userSprite.playerSprite.getX(), 0, worldWidth - spriteWidth));
        userSprite.playerSprite.setY(MathUtils.clamp(userSprite.playerSprite.getY(), 0, worldHeight - spriteHeight));

        //Add triggers for overlaps with event areas

        //Add a trigger for overlap with end goal area and when triggered: 
                // parent.changeScreen(GooseGame.ENDGAME);

    }

    private void draw(){
        //Displays the background and player on the screen
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.begin();

        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        userSprite.playerSprite.draw(batch);

        //Add code to display geese

        batch.end();

    }

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