package io.github.Spoffee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WinScreen implements Screen{
    private final GooseGame parent;
    private FitViewport viewport;
    private BitmapFont font;
    private SpriteBatch batch;
    private final int itemsUsed;
    private final int eventsActivated;

    public WinScreen(GooseGame parentClass, int itemsUsed, int eventsActivated){
        parent = parentClass;
        this.itemsUsed = itemsUsed;
        this.eventsActivated = eventsActivated;
    }

    /** Intialises the window and text for endgame in a win of the game
     * @param delta
     */
    @Override 
    public void show(){
        // Set up the menu and switch screens when the player chooses to start.
        viewport = new FitViewport(8, 5);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setUseIntegerPositions(false);
        font.getData().setScale((viewport.getWorldHeight() / Gdx.graphics.getHeight())*1.5f);

    }

    /** Renders the text and counters onto the screen, and has a trigger for a game restart
     * @param delta
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        
        font.draw(batch, "You won the game!!!", 3.3f, 4f);
        font.draw(batch, "Items Used: " + itemsUsed, 3.3f, 3.5f);
        font.draw(batch, "Events Activated: " + eventsActivated, 3.3f, 3f);
        font.draw(batch, "Tap anywhere to restart the game: " , 3.3f, 2.5f);
        batch.end();

		if (Gdx.input.isTouched()) {
			parent.changeScreen(GooseGame.APPLICATION);
			dispose();
		}

    }

    private void input(){
    }

    private void logic(){
    }

    private void draw(){
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
        font.dispose();
    }
}