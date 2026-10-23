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
        changeScreen(GooseGame.MENU);
    }

    /** Creates the screen, and starts the code for the main menu and game window
     * @param screen
     */
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
                winScreen = new WinScreen(this, 0, 0);
                this.setScreen(winScreen);
                break;

            case LOSSGAME :
                lossScreen = new LossScreen(this, 0, 0);
                this.setScreen(lossScreen);
                break;
        }
    }

    /** When an endgame screen is required, this creates either the win or loss screen and supplies it
     * with the counters for items and events
     * @param screen
     * @param player
     */
    public void changeScreen(int screen, Player player) {
        if (screen == WINGAME) {
            winScreen = new WinScreen(this, player.getItemUsedCounter(),
                    player.getEventActiveCounter());
            this.setScreen(winScreen);
        } else if (screen == LOSSGAME) {
            lossScreen = new LossScreen(this, player.getItemUsedCounter(),
                    player.getEventActiveCounter());
            this.setScreen(lossScreen);
        } else {
            changeScreen(screen);
        }
    }
}
