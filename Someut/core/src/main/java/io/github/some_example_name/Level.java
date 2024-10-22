package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public abstract class Level {
    protected Texture background;
    protected Texture pauseButton;
    protected Texture quitButton;
    protected Rectangle pauseButtonBounds;
    protected Rectangle quitButtonBounds;
    protected OrthographicCamera camera;
    protected boolean isPaused;
    protected boolean shouldReturnToLevelSelect;

    public Level(OrthographicCamera camera, String backgroundPath) {
        this.camera = camera;
        this.background = new Texture(backgroundPath);
        this.pauseButton = new Texture("pause_button.png");
        this.quitButton = new Texture("quit_button.png");

        // Position pause button in top right corner
        this.pauseButtonBounds = new Rectangle(730, 420, 50, 50);
        // Position quit button right below pause button
        this.quitButtonBounds = new Rectangle(730, 360, 50, 50);

        this.isPaused = false;
        this.shouldReturnToLevelSelect = false;
    }

    public void handleInput(float touchX, float touchY) {
        if (pauseButtonBounds.contains(touchX, touchY)) {
            isPaused = !isPaused;  // Toggle pause state
        }
        if (isPaused && quitButtonBounds.contains(touchX, touchY)) {
            shouldReturnToLevelSelect = true;
        }
    }

    public void render(SpriteBatch batch) {
        // Draw background
        batch.draw(background, 0, 0, 800, 480);

        // Draw level content if not paused
        if (!isPaused) {
            renderLevelContent(batch);
        }

        // Always draw pause button
        batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y,
            pauseButtonBounds.width, pauseButtonBounds.height);

        // Draw quit button only when paused
        if (isPaused) {
            batch.draw(quitButton, quitButtonBounds.x, quitButtonBounds.y,
                quitButtonBounds.width, quitButtonBounds.height);
        }
    }

    protected abstract void renderLevelContent(SpriteBatch batch);

    public void dispose() {
        background.dispose();
        pauseButton.dispose();
        quitButton.dispose();
    }

    public boolean shouldReturnToLevelSelect() {
        return shouldReturnToLevelSelect;
    }

    public boolean isPaused() {
        return isPaused;
    }
}
