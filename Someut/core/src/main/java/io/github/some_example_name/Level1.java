package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Level1 extends Level {
    Texture block;
    public Level1(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg");
        block= new Texture("Blocks.png");
    }

    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        batch.draw(background, 0, 0, 800, 480);
        batch.draw(new TextureRegion(block,0,239,79,80),100,100);
    }

}
