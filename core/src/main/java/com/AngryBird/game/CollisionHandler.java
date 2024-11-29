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

            // Check collision between BlueBird and ArmoredPig
            if ((dataA instanceof BlueBird && dataB instanceof ArmoredPig) ||
                (dataA instanceof ArmoredPig && dataB instanceof BlueBird)) {

                ArmoredPig pig = (dataA instanceof ArmoredPig) ? (ArmoredPig) dataA : (ArmoredPig) dataB;
                BlueBird bird = (dataA instanceof BlueBird) ? (BlueBird) dataA : (BlueBird) dataB;

                if (pig != null) pig.takeDamage(1); // Damage the pig
                if (bird != null) bird.markForRemoval(); // Mark bird for removal
            }

            // Check collision between BlueBird and GlassStructure
            if ((dataA instanceof BlueBird && dataB instanceof GlassStructure) ||
                (dataA instanceof GlassStructure && dataB instanceof BlueBird)) {

                GlassStructure structure = (dataA instanceof GlassStructure)
                    ? (GlassStructure) dataA
                    : (GlassStructure) dataB;
                BlueBird bird = (dataA instanceof BlueBird) ? (BlueBird) dataA : (BlueBird) dataB;

                if (structure != null) structure.markForRemoval(); // Mark the structure for removal
                if (bird != null) bird.markForRemoval(); // Mark bird for removal
            }

            // Check collision between Blackbird and Basicpig
            if ((dataA instanceof BlackBird && dataB instanceof BasicPig) ||
                (dataA instanceof BasicPig && dataB instanceof BlackBird)) {

                BasicPig pig = (dataA instanceof BasicPig) ? (BasicPig) dataA : (BasicPig) dataB;
                BlackBird bird = (dataA instanceof BlackBird) ? (BlackBird) dataA : (BlackBird) dataB;

                if (pig != null) pig.takeDamage(2); // Double damage
                if (bird != null) bird.markForRemoval(); // Mark bird for removal
            }


            // Check collision between Blackbird and Kingpig
            if ((dataA instanceof BlackBird && dataB instanceof KingPig) ||
                (dataA instanceof KingPig && dataB instanceof BlackBird)) {

                KingPig kingPig = (dataA instanceof KingPig) ? (KingPig) dataA : (KingPig) dataB;
                BlackBird bird = (dataA instanceof BlackBird) ? (BlackBird) dataA : (BlackBird) dataB;

                if (kingPig != null) {
                    kingPig.takeDamage(3); // Higher damage
                    if (kingPig.isMarkedForRemoval()) kingPig.isMarkedForRemoval();
                }
                if (bird != null) bird.markForRemoval();
            }

            // Black Bird with Wood Structure
            if ((dataA instanceof BlackBird && dataB instanceof WoodStructure) ||
                (dataA instanceof WoodStructure && dataB instanceof BlackBird)) {

                WoodStructure structure = (dataA instanceof WoodStructure)
                    ? (WoodStructure) dataA
                    : (WoodStructure) dataB;
                BlackBird bird = (dataA instanceof BlackBird) ? (BlackBird) dataA : (BlackBird) dataB;

                if (structure != null) structure.markForRemoval(); // Mark the structure for removal
                if (bird != null) bird.markForRemoval(); // Mark bird for removal
            }

            // Black Bird with Stone Structure
            if ((dataA instanceof BlackBird && dataB instanceof StoneStructure) ||
                (dataA instanceof StoneStructure && dataB instanceof BlackBird)) {

                StoneStructure structure = (dataA instanceof StoneStructure)
                    ? (StoneStructure) dataA
                    : (StoneStructure) dataB;
                BlackBird bird = (dataA instanceof BlackBird) ? (BlackBird) dataA : (BlackBird) dataB;

                if (structure != null) structure.markForRemoval(); // Mark the structure for removal
                if (bird != null) bird.markForRemoval(); // Mark bird for removal
            }

        } catch (Exception e) {
            Gdx.app.error("CollisionHandler", "Unexpected error in collision handling", e);
        }
    }

    @Override
    public void endContact(Contact contact) {
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
