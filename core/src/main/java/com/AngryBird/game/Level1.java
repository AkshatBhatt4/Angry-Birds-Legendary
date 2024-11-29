package com.AngryBird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;

public class Level1 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private RedBird activeBird;
    private Array<RedBird> availableBirds;
    private Array<Body> bodiesToRemove = new Array<>();
    private float endScreenTimer = -1; 
    private final float END_SCREEN_DELAY = 4.0f; // 4 seconds delay

    private World physicsWorld;

    public Level1(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg", 1);

        // Initialize Box2D World with gravity
        physicsWorld = new World(new Vector2(0, -9.8f), true);
        physicsWorld.setContactListener(new CollisionHandler());

        textureManager = new TextureManager();
        structures = new Array<>();
        characters = new Array<>();
        availableBirds = new Array<>();

        initializeLevel();
    }

    private void initializeLevel() {
        createStructures();
        createPigs();
        createBirds();
    }

    private void createStructures() {
        structures.add(new WoodStructure(physicsWorld,tower1X,ground,20,70,textureManager.getWoodRegion()));
        structures.add(new WoodStructure(physicsWorld,tower1X,ground+70+20,20,50,textureManager.getWoodRegion()));

        float tower2X = 640;
        structures.add(new WoodStructure(physicsWorld,tower2X,ground,20,70,textureManager.getWoodRegion()));
        structures.add(new WoodStructure(physicsWorld,tower2X,ground+70+20,20,50,textureManager.getWoodRegion()));

        // Horizontal platforms
        structures.add(new WoodStructure(physicsWorld,tower1X - 20,ground+70,150,20,textureManager.getWoodRegion()));
    }

    private void createPigs() {
        float tower2X = 640;
        characters.add(new BasicPig(physicsWorld,(tower1X + tower2X + 20-48)/2,ground+88,textureManager.getBasicPigRegion()));
        characters.add(new BasicPig(physicsWorld,(tower1X + tower2X + 20-48)/2,ground,textureManager.getBasicPigRegion()));
    }

    private void createBirds() {
        activeBird = new RedBird(physicsWorld,130,ground,textureManager.getRedBirdRegion());

        availableBirds.add(new RedBird(physicsWorld,70,ground,textureManager.getRedBirdRegion()));
        availableBirds.add(new RedBird(physicsWorld,30,ground,textureManager.getRedBirdRegion()));
    }

    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        physicsWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //System.out.println("Active Bodies: " + physicsWorld.getBodyCount());

        for (Body body : bodiesToRemove) {
            physicsWorld.destroyBody(body);
        }
        bodiesToRemove.clear();
        batch.draw(background, 0, 0, 800, 480);
        if (activeBird != null) {
            activeBird.update(camera);
            activeBird.render(batch);
            if (activeBird.isLaunched() && (activeBird.getPhysicsBody().getPosition().x > 10 || activeBird.getPhysicsBody().getPosition().y < 0)) {
                if (!availableBirds.isEmpty()) {
                    activeBird = availableBirds.removeIndex(0);
                }
                else {
                    activeBird = null;
                }
            }
        }
        // Draw structures
        for (Structure structure : structures) {
            structure.render(batch);
        }
        // Draw characters (pigs)
        for (GameCharacter character : characters) {
            character.render(batch);
        }

        for (int i = characters.size - 1; i >= 0; i--) {
            GameCharacter character = characters.get(i);
            if (character instanceof BasicPig) {
                BasicPig pig = (BasicPig) character;
                if (pig.isMarkedForRemoval()) {
                    bodiesToRemove.add(pig.getPhysicsBody()); // Add to removal list
                    characters.removeIndex(i); // Remove from the game list
                }
            }
        }
        //if(characters.isEmpty()) hasWon=true;
        for (int i = structures.size - 1; i >= 0; i--) {
            Structure structure = structures.get(i);
            if (structure.isMarkedForRemoval()) {
                bodiesToRemove.add(structure.getPhysicsBody()); // Schedule Box2D body removal
                structures.removeIndex(i);                      // Remove from game list
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

        // Draw available birds
        for (RedBird bird : availableBirds) {
            bird.render(batch);
        }

        if (activeBird != null && activeBird.isLaunched() && activeBird.isOutOfPlay()) {
            activeBird.dispose();
            if (!availableBirds.isEmpty()) {
                activeBird = availableBirds.removeIndex(0);
            } else {
                activeBird = null; // No more birds available
            }
        }

        // Update the current bird if it exists
        if (activeBird != null) {
            activeBird.update(camera);
        }
        if (activeBird == null && endScreenTimer == -1) {
            endScreenTimer = 0; // Start the timer
        }

        if (endScreenTimer >= 0) {
            endScreenTimer += Gdx.graphics.getDeltaTime();
            if (endScreenTimer >= END_SCREEN_DELAY) {
                haslost =true;
                endScreenTimer = -1; // Reset the timer
            }
        }
        //if(activeBird==null) haslost=true;
    }

    @Override
    public void dispose() {
        super.dispose();
        textureManager.dispose();
        physicsWorld.dispose();
        if (activeBird != null) {
            activeBird.dispose();
        }
        for (RedBird bird : availableBirds) {
            bird.dispose();
        }
    }
}
