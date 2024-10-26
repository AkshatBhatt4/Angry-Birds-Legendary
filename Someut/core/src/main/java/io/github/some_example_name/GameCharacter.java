package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

// Base class for game characters
abstract class GameCharacter {
    protected TextureRegion currentFrame;
    protected Rectangle bounds;
    protected float x, y;

    public GameCharacter(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, width, height);
    }

    public void render(SpriteBatch batch) {
        batch.draw(currentFrame, x, y, bounds.width, bounds.height);
    }
}
