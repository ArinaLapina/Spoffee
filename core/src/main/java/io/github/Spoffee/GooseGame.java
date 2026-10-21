package io.github.Spoffee;

import com.badlogic.gdx.Game;

public class GooseGame extends Game {
    private MenuScreen menuScreen;
    private GameScreen gameScreen;
    private WinScreen winScreen;
    private LossScreen lossScreen;

    public final static int MENU = 0;
    public final static int APPLICATION = 1;
    public final static int WINGAME = 2;
    public final static int LOSSGAME = 3;

    @Override
    public void create(){
        changeScreen(GooseGame.APPLICATION);
    }

    public void changeScreen(int screen){
        switch(screen){
            case MENU :
                menuScreen = new MenuScreen(this);
                this.setScreen(menuScreen);
                break;

            case APPLICATION :
                gameScreen = new GameScreen(this);
                this.setScreen(gameScreen);

                break;
            
            case WINGAME :
                winScreen = new WinScreen(this);
                this.setScreen(winScreen);
                break;

            case LOSSGAME :
                lossScreen = new LossScreen(this);
                this.setScreen(lossScreen);
                break;
        }
    }
}