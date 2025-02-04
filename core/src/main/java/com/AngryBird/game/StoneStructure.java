package com.AngryBird.game;

import com.AngryBird.game.Structure;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class StoneStructure extends Structure {
    private boolean markedForRemoval = false;

    public StoneStructure(World world, float x, float y, float width, float height, TextureRegion texture) {
        super(world, x, y, width, height, 80f, texture);
    }

    @Override
    protected float getDensity() {
        return 1.0f; // Heavy material
    }

    @Override
    protected float getFriction() {
        return 0.8f;
    }

    @Override
    protected float getRestitution() {
        return 0.0f;
    }

    public void markForRemoval() {
        markedForRemoval = true;
    }

    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

}
