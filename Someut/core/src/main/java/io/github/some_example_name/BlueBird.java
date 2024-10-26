package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class BlueBird extends GameCharacter {
    public BlueBird(float x, float y, TextureRegion region) {
        super(x, y, 30, 30);
        this.currentFrame = region;
    }
}
