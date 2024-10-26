package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class ArmoredPig extends GameCharacter {
    public ArmoredPig(float x, float y, TextureRegion region) {
        super(x, y, 55, 55);
        this.currentFrame = region;
    }
}
