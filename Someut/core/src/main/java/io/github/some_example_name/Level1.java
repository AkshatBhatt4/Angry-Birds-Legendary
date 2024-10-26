package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Level1 extends Level {
    public Level1(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg");
    }

    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        batch.draw(background, 0, 0, 800, 480);
    }

}
