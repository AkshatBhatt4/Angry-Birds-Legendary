package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class LooseScreen {
    private Texture background;
    protected Texture nostar1;
    protected Rectangle nostar1Bounds;
    protected Rectangle nostar2Bounds;
    protected Rectangle nostar3Bounds;
    protected Texture gover;
    protected Rectangle goverBounds;
    private Texture backButton;
    private Rectangle backButtonBounds;
    private Texture replayButton;
    private Rectangle replayButtonBounds;
    private int currentLevel;

    public LooseScreen() {
        background = new Texture("victory_bg.jpg");
        this.nostar1 = new Texture("no_star.png");
        this.gover = new Texture("over.png");
        this.replayButton = new Texture("replay.png");
        this.nostar1Bounds = new Rectangle(230, 240, 90, 80);
        this.nostar2Bounds = new Rectangle(350, 240, 90, 80);
        this.nostar3Bounds = new Rectangle(470, 240, 90, 80);

        this.replayButtonBounds = new Rectangle(430, 65, 65, 60);
        this.goverBounds = new Rectangle(305, 330, 170, 130);

        this.backButton = new Texture("home_page.png");
        // Positioning the back button at the bottom center of the screen
        this.backButtonBounds = new Rectangle(285, 60, 80, 70); // Adjust position and size as needed

    }

    public void render(SpriteBatch batch) {
        batch.draw(background, 0, 0, 800, 480);
        batch.draw(nostar1, nostar1Bounds.x, nostar1Bounds.y,
            nostar1Bounds.width, nostar1Bounds.height);
        batch.draw(nostar1, nostar2Bounds.x, nostar2Bounds.y,
            nostar2Bounds.width, nostar2Bounds.height);
        batch.draw(nostar1, nostar3Bounds.x, nostar3Bounds.y,
            nostar3Bounds.width, nostar3Bounds.height);
        batch.draw(gover, goverBounds.x, goverBounds.y,
            goverBounds.width, goverBounds.height);
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y,
            backButtonBounds.width, backButtonBounds.height);
        batch.draw(replayButton, replayButtonBounds.x, replayButtonBounds.y,
            replayButtonBounds.width, replayButtonBounds.height);
    }

    public boolean isBackButtonPressed(float x, float y) {
        return backButtonBounds.contains(x, y);
    }

    public boolean isReplayButtonPressed(float x, float y) {
        return replayButtonBounds.contains(x, y);
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }
}

