package io.github.Spoffee;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MenuScreen implements Screen{
    private final GooseGame parent;
    private FitViewport viewport;

    public MenuScreen(GooseGame parentClass){
        parent = parentClass;
    }

    @Override 
    public void show(){
        // Set up the menu and switch screens when the player chooses to start.
        viewport = new FitViewport(8, 5);
        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
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
    }
}