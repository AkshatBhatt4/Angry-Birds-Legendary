package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class BlackBird extends GameCharacter {
    public BlackBird(float x, float y, TextureRegion region) {
        super(x, y, 50, 65);
        this.currentFrame = region;
    }
}
