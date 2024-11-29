package com.AngryBird.game;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

// Updated Structure abstract class to include Box2D physics
public abstract class Structure {
    protected TextureRegion textureRegion;
    protected Rectangle bounds;
    protected float x, y;
    protected float health;
    protected float durability;
    protected boolean isDestroyed;

    // Box2D related fields
    protected Body physicsBody;
    protected World gameWorld;
    protected BodyDef bodyDef;
    protected FixtureDef fixtureDef;

    private boolean markedForRemoval = false;

    // Constructor with Box2D World parameter
    public Structure(World world, float x, float y, float width, float height, float durability, TextureRegion texture) {
        this.gameWorld = world;
        this.x = x;
        this.y = y;
        this.bounds = new Rectangle(x, y, width, height);
        this.textureRegion = texture;
        this.durability = durability;
        this.health = 100f;
        this.isDestroyed = false;

        initializePhysicsBody(width, height);

        physicsBody.setUserData(this);
    }

    // Method to initialize Box2D physics body
    private void initializePhysicsBody(float width, float height) {
        // Create body definition
        bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody; // Most structures are static
        bodyDef.position.set((x + width/2) / PhysicsConstants.PIXELS_TO_METERS,
            (y + height/2) / PhysicsConstants.PIXELS_TO_METERS);

        // Create the body in the world
        physicsBody = gameWorld.createBody(bodyDef);

        // Create shape for the fixture
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width/2 / PhysicsConstants.PIXELS_TO_METERS,
            height/2 / PhysicsConstants.PIXELS_TO_METERS);

        // Create fixture definition
        fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = getDensity();
        fixtureDef.friction = getFriction();
        fixtureDef.restitution = getRestitution();

        // Create fixture and attach to body
        physicsBody.createFixture(fixtureDef);

        // Clean up
        shape.dispose();
    }

    public void dispose() {
        if (physicsBody != null) {
            gameWorld.destroyBody(physicsBody);
            physicsBody = null;
        }
    }

    public Body getPhysicsBody() {
        return physicsBody;
    }

    // Abstract methods to define material properties
    protected abstract float getDensity();
    protected abstract float getFriction();
    protected abstract float getRestitution();

    public void render(SpriteBatch batch) {
        if (!isDestroyed) {
            // Update position from physics body
            x = physicsBody.getPosition().x * PhysicsConstants.PIXELS_TO_METERS - bounds.width/2;
            y = physicsBody.getPosition().y * PhysicsConstants.PIXELS_TO_METERS - bounds.height/2;

            batch.draw(textureRegion, x, y, bounds.width, bounds.height);
        }
    }

    // Utility class for physics constants
    public static class PhysicsConstants {
        public static final float PIXELS_TO_METERS = 100f;
    }

    public void takeDamage(int damage) {
        markedForRemoval = true;
    }
    public boolean isMarkedForRemoval() {
        return markedForRemoval;
    }
}

// Concrete implementation for Wood Structures

