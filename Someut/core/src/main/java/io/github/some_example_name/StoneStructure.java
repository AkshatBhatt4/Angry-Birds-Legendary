package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

class StoneStructure extends Structure {
    private static final float STEEL_DURABILITY = 2.0f;
    //private boolean isRusted;

    public StoneStructure(float x, float y, float width, float height, TextureRegion region) {
        super(x, y, width, height, STEEL_DURABILITY);
        this.textureRegion = region;
        //this.isRusted = false;
    }
}
