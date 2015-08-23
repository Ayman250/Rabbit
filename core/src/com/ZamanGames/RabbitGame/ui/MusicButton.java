package com.ZamanGames.RabbitGame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ayman on 7/24/2015.
 */
public class MusicButton extends Button {
    private float x, y, width, height;

    private TextureRegion buttonUp, buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    private TextureRegion drawnTextureUp, drawnTextureDown;



    public MusicButton(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        super(x, y, width, height, buttonUp, buttonDown);
        drawnTextureUp = buttonUp;
        drawnTextureDown = buttonDown;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        bounds = new Rectangle(x, y, width, height);
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(this.drawnTextureDown, this.x, this.y, this.width, this.height);
        } else {
            batch.draw(this.drawnTextureUp, this.x, this.y, this.width, this.height);
        }
    }



    public boolean isTouchDown(int screenX, int screenY) {

        if (bounds.contains(screenX, screenY)) {

            isPressed = true;
            return true;
        }
        return false;
    }
    public boolean isTouchUp(int screenX, int screenY) {
        // It only counts as touchUp if the button is in a pressed state.
        if(bounds.contains(screenX, screenY) && isPressed){
            isPressed = false;
            return true;
        }
        isPressed = false;
        return false;
    }

    public void changeTexture(TextureRegion drawnTextureUp, TextureRegion drawnTextureDown) {
        this.drawnTextureUp = drawnTextureUp;
        this.drawnTextureDown = drawnTextureDown;
    }
}
