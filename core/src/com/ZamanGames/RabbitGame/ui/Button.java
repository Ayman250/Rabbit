package com.ZamanGames.RabbitGame.ui;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ayman on 6/26/2015.
 */
public class Button {

    private float x, y, width, height;

    private TextureRegion buttonUp, buttonDown;

    private Rectangle bounds;

    private boolean isPressed = false;

    public Button(float x, float y, float width, float height, TextureRegion buttonUp, TextureRegion buttonDown) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.buttonUp =  buttonUp;
        this.buttonDown = buttonDown;
        bounds = new Rectangle(x, y, width, height);
    }

    public boolean isClicked(int screenX, int screenY) {
        return bounds.contains(screenX, screenY);

    }

    public void draw(SpriteBatch batch) {
        if (isPressed) {
            batch.draw(this.buttonDown, this.x, this.y, this.width, this.height);
        } else {
            batch.draw(this.buttonUp, this.x, this.y, this.width, this.height);
        }
    }

    public boolean isTouchDown(int screenX, int screenY) {
        System.out.println(this.x <= screenX);
        System.out.println(this.x + this.width >= screenX);
        System.out.println(this.y <= screenY);
        System.out.println(this.y + this.height >= screenY);

        if (bounds.contains(screenX, screenY)) {

            isPressed = true;
            return true;
        }
        return false;
    }

    public boolean isTouchUp(int screenX, int screenY) {
        System.out.println(screenX + " , " + screenY);
        // It only counts as touchUp if the button is in a pressed state.
        if(bounds.contains(screenX, screenY) && isPressed){
            return true;
        }
        isPressed = false;
        return false;
    }


}
