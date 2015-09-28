package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 9/19/2015.
 */
public class Bullet extends Scrollable {

    public Bullet(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed*3);
    }

    public void setY(float y) {
        position.y = y;
    }


}
