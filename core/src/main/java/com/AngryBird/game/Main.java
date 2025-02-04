package com.AngryBird.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.graphics.GL20;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private Texture background, playButton, musicOn, musicOff;
    private OrthographicCamera camera;
    private Music backgroundMusic;

    private Rectangle playButtonBounds, musicToggleBounds;
    private boolean isMusicOn = true;
    private GameState currentState = GameState.HOME;

    private LevelSelectScreen levelSelectScreen;
    private Level currentLevel;
    private VictoryScreen victoryScreen;
    private LooseScreen looseScreen;

    private enum GameState {
        HOME,
        LEVEL_SELECT,
        PLAYING_LEVEL,
        VICTORY,
        LOSE
    }

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = new SpriteBatch();

        // Load textures
        background = new Texture("background.jpg");
        playButton = new Texture("play_button.png");
        musicOn = new Texture("music_on.png");
        musicOff = new Texture("music_off.png");

        // Load and setup background music
        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("music.mp3"));
        backgroundMusic.setLooping(true);
        backgroundMusic.play();

        // Set up button bounds
        playButtonBounds = new Rectangle(300, 110, 200, 100);
        musicToggleBounds = new Rectangle(700, 400, 50, 50);

        // Initialize LevelSelectScreen
        levelSelectScreen = new LevelSelectScreen(camera);
        victoryScreen = new VictoryScreen();
        looseScreen = new LooseScreen();
    }

    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        switch (currentState) {
            case HOME:
                batch.draw(background, 0, 0, 800, 480);
                renderHomeScreen();
                break;
            case LEVEL_SELECT:
                batch.draw(background, 0, 0, 800, 480);
                levelSelectScreen.render(batch);
                break;
            case PLAYING_LEVEL:
                if (currentLevel != null) {
                    currentLevel.render(batch, isMusicOn);
                    // Check for victory condition
                    if (currentLevel.hasWon()) {
                        currentState = GameState.VICTORY;
                        victoryScreen.setCurrentLevel(currentLevel.getLevelNumber()); // Add this line
                    }

                    if (currentLevel.hasLost()) {
                        currentState = GameState.LOSE;
                        looseScreen.setCurrentLevel(currentLevel.getLevelNumber()); // Add this line
                    }

                    // Check if we should return to level select
                    if (currentLevel.shouldReturnToLevelSelect()) {
                        currentState = GameState.LEVEL_SELECT;
                        currentLevel.dispose();
                        currentLevel = null;
                    }
                }
                break;
            case VICTORY:  // Add this case
                victoryScreen.render(batch);
                break;

            case LOSE:  // Add this case
                looseScreen.render(batch);
                break;
        }

        batch.end();
        handleInput();
    }

    private void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
            camera.unproject(touchPos);

            switch (currentState) {
                case HOME:
                    handleHomeScreenInput(touchPos);
                    break;
                case LEVEL_SELECT:
                    handleLevelSelectInput(touchPos);
                    break;
                case PLAYING_LEVEL:
                    if (currentLevel != null) {
                        currentLevel.handleInput(touchPos.x, touchPos.y);
                        if (currentLevel.isMusicStateChanged()) {
                            toggleMusic();
                            currentLevel.resetMusicStateChanged();
                        }
                    }
                    break;

                case VICTORY:

                    if (victoryScreen.isBackButtonPressed(touchPos.x, touchPos.y)) {
                        currentState = GameState.LEVEL_SELECT;
                    } else if (victoryScreen.isReplayButtonPressed(touchPos.x, touchPos.y)) {
                        // Reload the current level
                        loadLevel(victoryScreen.getCurrentLevel());
                        currentState = GameState.PLAYING_LEVEL;
                    } else if (victoryScreen.isNextButtonPressed(touchPos.x, touchPos.y)) {
                        // Load next level
                        loadLevel(victoryScreen.getNextLevel());
                        currentState = GameState.PLAYING_LEVEL;
                    }
                    break;

                case LOSE:
                    if (looseScreen.isBackButtonPressed(touchPos.x, touchPos.y)) {
                        currentState = GameState.LEVEL_SELECT;
                    } else if (looseScreen.isReplayButtonPressed(touchPos.x, touchPos.y)) {
                        // Reload the current level
                        loadLevel(looseScreen.getCurrentLevel());
                        currentState = GameState.PLAYING_LEVEL;
                    }
                    break;
            }
        }
    }

    private void renderHomeScreen() {
        batch.draw(playButton, playButtonBounds.x, playButtonBounds.y,
                playButtonBounds.width, playButtonBounds.height);
        batch.draw(isMusicOn ? musicOn : musicOff,
                musicToggleBounds.x, musicToggleBounds.y,
                musicToggleBounds.width, musicToggleBounds.height);
    }

    private void handleHomeScreenInput(Vector3 touchPos) {
        if (playButtonBounds.contains(touchPos.x, touchPos.y)) {
            currentState = GameState.LEVEL_SELECT;
        } else if (musicToggleBounds.contains(touchPos.x, touchPos.y)) {
            toggleMusic();
        }
    }

    private void handleLevelSelectInput(Vector3 touchPos) {
        if (levelSelectScreen.isBackButtonPressed(touchPos.x, touchPos.y)) {
            currentState = GameState.HOME;
        } else {
            int levelPressed = levelSelectScreen.getLevelPressed(touchPos.x, touchPos.y);
            if (levelPressed != -1) {
                loadLevel(levelPressed);
                currentState = GameState.PLAYING_LEVEL;
            }
        }
    }

    private void loadLevel(int levelNumber) {
        if (currentLevel != null) {
            currentLevel.dispose();
        }

        switch (levelNumber) {
            case 1:
                currentLevel = new Level1(camera);
                break;
            case 2:
                currentLevel = new Level2(camera);
                break;
            case 3:
                currentLevel = new Level3(camera);
                break;
        }
    }

    private void toggleMusic() {
        isMusicOn = !isMusicOn;
        if (isMusicOn) {
            backgroundMusic.play();
        } else {
            backgroundMusic.pause();
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        background.dispose();
        playButton.dispose();
        musicOn.dispose();
        musicOff.dispose();
        backgroundMusic.dispose();
        if (currentLevel != null) {
            currentLevel.dispose();
        }
    }
}
