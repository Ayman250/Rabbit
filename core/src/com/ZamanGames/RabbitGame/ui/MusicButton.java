package com.ZamanGames.RabbitGame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Ayman on 7/24/2015.
 */
public class MusicButton extends Button {
    private float x, y, width, height;

    private boolean isPressed;

    private TextureRegion drawnTexture;

    public MusicButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        super(x, y, width, height, buttonUp, buttonDown);
        drawnTexture = buttonUp;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(this.drawnTexture, this.x, this.y, this.width, this.height);
    }

    public void changeTexture(TextureRegion drawnTexture) {
        this.drawnTexture = drawnTexture;
    }
}
