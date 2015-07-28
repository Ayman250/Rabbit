package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 7/17/2015.
 */
public class Tree extends Scrollable {

    private boolean Tall;

    public Tree(float x, float y, int width, int height, float scrollSpeed, boolean Tall) {
        super(x, y, width, height, scrollSpeed);

        this.Tall = Tall;
    }

    public void onRestart(float newX, boolean Tall) {
        position.x = newX;
        this.Tall = Tall;
    }

    public boolean isTall() {
        return Tall;
    }
}
