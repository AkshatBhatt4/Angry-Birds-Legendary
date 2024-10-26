package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class VictoryScreen {
    private Texture background;
    protected Texture star1;
    protected Rectangle star1Bounds;
    protected Rectangle star2Bounds;
    protected Rectangle star3Bounds;
    protected Texture goldwin;
    protected Rectangle goldwinBounds;

    public VictoryScreen() {
        background = new Texture("victory_bg.jpg");
        this.star1 = new Texture("star.png");
        this.goldwin = new Texture("gold_win.png");
        this.star1Bounds = new Rectangle(230, 240, 90, 80);
        this.star2Bounds = new Rectangle(350, 240, 90, 80);
        this.star3Bounds = new Rectangle(470, 240, 90, 80);
        this.goldwinBounds = new Rectangle(315, 340, 155, 135);
    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, 800, 480);
        batch.draw(star1, star1Bounds.x, star1Bounds.y,
            star1Bounds.width, star1Bounds.height);
        batch.draw(star1, star2Bounds.x, star2Bounds.y,
            star2Bounds.width, star2Bounds.height);
        batch.draw(star1, star3Bounds.x, star3Bounds.y,
            star3Bounds.width, star3Bounds.height);
        batch.draw(goldwin, goldwinBounds.x, goldwinBounds.y,
            goldwinBounds.width, goldwinBounds.height);
    }

}
