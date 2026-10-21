package io.github.Spoffee;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class WinScreen implements Screen{
    private final GooseGame parent;
    private FitViewport viewport;

    public WinScreen(GooseGame parentClass){
        parent = parentClass;
    }

    @Override 
    public void show(){
        //Tells the player if they've won or lost and the counters and time
        //Also displays a button that when they click on it, it restarts the game
        
        parent.changeScreen(GooseGame.APPLICATION);
    }

    @Override
    public void render(float delta) {
        input();
        logic();
        draw();
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
    }
}