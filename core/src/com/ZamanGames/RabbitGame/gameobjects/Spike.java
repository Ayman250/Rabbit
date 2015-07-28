package com.ZamanGames.RabbitGame.gameobjects;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ayman on 6/22/2015.
 */
public class Spike extends Scrollable {


    public Spike(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        //to give the spike hitBox a little leeWay width and Height are made a little smaller
        hitBox = new Rectangle(x, y, width-10, -height);
    }

    @Override
    public void reset(float newX, float newY) {
        super.reset(newX, newY);
        position.y = newY;
        hitBox.x = position.x;
        hitBox.y = position.y;
    }

    @Override
    public void update(float delta) {
        super.update(delta);


    }

    //This onReset method needs to be fixed
    public void onReset(float x, float scrollSpeed) {
        position.x = x;
        position.y = -1000;
        velocity.x = scrollSpeed;
        hitBox.x = x;
        hitBox.y = -1000;

    }

}
