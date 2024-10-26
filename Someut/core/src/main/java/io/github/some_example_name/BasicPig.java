package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class BasicPig extends GameCharacter {
    public BasicPig(float x, float y, TextureRegion region) {
        super(x, y, 50, 50);
        this.currentFrame = region;
    }
}
