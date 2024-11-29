package com.AngryBird.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class BasicPig extends GameCharacter {
    // Physics body
    private Body physicsBody;
    private World gameWorld;
    private int health = 1;
    private boolean markedForRemoval = false;


    public BasicPig(World world, float x, float y, TextureRegion region) {
        super(x, y, 50, 50);
        this.gameWorld = world;
        this.currentFrame = region;
        this.health = 1; // Explicitly set initial health to 1

        initializePhysicsBody(x, y);

        physicsBody.setUserData(this);
    }

    public void takeDamage(int damage) {
        health -= damage;
        if (health <= 0) {
            markedForRemoval = true; // Mark for removal instead of directly disposing
        }
    }


    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }

    private void initializePhysicsBody(float x, float y) {
        // Create body definition
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        // Convert pixel coordinates to Box2D meters
        bodyDef.position.set(
            x / Structure.PhysicsConstants.PIXELS_TO_METERS,
            y / Structure.PhysicsConstants.PIXELS_TO_METERS
        );

        // Create body in the world
        physicsBody = gameWorld.createBody(bodyDef);

        // Create shape for the pig
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(
            bounds.width / 2 / Structure.PhysicsConstants.PIXELS_TO_METERS,
            bounds.height / 2 / Structure.PhysicsConstants.PIXELS_TO_METERS
        );

        // Create fixture definition
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;

        // Add fixture to body
        physicsBody.createFixture(fixtureDef);

        // Dispose of shape
        shape.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
        // Only render if the physics body exists
        if (physicsBody != null) {
            // Update position based on physics body
            x = physicsBody.getPosition().x * Structure.PhysicsConstants.PIXELS_TO_METERS;
            y = physicsBody.getPosition().y * Structure.PhysicsConstants.PIXELS_TO_METERS;

            // Render at the physics body's position
            batch.draw(currentFrame,
                x - bounds.width / 2,
                y - bounds.height / 2,
                bounds.width,
                bounds.height
            );
        }
    }

    public Body getPhysicsBody() {
        return physicsBody;
    }

    public void dispose() {
        if (gameWorld != null && physicsBody != null) {
            gameWorld.destroyBody(physicsBody);
            physicsBody = null; // Explicitly set to null to indicate disposal
        }
    }
}
