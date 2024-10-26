package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class WoodStructure extends Structure {
    private static final float WOOD_DURABILITY = 1.0f;

    public WoodStructure(float x, float y, float width, float height, TextureRegion region) {
        super(x, y, width, height, WOOD_DURABILITY);
        this.textureRegion = region;
    }
}
