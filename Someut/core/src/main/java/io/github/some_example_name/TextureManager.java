package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

class TextureManager {
    Texture block;
    Texture BirdPig;

    // Specific regions for different characters
    private TextureRegion basicPigRegion;
    private TextureRegion kingPigRegion;
    private TextureRegion armoredPigRegion;
    private TextureRegion redBirdRegion;
    private TextureRegion blueBirdRegion;
    private TextureRegion blackBirdRegion;
    private TextureRegion woodRegion;
    private TextureRegion glassRegion;
    private TextureRegion stoneRegion;
    //private TextureRegion crackedGlassRegion;
    //private TextureRegion damagedWoodRegion;
    //private TextureRegion rustedSteelRegion;

    public TextureManager() {
        block= new Texture("Blocks.png");
        BirdPig = new Texture("BirdPig.png");

        //woodRegion = new TextureRegion(block,470,707,160,18);
        woodRegion = new TextureRegion(block,83,823,35,35);
        glassRegion =new TextureRegion(block,615,232,35,35);
        stoneRegion = new TextureRegion(block,83,511,35,35);

        redBirdRegion = new TextureRegion(BirdPig,831,815,48,46);
        blueBirdRegion = new TextureRegion(BirdPig,0,201,32,30);
        blackBirdRegion = new TextureRegion(BirdPig,874,258,62,80);

        basicPigRegion = new TextureRegion(BirdPig,718,817,50,46);
        kingPigRegion = new TextureRegion(BirdPig,808,0,126,154);
        armoredPigRegion = new TextureRegion(BirdPig,368,884,95,85);
        /*
        crackedGlassRegion = ;
        damagedWoodRegion = ;
        rustedSteelRegion = ;*/
    }

    public TextureRegion getBasicPigRegion() { return basicPigRegion; }
    public TextureRegion getKingPigRegion() { return kingPigRegion; }
    public TextureRegion getArmoredPigRegion() { return armoredPigRegion; }
    public TextureRegion getRedBirdRegion() { return redBirdRegion; }
    public TextureRegion getBlueBirdRegion() { return blueBirdRegion; }
    public TextureRegion getBlackBirdRegion() { return blackBirdRegion; }

    public TextureRegion getWoodRegion() { return woodRegion; }
    public TextureRegion getGlassRegion() { return glassRegion; }
    public TextureRegion getStoneRegion() { return stoneRegion; }
    /*public TextureRegion getCrackedGlassRegion() { return crackedGlassRegion; }
    public TextureRegion getDamagedWoodRegion() { return damagedWoodRegion; }
    public TextureRegion getRustedSteelRegion() { return rustedSteelRegion; }*/

    public void dispose() {
        return;
    }
}