package com.AngryBird.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.*;

public class ArmoredPig extends GameCharacter {
    // Physics body
    private Body physicsBody;
    private World gameWorld;

    public ArmoredPig(World world, float x, float y, TextureRegion region) {
        super(x, y, 55, 55);
        this.gameWorld = world;
        this.currentFrame = region;

        initializePhysicsBody(x, y);
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
        fixtureDef.density = 0.7f; // Slightly heavier due to armor
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f; // Less bouncy

        // Add fixture to body
        physicsBody.createFixture(fixtureDef);

        // Dispose of shape
        shape.dispose();
    }

    @Override
    public void render(SpriteBatch batch) {
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

    public Body getPhysicsBody() {
        return physicsBody;
    }

    public void dispose() {
        // Remove the physics body from the world
        if (gameWorld != null && physicsBody != null) {
            gameWorld.destroyBody(physicsBody);
        }
    }
}
