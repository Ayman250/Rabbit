package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 1/15/2016.
 */
public class Weed extends Scrollable {

    public Weed(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        hitBox.height = -height;
    }

    public void reset(float newX, float newY, float scrollSpeed) {
        position.x = newX;
        isScrolledLeft = false;
        velocity.x = scrollSpeed;
    }

    public void onReset(float x, float y, float scrollSpeed) {
        position.x = x;
        position.y = y;
        velocity.x = scrollSpeed;

    }
}
