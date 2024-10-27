package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

abstract class Structure {
    protected TextureRegion textureRegion;
    protected Rectangle bounds;
    protected float x, y;
    protected float health;
    protected float durability;
    protected boolean isDestroyed;

    public Structure(float x, float y, float width, float height, float durability) {
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, width, height);
        this.durability = durability;
        this.health = 100f;
        this.isDestroyed = false;
    }

    public void render(SpriteBatch batch) {
        if (!isDestroyed) {
            batch.draw(textureRegion, x, y, bounds.width, bounds.height);
        }
    }

}
