package com.AngryBird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.joints.DistanceJoint;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;

public class BlueBird extends GameCharacter {
    private Body physicsBody;
    private World gameWorld;
    private Body anchorBody;
    private Body groundBody;

    private boolean isBeingDragged = false;
    private boolean isLaunched = false;
    private boolean isReadyForReuse = false;
    private Vector2 slingshotPosition;
    private float maxDragDistance = 1.0f;
    private float launchMultiplier = 0.8f;
    private int damage = 1;

    private ShapeRenderer trajectoryRenderer;
    private Vector2 initialPosition;

    public BlueBird(World world, float x, float y, TextureRegion texture) {
        super(x, y, 30, 30);
        this.gameWorld = world;
        this.currentFrame = texture;

        initialPosition = new Vector2(x / Structure.PhysicsConstants.PIXELS_TO_METERS,
            y / Structure.PhysicsConstants.PIXELS_TO_METERS);

        slingshotPosition = new Vector2(2f, 0.9f);
        initializePhysicsBody(initialPosition);


        physicsBody.setUserData(this);

        createGround();

        trajectoryRenderer = new ShapeRenderer();
    }

    public void reset() {
        if (physicsBody != null) {
            gameWorld.destroyBody(physicsBody);
        }
        initializePhysicsBody(initialPosition);
        isBeingDragged = false;
        isLaunched = false;
        isReadyForReuse = false;
    }

    private void createGround() {
        BodyDef groundDef = new BodyDef();
        groundDef.type = BodyDef.BodyType.StaticBody;
        groundDef.position.set(0, 0);
        groundBody = gameWorld.createBody(groundDef);

        PolygonShape groundShape = new PolygonShape();
        groundShape.setAsBox(20f, 0.5f);

        FixtureDef groundFixtureDef = new FixtureDef();
        groundFixtureDef.shape = groundShape;
        groundFixtureDef.friction = 0.5f;
        groundFixtureDef.restitution = 0.5f;

        groundBody.createFixture(groundFixtureDef);
        groundShape.dispose();
    }

    public boolean isOutOfPlay() {
        // Check if bird is far from play area or has stopped moving
        Vector2 position = physicsBody.getPosition();
        Vector2 velocity = physicsBody.getLinearVelocity();

        return (position.x > 20f || position.x < -2f ||
            position.y < -1f ||
            (Math.abs(velocity.x) < 0.1f && Math.abs(velocity.y) < 0.1f && isLaunched));
    }

    private void initializePhysicsBody(Vector2 startPosition) {
        BodyDef anchorDef = new BodyDef();
        anchorDef.type = BodyDef.BodyType.StaticBody;
        anchorDef.position.set(slingshotPosition);
        anchorBody = gameWorld.createBody(anchorDef);

        BodyDef birdDef = new BodyDef();
        birdDef.type = BodyDef.BodyType.KinematicBody;
        birdDef.position.set(startPosition);
        birdDef.fixedRotation = true;

        physicsBody = gameWorld.createBody(birdDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(bounds.width / 2 / Structure.PhysicsConstants.PIXELS_TO_METERS);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.8f;
        fixtureDef.friction = 0.3f;
        fixtureDef.restitution = 0.2f;

        physicsBody.createFixture(fixtureDef);
        shape.dispose();

        physicsBody.setLinearVelocity(0, 0);

        DistanceJointDef jointDef = new DistanceJointDef();
        jointDef.bodyA = anchorBody;
        jointDef.bodyB = physicsBody;
        jointDef.localAnchorA.set(0, 0);
        jointDef.localAnchorB.set(0, 0);
        jointDef.length = 0f;
        jointDef.dampingRatio = 1.0f;
        jointDef.frequencyHz = 0f;

        gameWorld.createJoint(jointDef);
    }

    public void update(Camera camera) {
        if (!isLaunched) {
            handleInput(camera);
        }
        updatePhysicsPosition();
    }

    private void handleInput(Camera camera) {
        Vector2 worldCoords = screenToWorld(camera, Gdx.input.getX(), Gdx.input.getY());

        if (Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
            if (!isBeingDragged && worldCoords.dst(slingshotPosition) < maxDragDistance) {
                isBeingDragged = true;
            }
            if (isBeingDragged) {
                Vector2 dragVector = worldCoords.cpy().sub(slingshotPosition);
                if (dragVector.len() > maxDragDistance) {
                    dragVector.setLength(maxDragDistance);
                }
                physicsBody.setTransform(slingshotPosition.cpy().add(dragVector), 0);
            }
        } else if (isBeingDragged) {
            launchBird();
            isBeingDragged = false;
            isLaunched = true;
        }
    }

    private void launchBird() {
        physicsBody.setType(BodyDef.BodyType.DynamicBody);

        for (JointEdge jointEdge : physicsBody.getJointList()) {
            if (jointEdge.joint instanceof DistanceJoint) {
                gameWorld.destroyJoint(jointEdge.joint);
                break;
            }
        }

        Vector2 launchVector = slingshotPosition.cpy().sub(physicsBody.getPosition());
        launchVector.scl(launchMultiplier);

        physicsBody.setAwake(true);
        physicsBody.applyLinearImpulse(launchVector, physicsBody.getWorldCenter(), true);
    }

    private void updatePhysicsPosition() {
        x = physicsBody.getPosition().x * Structure.PhysicsConstants.PIXELS_TO_METERS;
        y = physicsBody.getPosition().y * Structure.PhysicsConstants.PIXELS_TO_METERS;
        bounds.x = x;
        bounds.y = y;
    }

    private Vector2 screenToWorld(Camera camera, float screenX, float screenY) {
        Vector3 screenCoords = new Vector3(screenX, screenY, 0);
        Vector3 worldCoords = camera.unproject(screenCoords);
        return new Vector2(worldCoords.x, worldCoords.y).scl(1 / Structure.PhysicsConstants.PIXELS_TO_METERS);
    }

    public void render(SpriteBatch batch, Camera camera) {
        batch.draw(currentFrame,
            x - bounds.width / 2,
            y - bounds.height / 2,
            bounds.width, bounds.height);

        if (isBeingDragged) {
            renderTrajectory(camera);
        }
    }
    public void markForRemoval() {
        isReadyForReuse = true;
    }


    private void renderTrajectory(Camera camera) {
        trajectoryRenderer.setProjectionMatrix(camera.combined);
        trajectoryRenderer.begin(ShapeType.Line);
        trajectoryRenderer.setColor(0, 0, 1, 1);

        Vector2 launchVector = slingshotPosition.cpy().sub(physicsBody.getPosition()).scl(launchMultiplier);
        Vector2 velocity = launchVector.cpy();
        Vector2 currentPos = physicsBody.getPosition().cpy();

        float timeStep = 0.1f;
        for (int i = 0; i < 30; i++) {
            Vector2 nextPos = currentPos.cpy().add(velocity.cpy().scl(timeStep));
            trajectoryRenderer.line(
                currentPos.x * Structure.PhysicsConstants.PIXELS_TO_METERS,
                currentPos.y * Structure.PhysicsConstants.PIXELS_TO_METERS,
                nextPos.x * Structure.PhysicsConstants.PIXELS_TO_METERS,
                nextPos.y * Structure.PhysicsConstants.PIXELS_TO_METERS
            );

            currentPos.set(nextPos);
            velocity.y -= 9.8f * timeStep;
        }

        trajectoryRenderer.end();
    }


    public boolean isLaunched() {
        return isLaunched;
    }

    public Body getPhysicsBody() {
        return physicsBody;
    }

    public void dispose() {
        if (physicsBody != null && gameWorld != null) {
            gameWorld.destroyBody(physicsBody);
            physicsBody = null; // Prevent further interactions
        }
        if (trajectoryRenderer != null) {
            trajectoryRenderer.dispose();
            trajectoryRenderer = null; // Ensure it's not disposed again
        }
    }

}
