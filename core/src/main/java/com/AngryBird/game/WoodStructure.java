package com.AngryBird.game;

import com.AngryBird.game.Structure;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class WoodStructure extends Structure {


    private boolean markedForRemoval = false;


    public WoodStructure(World world, float x, float y, float width, float height, TextureRegion texture) {
        super(world, x, y, width, height, 50f, texture);
    }

    @Override
    protected float getDensity() {
        return 1f; // Lighter material
    }

    @Override
    protected float getFriction() {
        return 1f;
    }

    @Override
    protected float getRestitution() {
        return 0.0f; // Low bounciness
    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }


}
