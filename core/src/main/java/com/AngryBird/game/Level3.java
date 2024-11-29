package com.AngryBird.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;

public class Level3 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private BlackBird activeBird;
    //private Array<GameCharacter> availableBirds;
    private Array<BlackBird> availableBirds;

    // Box2D World
    private World physicsWorld;

    public Level3(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg", 3);

        // Initialize Box2D World with gravity
        physicsWorld = new World(new Vector2(0, -9.8f), true);

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
        structures.add(new WoodStructure(
            physicsWorld,
            tower1X - 100,
            ground,
            20,
            60,
            textureManager.getWoodRegion()
        ));
        structures.add(new WoodStructure(
            physicsWorld,
            tower1X - 30,
            ground,
            20,
            60,
            textureManager.getWoodRegion()
        ));
//        structures.add(new WoodStructure(
//            physicsWorld,
//            tower1X - 10,
//            ground,
//            20,
//            70,
//            textureManager.getWoodRegion()
//        ));
//
//        // Second tower
        float tower2X = tower1X + 150;
        structures.add(new WoodStructure(
            physicsWorld,
            tower2X,
            ground,
            20,
            60,
            textureManager.getWoodRegion()
        ));
        structures.add(new WoodStructure(
            physicsWorld,
            tower2X + 70,
            ground,
            20,
            60,
            textureManager.getWoodRegion()
        ));

        // First stone tower
        float tower3X = tower1X+10;
        structures.add(new StoneStructure(
            physicsWorld,
            tower3X,
            ground,
            30,
            140,
            textureManager.getStoneRegion()
        ));
        structures.add(new StoneStructure(
            physicsWorld,
            tower3X+100,
            ground,
            30,
            140,
            textureManager.getStoneRegion()
        ));
//        structures.add(new StoneStructure(
//            physicsWorld,
//            tower3X-40,
//            ground,
//            20,
//            140,
//            textureManager.getStoneRegion()
//        ));

//        structures.add(new GlassStructure(
//            physicsWorld,
//            tower3X - 50,
//            ground + 160,
//            20,
//            100,
//            textureManager.getGlassRegion()
//        ));
//        structures.add(new GlassStructure(
//            physicsWorld,
//            tower3X + 50,
//            ground + 160,
//            20,
//            100,
//            textureManager.getGlassRegion()
//        ));

        // Horizontal platforms
        structures.add(new WoodStructure(
            physicsWorld,
            tower1X - 100,
            ground + 61,
            90,
            20,
            textureManager.getWoodRegion()
        ));
        structures.add(new WoodStructure(
            physicsWorld,
            tower2X,
            ground + 61,
            90,
            20,
            textureManager.getWoodRegion()
        ));

        structures.add(new StoneStructure(
            physicsWorld,
            tower3X,
            ground + 140,
            130,
            20,
            textureManager.getStoneRegion()
        ));
//        structures.add(new GlassStructure(
//            physicsWorld,
//            tower3X - 50,
//            ground + 260,
//            120,
//            20,
//            textureManager.getGlassRegion()
//        ));
    }

    private void createPigs() {
        float tower2X = tower1X + 150;
        float tower3X = tower1X + 10 ;

        // Add pigs
        characters.add(new BasicPig(physicsWorld,
            tower1X -55,
            ground + 81 ,
            textureManager.getBasicPigRegion()
        ));
        characters.add(new BasicPig(physicsWorld,
            tower2X +45,
            ground + 81 ,
            textureManager.getBasicPigRegion()
        ));
        characters.add(new KingPig(physicsWorld,
            tower3X +65,
            ground + 160+2,
            textureManager.getKingPigRegion()
        ));
    }

    private void createBirds() {
        float birdStartX = 125;
        float birdStartY = ground;

        // Create the active bird (the one ready to launch)
        activeBird = new BlackBird(
            physicsWorld,
            birdStartX,
            birdStartY,
            textureManager.getBlackBirdRegion()
        );

        // Add available birds (waiting to be used)
        availableBirds.add(new BlackBird(
            physicsWorld,
            75,
            birdStartY,
            textureManager.getBlackBirdRegion()
        ));
        availableBirds.add(new BlackBird(
            physicsWorld,
            25,
            birdStartY,
            textureManager.getBlackBirdRegion()
        ));
    }

    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        // Update physics world
        physicsWorld.step(Gdx.graphics.getDeltaTime(), 6, 2);

        // Draw background
        batch.draw(background, 0, 0, 800, 480);

        // Manage active bird
        if (activeBird != null) {
            activeBird.update(camera);
            activeBird.render(batch);

            // Check if bird is out of play
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

        // Draw structures
        for (Structure structure : structures) {
            structure.render(batch);
        }

        // Draw characters (pigs)
        for (GameCharacter character : characters) {
            character.render(batch);
        }

        // Draw available birds
        for (BlackBird bird : availableBirds) {
            bird.render(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        textureManager.dispose();
        physicsWorld.dispose();

        // Dispose of birds
        if (activeBird != null) {
            activeBird.dispose();
        }
        for (BlackBird bird : availableBirds) {
            bird.dispose();
        }
    }

}
