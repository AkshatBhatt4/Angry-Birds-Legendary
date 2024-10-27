package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Level1 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private RedBird activeBird;
    private Array<GameCharacter> availableBirds;

    public Level1(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg",1);

        // Initialize texture manager with sprite sheet
        textureManager = new TextureManager();

        structures = new Array<>();
        characters = new Array<>();
        availableBirds = new Array<>();

        initializeLevel();
    }

    private void initializeLevel() {
        // First Tower
        structures.add(new WoodStructure(tower1X, ground, 20, 70, textureManager.getWoodRegion()));
        structures.add(new WoodStructure(tower1X, ground+70+20, 20, 50, textureManager.getWoodRegion()));

        // Second tower
        float tower2X = 640;
        structures.add(new WoodStructure(tower2X, ground, 20, 70, textureManager.getWoodRegion()));
        structures.add(new WoodStructure(tower2X, ground+70+20, 20, 50, textureManager.getWoodRegion()));

        // Horizontal platforms
        structures.add(new WoodStructure(tower1X - 20, ground+70, 150, 20, textureManager.getWoodRegion()));


        // Add pigs
        characters.add(new BasicPig((tower1X + tower2X + 20-48)/2, ground+88, textureManager.getBasicPigRegion()));
        characters.add(new BasicPig((tower1X + tower2X + 20-48)/2, ground, textureManager.getBasicPigRegion()));

        // Initialize birds
        float birdStartX = 120;
        float birdStartY = ground;

        // Create the active bird (the one ready to launch)
        activeBird = new RedBird(birdStartX, birdStartY, textureManager.getRedBirdRegion());

        // Add available birds (waiting to be used)
        availableBirds.add(new RedBird(70, birdStartY, textureManager.getRedBirdRegion()));
        availableBirds.add(new RedBird(30, birdStartY, textureManager.getRedBirdRegion()));

    }

    @Override
    protected void renderLevelContent(SpriteBatch batch) {
        // Draw background
        batch.draw(background, 0, 0, 800, 480);

        // Draw all structures
        for (Structure structure : structures) {
            structure.render(batch);
        }

        // Draw all characters (pigs)
        for (GameCharacter character : characters) {
            character.render(batch);
        }

        // Draw active bird
        if (activeBird != null) {
            activeBird.render(batch);
        }

        // Draw available birds
        for (GameCharacter bird : availableBirds) {
            bird.render(batch);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        textureManager.dispose();
    }

    // Method to handle bird launching
    /*public void launchBird(float velocityX, float velocityY) {
        if (activeBird != null) {

        }
    }

    // Method to select next bird
    private void selectNextBird() {
        if (availableBirds.size > 0) {
            activeBird = (RedBird) availableBirds.first();
            availableBirds.removeIndex(0);
        } else {
            activeBird = null;
            // Could trigger level completion check here
        }
    }

    // Method to check for collisions (to be implemented)
    private void checkCollisions() {
        // Implementation for collision detection between birds, structures, and pigs would go here
    }

    // Method to update level state (to be implemented)
    public void update(float delta) {
        if (!isPaused) {
            // Update physics, check collisions, etc.
            checkCollisions();

            // Check for destroyed structures and pigs
            // Update score and level status
        }
    }*/
}