package io.github.Spoffee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen{
    private final GooseGame parent;
    private FitViewport viewport;
    private Texture backgroundImage;
    private SpriteBatch batch;

    public MenuScreen(GooseGame parentClass){
        parent = parentClass;
    }

    /** Initialises the screen window, and the background image ready to display
     * @param delta
     */
    @Override 
    public void show(){
        viewport = new FitViewport(8, 5);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

        batch = new SpriteBatch();
        
        backgroundImage = new Texture("MenuScreenBackground.png");

    }

    /** Renders the image of the screen, and checks if the trigger for the game start has been activated
     * @param delta
     */
    @Override
    public void render(float delta) {
        ScreenUtils.clear(Color.BLACK);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        
        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());

        batch.end();

		if (Gdx.input.isTouched()) {
			parent.changeScreen(GooseGame.APPLICATION);
			dispose();
		}

       
    }

    private void input(){
        //When the button is clicked trigger this line of code: 
            //parent.changeScreen(GooseGame.APPLICATION);
    }

    private void logic(){
    }

    private void draw(){
        //Place a button that when clicked starts the game
    }

    /** 
     * @param width
     * @param height
     */
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
    }
}