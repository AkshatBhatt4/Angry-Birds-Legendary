package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public abstract class Level {
    protected Texture background;
    protected Texture pauseButton;
    protected Texture playButton;
    protected Texture quitButton;
    protected int levelNumber;
    protected Texture musicOn;
    protected Texture musicOff;
    protected Texture slingshot;
    protected Rectangle pauseButtonBounds;
    protected Rectangle quitButtonBounds;
    protected Rectangle musicToggleBounds;
    protected Rectangle slingshotBounds;
    protected OrthographicCamera camera;
    protected boolean isPaused;
    protected boolean shouldReturnToLevelSelect;
    protected boolean musicStateChanged;
    protected Texture winButton;
    protected Texture saveButton;
    protected Rectangle saveButtonBounds;
    protected Rectangle winButtonBounds;
    protected boolean hasWon;
    protected Texture looseButton;
    protected Rectangle looseButtonBounds;
    protected boolean haslost;
    public int ground=65;
    public float tower1X = 550;

    public Level(OrthographicCamera camera, String backgroundPath , int levelNumber) {
        this.camera = camera;
        this.background = new Texture(backgroundPath);
        this.pauseButton = new Texture("pause_button.png");
        this.playButton = new Texture("play_button2.png");
        this.quitButton = new Texture("home_page_dark.png");
        this.musicOn = new Texture("music_on2.png");
        this.musicOff = new Texture("music_off2.png");
        this.slingshot = new Texture("slingshot.png");
        this.saveButton = new Texture("SaveButton.png");
        this.levelNumber = levelNumber;

        // Position pause/play button in top right corner
        this.pauseButtonBounds = new Rectangle(730, 420, 50, 50);

        // Position menu items directly below the pause button with small gaps
        this.quitButtonBounds = new Rectangle(730, 300, 50, 50);
        this.musicToggleBounds = new Rectangle(730, 360, 42, 42);

        // Position slingshot
        this.slingshotBounds = new Rectangle(130, ground, 100, 85);
        this.winButton = new Texture("win.png"); // Using your win.png
        this.winButtonBounds = new Rectangle(20, 420, 50, 50); // Top left position
        this.hasWon = false;
        this.saveButtonBounds = new Rectangle(160, 420, 50, 50);

        this.looseButton = new Texture("lose.png"); // Using your win.png
        this.looseButtonBounds = new Rectangle(90, 420, 50, 50); // Top left position
        this.haslost = false;

        this.isPaused = false;
        this.shouldReturnToLevelSelect = false;
        this.musicStateChanged = false;
    }

    public void render(SpriteBatch batch, boolean isMusicOn) {
        // Draw background
        batch.draw(background, 0, 0, 800, 480);

        // Draw level content if not paused
        if (!isPaused) {
            renderLevelContent(batch);

            batch.draw(winButton, winButtonBounds.x, winButtonBounds.y, winButtonBounds.width, winButtonBounds.height);
            batch.draw(saveButton, saveButtonBounds.x, saveButtonBounds.y, saveButtonBounds.width, saveButtonBounds.height);
            batch.draw(looseButton, looseButtonBounds.x, looseButtonBounds.y, looseButtonBounds.width, looseButtonBounds.height);
        }

        // Always draw the slingshot, regardless of pause state
        batch.draw(slingshot, slingshotBounds.x, slingshotBounds.y, slingshotBounds.width, slingshotBounds.height);

        // Draw pause/play button based on current state
        if (isPaused) {
            // Draw play button when paused
            batch.draw(playButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);

            // Draw menu items
            batch.draw(quitButton, quitButtonBounds.x, quitButtonBounds.y, quitButtonBounds.width, quitButtonBounds.height);

            batch.draw(isMusicOn ? musicOn : musicOff, musicToggleBounds.x, musicToggleBounds.y, musicToggleBounds.width, musicToggleBounds.height);
        }
        else {
            // Draw pause button when playing
            batch.draw(pauseButton, pauseButtonBounds.x, pauseButtonBounds.y, pauseButtonBounds.width, pauseButtonBounds.height);
        }
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void handleInput(float touchX, float touchY) {
        if (pauseButtonBounds.contains(touchX, touchY)) {
            isPaused = !isPaused;
        }
        if (isPaused) {
            if (quitButtonBounds.contains(touchX, touchY)) {
                shouldReturnToLevelSelect = true;
            } else if (musicToggleBounds.contains(touchX, touchY)) {
                musicStateChanged = true;
            }
        }else if (winButtonBounds.contains(touchX, touchY)) {
            hasWon = true;
        }
        else if (looseButtonBounds.contains(touchX, touchY)) {
            haslost = true;
        }
    }

    public boolean hasWon() {
        return hasWon;
    }

    public boolean haslost() {
        return haslost;
    }

    protected abstract void renderLevelContent(SpriteBatch batch);

    public void dispose() {
        background.dispose();
        pauseButton.dispose();
        playButton.dispose();
        quitButton.dispose();
        musicOn.dispose();
        musicOff.dispose();
        slingshot.dispose();
    }

    public boolean shouldReturnToLevelSelect() {
        return shouldReturnToLevelSelect;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isMusicStateChanged() {
        return musicStateChanged;
    }

    public void resetMusicStateChanged() {
        musicStateChanged = false;
    }
}
