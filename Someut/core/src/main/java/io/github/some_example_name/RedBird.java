package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class RedBird extends GameCharacter {
    public RedBird(float x, float y, TextureRegion region) {
        super(x, y, 40, 40);
        this.currentFrame = region;
    }
}

