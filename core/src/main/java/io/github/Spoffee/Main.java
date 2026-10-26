package io.github.Spoffee;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture backgroundImage;
    private Texture spriteImage;
    private Sprite userSprite;
    private FitViewport viewport;

    @Override
    public void create() {
        batch = new SpriteBatch();

        backgroundImage = new Texture("background.png");

        spriteImage = new Texture("person.png");

        userSprite = new Sprite(spriteImage);
        userSprite.setSize(0.75f, 1f);
        userSprite.setPosition(0, 0);

        viewport = new FitViewport(8, 5);

        DisplayMode displayMode = Gdx.graphics.getDisplayMode();
        Gdx.graphics.setWindowedMode(displayMode.width, displayMode.height);

    }

    @Override
    public void render() {
        logic();
        input();
        draw();
    }

    @Override
    public void dispose() {
        batch.dispose();
        backgroundImage.dispose();
        spriteImage.dispose();
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    public void input() {
        float speed = 1f;
        float delta = Gdx.graphics.getDeltaTime();

        // Movement input
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
            userSprite.translateX(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
            userSprite.translateX(-speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
            userSprite.translateY(speed * delta);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
            userSprite.translateY(-speed * delta);
        }
    }

    public void logic() {
        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float spriteWidth = userSprite.getWidth();
        float spriteHeight = userSprite.getHeight();

        userSprite.setX(MathUtils.clamp(userSprite.getX(), 0, worldWidth - spriteWidth));
        userSprite.setY(MathUtils.clamp(userSprite.getY(), 0, worldHeight - spriteHeight));

    }

    public void draw() {
        ScreenUtils.clear(Color.BLACK);
        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);


        batch.begin();

        batch.draw(backgroundImage, 0, 0, viewport.getWorldWidth(), viewport.getWorldHeight());
        userSprite.draw(batch);

        batch.end();
    }
}
