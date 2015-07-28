package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 6/21/2015.
 */
public class Water extends Scrollable {
    public Water(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
    }

        public void onReset(float x, float scrollSpeed) {
            position.x = x;
            velocity.x = scrollSpeed;
            hitBox.x = x;

    }
}
