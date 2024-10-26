package io.github.some_example_name;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class LevelSelectScreen {
    private Texture backButton;
    private Texture[] levelIcons;
    private Rectangle backButtonBounds;
    private Rectangle[] levelButtons;
    private OrthographicCamera camera;
    private BitmapFont font1;

    public LevelSelectScreen(OrthographicCamera camera) {
        this.camera = camera;

        backButton = new Texture("back_button.png");

        levelIcons = new Texture[3];
        levelIcons[0] = new Texture("level_3.png");
        levelIcons[1] = new Texture("level_2.png");
        levelIcons[2] = new Texture("level_1.png");

        backButtonBounds = new Rectangle(10, 410, 60, 55);

        levelButtons = new Rectangle[3];
        levelButtons[0] = new Rectangle(155, 300, 85, 70);
        levelButtons[1] = new Rectangle(355, 100, 85, 70);
        levelButtons[2] = new Rectangle(550, 300, 85, 70);

        font1 = new BitmapFont();
        font1.getData().setScale(1);
    }

    public void render(SpriteBatch batch) {
        batch.draw(backButton, backButtonBounds.x, backButtonBounds.y, backButtonBounds.width, backButtonBounds.height);

        font1.draw(batch, "Level 1", 175, 290);
        font1.draw(batch, "Level 2", 375, 90);
        font1.draw(batch, "Level 3", 570, 290);

        for (int i = 0; i < levelButtons.length; i++) {
            Rectangle levelButton = levelButtons[i];
            batch.draw(levelIcons[i], levelButton.x, levelButton.y, levelButton.width, levelButton.height);
        }
    }

    public boolean isBackButtonPressed(float x, float y) {
        return backButtonBounds.contains(x, y);
    }

    public int getLevelPressed(float x, float y) {
        for (int i = 0; i < levelButtons.length; i++) {
            if (levelButtons[i].contains(x, y)) {
                return i + 1;
            }
        }
        return -1;
    }
}
