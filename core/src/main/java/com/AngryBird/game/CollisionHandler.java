package com.AngryBird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.physics.box2d.*;

public class CollisionHandler implements ContactListener {
    @Override
    public void beginContact(Contact contact) {
        try {
            Fixture fixtureA = contact.getFixtureA();
            Fixture fixtureB = contact.getFixtureB();

            if (fixtureA == null || fixtureB == null) return;

            Body bodyA = fixtureA.getBody();
            Body bodyB = fixtureB.getBody();

            if (bodyA == null || bodyB == null) return;

            Object dataA = bodyA.getUserData();
            Object dataB = bodyB.getUserData();

            // Check collision between RedBird and BasicPig
            if ((dataA instanceof RedBird && dataB instanceof BasicPig) ||
                (dataA instanceof BasicPig && dataB instanceof RedBird)) {

                BasicPig pig = (dataA instanceof BasicPig) ? (BasicPig) dataA : (BasicPig) dataB;

                if (pig != null) pig.takeDamage(1); // Damage the pig
            }

            // Check collision between RedBird and WoodStructure
            if ((dataA instanceof RedBird && dataB instanceof WoodStructure) ||
                (dataA instanceof WoodStructure && dataB instanceof RedBird)) {

                WoodStructure structure = (dataA instanceof WoodStructure)
                    ? (WoodStructure) dataA
                    : (WoodStructure) dataB;

                if (structure != null) structure.markForRemoval(); // Mark the structure for removal
            }
        } catch (Exception e) {
            Gdx.app.error("CollisionHandler", "Unexpected error in collision handling", e);
        }
    }




    @Override
    public void endContact(Contact contact) {
        // Not needed for this implementation
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        // Not needed for this implementation
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {
        // Not needed for this implementation
    }
    }
