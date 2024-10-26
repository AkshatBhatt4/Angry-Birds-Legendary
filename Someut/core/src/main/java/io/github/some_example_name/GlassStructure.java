package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class GlassStructure extends Structure {
    private static final float GLASS_DURABILITY = 0.5f;

    public GlassStructure(float x, float y, float width, float height, TextureRegion region) {
        super(x, y, width, height, GLASS_DURABILITY);
        this.textureRegion = region;
    }

}
