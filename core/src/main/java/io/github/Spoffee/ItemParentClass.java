package io.github.Spoffee;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;

public abstract class ItemParentClass{
    private Texture itemTexture;
    private boolean itemUsed = false;
    private int geeseDispered;
    private Timer timerForItem;

    public ItemParentClass(String filePath, int geeseDisperalNum){
        itemTexture = new Texture(filePath);
        geeseDispered = geeseDisperalNum;
    }

    public Texture getTexture(){
        return itemTexture;
    }

    public boolean  getItemActivationStatus(){
        return itemUsed;
    }

    public void setItemActivationStatus(boolean varToSet){
        itemUsed = varToSet;
    }

    public int getGeeseDispered(){
        return geeseDispered;
    }
    
    public Timer getTimer(){
        return timerForItem;
    }

    protected void markAsUsed(Player userSprite){
        itemUsed = true;
        userSprite.increaseItemUsedCounter();
    }

    protected void setTimer(Timer timer){
        timerForItem = timer;
    }

    public abstract void useItem(Player userSprite, ArrayList<Goose> activeGeese);

    public abstract void itemLogic(Player userSprite);

}

class EnergyDrink extends ItemParentClass{
    private boolean speedRestored = false;
    public EnergyDrink(String filePath, int geeseDisperalNum){
        super(filePath, geeseDisperalNum);
    }

    @Override
    public void useItem(Player userSprite, ArrayList<Goose> activeGeese){
        if (getItemActivationStatus()){
            return;
        }

        setTimer(new Timer(10000));
        markAsUsed(userSprite);
        speedRestored = false;
        userSprite.setSpeed(1f);
    }

    @Override
    public void itemLogic(Player userSprite) {
        if (!speedRestored && this.getTimer().toString().equals("0:00")){
            userSprite.setSpeed(0.5f);
            speedRestored = true;
        }
    }
}

class Foghorn extends ItemParentClass{
    public Foghorn(String filePath, int geeseDisperalNum){
        super(filePath, geeseDisperalNum);
    }

    @Override
    public void useItem(Player userSprite, ArrayList<Goose> activeGeese){
        if (getItemActivationStatus()){
            return;
        }

        markAsUsed(userSprite);
        int geeseToDisperse = Math.min(getGeeseDispered(), activeGeese.size());
        int geeseRemoved = 0;

        while (geeseRemoved != geeseToDisperse){
            int randomIndex = (int) (Math.random() * activeGeese.size());

            if (activeGeese.get(randomIndex).getActiveStatus()){
                activeGeese.get(randomIndex).disperseGoose();
                geeseRemoved += 1;
            }
        }
    }

    @Override
    public void itemLogic(Player userSprite) {
        
    }
}