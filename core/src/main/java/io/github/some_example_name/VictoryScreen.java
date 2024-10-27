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
    private Texture backButton;
    private Rectangle backButtonBounds;
    private Texture replayButton;
    private Rectangle replayButtonBounds;
    private Texture nextButton;
    private Rectangle nextButtonBounds;
    private int currentLevel;

    public VictoryScreen() {
        background = new Texture("victory_bg.jpg");
        this.star1 = new Texture("star.png");
        this.goldwin = new Texture("gold_win.png");
        this.star1Bounds = new Rectangle(230, 240, 90, 80);
        this.star2Bounds = new Rectangle(350, 240, 90, 80);
        this.star3Bounds = new Rectangle(470, 240, 90, 80);
        this.goldwinBounds = new Rectangle(315, 340, 155, 135);

        // Initialize all buttons
        this.backButton = new Texture("home_page.png");
        this.replayButton = new Texture("replay.png");
        this.nextButton = new Texture("next.png");

        // Position buttons in a row at the bottom
        this.backButtonBounds = new Rectangle(235, 60, 80, 70);
        this.replayButtonBounds = new Rectangle(365, 65, 65, 60);
        this.nextButtonBounds = new Rectangle(485, 60, 80, 70);
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

        // Draw all buttons
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y,
                backButtonBounds.width, backButtonBounds.height);
        batch.draw(replayButton, replayButtonBounds.x, replayButtonBounds.y,
                replayButtonBounds.width, replayButtonBounds.height);

        // Only draw next button if there's a next level available
        if (currentLevel < 3) { // Assuming 3 is your max level
            batch.draw(nextButton, nextButtonBounds.x, nextButtonBounds.y,
                    nextButtonBounds.width, nextButtonBounds.height);
        }
    }

    public boolean isBackButtonPressed(float x, float y) {
        return backButtonBounds.contains(x, y);
    }

    public boolean isReplayButtonPressed(float x, float y) {
        return replayButtonBounds.contains(x, y);
    }

    public boolean isNextButtonPressed(float x, float y) {
        return currentLevel < 3 && nextButtonBounds.contains(x, y);
    }

    public void setCurrentLevel(int level) {
        this.currentLevel = level;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public int getNextLevel() {
        return currentLevel + 1;
    }

    public void dispose() {
        background.dispose();
        star1.dispose();
        goldwin.dispose();
        backButton.dispose();
        replayButton.dispose();
        nextButton.dispose();
    }
}