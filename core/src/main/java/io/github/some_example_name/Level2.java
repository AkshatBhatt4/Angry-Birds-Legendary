package io.github.some_example_name;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.Array;

public class Level2 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private GameCharacter activeBird;
    private Array<GameCharacter> availableBirds;

    public Level2(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg",2);

        // Initialize texture manager with sprite sheet
        textureManager = new TextureManager();

        structures = new Array<>();
        characters = new Array<>();
        availableBirds = new Array<>();

        initializeLevel();
    }

    private void initializeLevel() {
        // First Tower
        structures.add(new StoneStructure(390, ground, 20, 160, textureManager.getStoneRegion()));
        //structures.add(new StoneStructure(tower1X-30, ground+105, 40, 45, textureManager.getStoneRegion()));


        // Second tower
        float tower2X = tower1X;
        structures.add(new GlassStructure(tower2X, ground, 200, 75, textureManager.getGlassRegion()));
        structures.add(new GlassStructure(tower2X, ground+75, 10, 30, textureManager.getGlassRegion()));
        structures.add(new GlassStructure(tower2X+190, ground+75, 10, 30, textureManager.getGlassRegion()));


        // Add pigs
        characters.add(new ArmoredPig(tower2X +15 , ground+75, textureManager.getArmoredPigRegion()));
        characters.add(new ArmoredPig(tower2X+75 , ground+75, textureManager.getArmoredPigRegion()));
        characters.add(new ArmoredPig(tower2X +135 , ground+75, textureManager.getArmoredPigRegion()));

        // Initialize birds
        float birdStartX = 120;
        float birdStartY = ground;

        // Create the active bird (the one ready to launch)
        activeBird = new BlueBird(birdStartX, birdStartY, textureManager.getBlueBirdRegion());

        // Add available birds (waiting to be used)
        availableBirds.add(new BlackBird(70, birdStartY, textureManager.getBlackBirdRegion()));
        availableBirds.add(new BlueBird(30, birdStartY, textureManager.getBlueBirdRegion()));

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
}
