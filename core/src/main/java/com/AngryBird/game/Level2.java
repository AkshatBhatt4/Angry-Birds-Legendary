package com.AngryBird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

public class Level2 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private BlueBird activeBird;
    private Array<BlueBird> availableBirds;
    private Array<Body> bodiesToRemove = new Array<>();
    private Array<Body> bodiesMarkedForRemoval = new Array<>();

    // Box2D World
    private World physicsWorld;

    public Level2(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg", 2);

        // Initialize Box2D World with gravity
        physicsWorld = new World(new Vector2(0, -9.8f), true);
        physicsWorld.setContactListener(new CollisionHandler());

        // Initialize texture manager with sprite sheet
        textureManager = new TextureManager();

        structures = new Array<>();
        characters = new Array<>();
        availableBirds = new Array<>();

        initializeLevel();
    }

    private void initializeLevel() {
        // Create structures
        createStructures();

        // Create characters (pigs)
        createPigs();

        // Create birds
        createBirds();
    }

    private void createStructures() {
        // First Tower
        structures.add(new StoneStructure(
            physicsWorld,
            tower1X+220,
            ground,
            20,
            160,
            textureManager.getStoneRegion()
        ));

        // Second tower
        float tower2X = tower1X-20;
        structures.add(new GlassStructure(
            physicsWorld,
            tower2X,
            ground,
            200,
            75,
            textureManager.getGlassRegion()
        ));
        structures.add(new GlassStructure(
            physicsWorld,
            tower2X,
            ground + 75,
            10,
            30,
            textureManager.getGlassRegion()
        ));
        structures.add(new GlassStructure(
            physicsWorld,
            tower2X + 190,
            ground + 75,
            10,
            30,
            textureManager.getGlassRegion()
        ));
    }

    private void createPigs() {
        float tower2X = tower1X;

        // Add pigs
        characters.add(new ArmoredPig(physicsWorld,
            tower2X + 15,
            ground + 75,
            textureManager.getArmoredPigRegion()
        ));
        characters.add(new ArmoredPig(physicsWorld,
            tower2X + 75,
            ground + 75,
            textureManager.getArmoredPigRegion()
        ));
        characters.add(new ArmoredPig(physicsWorld,
            tower2X + 135,
            ground + 75,
            textureManager.getArmoredPigRegion()
        ));
    }

    private void createBirds() {
        float birdStartX = 120;
        float birdStartY = ground;

        // Create the active bird (the one ready to launch)
        activeBird = new BlueBird(
            physicsWorld,
            birdStartX,
            birdStartY,
            textureManager.getBlueBirdRegion()
        );

        // If BlackBird extends BlueBird, this would work
        availableBirds.add(new BlueBird(
            physicsWorld,
            70,
            birdStartY,
            textureManager.getBlueBirdRegion()
        ));
        availableBirds.add(new BlueBird(
            physicsWorld,
            30,
            birdStartY,
            textureManager.getBlueBirdRegion()
        ));
    }


    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        // Update physics world
        physicsWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // Draw background
        batch.draw(background, 0, 0, 800, 480);

        // Draw structures
        for (Structure structure : structures) {
            structure.render(batch);
        }

        // Draw characters (pigs)
        for (GameCharacter character : characters) {
            character.render(batch);
        }

        // Manage active bird
        if (activeBird != null) {
            activeBird.update(camera);
            activeBird.render(batch);

            // Check if bird is launched and out of play
            if (activeBird.isLaunched() && activeBird.isOutOfPlay()) {
                // Dispose of the current bird
                activeBird.dispose();

                // Get the next bird from the queue if available
                if (!availableBirds.isEmpty()) {
                    activeBird = availableBirds.removeIndex(0);
                } else {
                    activeBird = null; // No more birds
                }
            }
        }

        for (int i = structures.size - 1; i >= 0; i--) {
            Structure structure = structures.get(i);
            if (structure.isMarkedForRemoval()) {
                bodiesToRemove.add(structure.getPhysicsBody());
                structures.removeIndex(i);
            } else {
                structure.render(batch);
            }
        }

        // Draw characters (pigs)
        for (int i = characters.size - 1; i >= 0; i--) {
            GameCharacter character = characters.get(i);
            if (character instanceof ArmoredPig) {
                ArmoredPig pig = (ArmoredPig) character;
                if (pig.isMarkedForRemoval()) {
                    bodiesToRemove.add(pig.getPhysicsBody());
                    characters.removeIndex(i);
                    pig.dispose();
                } else {
                    pig.render(batch);
                }
            }
        }

        if (characters.isEmpty() && endScreenTimer == -1) {
            endScreenTimer = 0; // Start the timer
        }

        if (endScreenTimer >= 0) {
            endScreenTimer += Gdx.graphics.getDeltaTime();
            if (endScreenTimer >= END_SCREEN_DELAY) {
                hasWon =true;
                endScreenTimer = -1; // Reset the timer
            }
        }

        for (BlueBird bird : availableBirds) {
            bird.render(batch);
        }

        // Destroy bodies after simulation step
        for (Body body : bodiesToRemove) {
            physicsWorld.destroyBody(body);
        }
        bodiesToRemove.clear();

        // Draw available birds
        for (BlueBird bird : availableBirds) {
            bird.render(batch);
        }
        if (activeBird == null && endScreenTimer == -1) {
            endScreenTimer = 0; // Start the timer
        }

        if (endScreenTimer >= 0) {
            endScreenTimer += Gdx.graphics.getDeltaTime();
            if (endScreenTimer >= END_SCREEN_DELAY) {
                hasLost =true;
                endScreenTimer = -1; // Reset the timer
            }
        }
    }


}
