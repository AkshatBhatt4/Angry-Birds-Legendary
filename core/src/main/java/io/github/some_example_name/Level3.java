package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Level3 extends Level {
    private TextureManager textureManager;
    private Array<Structure> structures;
    private Array<GameCharacter> characters;
    private RedBird activeBird;
    private Array<GameCharacter> availableBirds;

    public Level3(OrthographicCamera camera) {
        super(camera, "level1_bg.jpg",3);

        // Initialize texture manager with sprite sheet
        textureManager = new TextureManager();

        structures = new Array<>();
        characters = new Array<>();
        availableBirds = new Array<>();

        initializeLevel();
    }

    private void initializeLevel() {
        // First Tower
        structures.add(new WoodStructure(tower1X-50, ground, 20, 70, textureManager.getWoodRegion()));

        // Second tower
        float tower2X = tower1X+150;
        structures.add(new WoodStructure(tower2X, ground, 20, 70, textureManager.getWoodRegion()));

        // First stone tower
        float tower3X = (tower1X-50+tower2X)/2;
        structures.add(new StoneStructure(tower3X, ground, 20, 140, textureManager.getStoneRegion()));

        structures.add(new GlassStructure(tower3X-50, ground+160, 20, 100, textureManager.getGlassRegion()));
        structures.add(new GlassStructure(tower3X+50, ground+160, 20, 100, textureManager.getGlassRegion()));

        // Horizontal platforms
        structures.add(new WoodStructure(tower1X - 90, ground+70, 100, 20, textureManager.getWoodRegion()));
        structures.add(new WoodStructure(tower2X - 40, ground+70, 100, 20, textureManager.getWoodRegion()));
        structures.add(new StoneStructure(tower3X-50, ground+140, 120, 20, textureManager.getStoneRegion()));
        structures.add(new GlassStructure(tower3X-50, ground+260, 120, 20, textureManager.getGlassRegion()));


        // Add pigs
        characters.add(new BasicPig((tower1X-50 -25+10), ground+90-2, textureManager.getBasicPigRegion()));
        characters.add(new BasicPig((tower2X -25+10), ground+90-2, textureManager.getBasicPigRegion()));
        characters.add(new KingPig((tower3X -38+10), ground+160, textureManager.getKingPigRegion()));

        // Initialize birds
        float birdStartX = 125;
        float birdStartY = ground;

        // Create the active bird (the one ready to launch)
        activeBird = new RedBird(birdStartX, birdStartY, textureManager.getRedBirdRegion());

        // Add available birds (waiting to be used)
        availableBirds.add(new BlackBird(75, birdStartY, textureManager.getBlackBirdRegion()));
        availableBirds.add(new BlackBird(25, birdStartY, textureManager.getBlackBirdRegion()));

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
