package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 7/18/2015.
 */
public class Cloud extends Scrollable {

    private boolean is1;

    public Cloud(float x, float y, int width, int height, float scrollSpeed, boolean is1) {
        super(x, y, width, height, scrollSpeed);
        this.is1 = is1;
    }

    //If it is cloud1 type it will return 2 else false
    public boolean is1() {
        return is1;
    }

    public void onRestart(float newX, float newY, boolean is1) {
        position.x = newX;
        position.y = newY;
        this.is1 = is1;
    }

    public void reset(float newX, float newY, boolean is1) {
        super.reset(newX, newY);
        is1 = is1;
    }
}
