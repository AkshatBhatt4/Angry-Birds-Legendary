package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class KingPig extends GameCharacter {

    public KingPig(float x, float y, TextureRegion pigRegion) {
        super(x, y, 75, 92);
        this.currentFrame = pigRegion;
    }
}
