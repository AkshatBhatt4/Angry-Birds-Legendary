package com.AngryBird.game;// Concrete implementation for Glass Structures

import com.AngryBird.game.Structure;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.World;

public class GlassStructure extends Structure {
    public GlassStructure(World world, float x, float y, float width, float height, TextureRegion texture) {
        super(world, x, y, width, height, 30f, texture);
    }

    @Override
    protected float getDensity() {
        return 0.3f; // Very light
    }

    @Override
    protected float getFriction() {
        return 0.5f;
    }

    @Override
    protected float getRestitution() {
        return 0.0f;
    }
}
