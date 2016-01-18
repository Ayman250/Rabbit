package com.ZamanGames.RabbitGame.gameobjects;

import com.ZamanGames.RabbitGame.rhelpers.Extras;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ayman on 6/6/2015.
 */
public class Scrollable {
    protected Vector2 position;
    protected Vector2 velocity;
    protected int width, height;
    protected float initXVelocity;
    protected float scrollSpeed;
    protected boolean isScrolledLeft;
    protected Rectangle hitBox;

    public Scrollable(float x, float y, int width, int height, float scrollSpeed) {
        position = new Vector2(x, y);
        velocity = new Vector2(scrollSpeed, 0);
        this.scrollSpeed = scrollSpeed;
        this.width = width;
        //So that rendering is made more simple and uniform
        //Except ground because I don't want to recode half the game
        this.height = - height;
        isScrolledLeft = false;

        hitBox = new Rectangle(x, y, width, height);
    }

    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        //If no longer visible FUCKIT
        if (position.x + width <= 0 ) {
            isScrolledLeft
             = true;
        }
        hitBox.x = position.x;
        initXVelocity = velocity.x;

    }

    public void reset(float newX, float newY) {
        position.x = newX;
        isScrolledLeft = false;
    }

    public void stop() {
        //fSystem.out.println(initXVelocity);
        velocity.x = 0;
    }

    public void resume() {
        //System.out.println(initXVelocity);
        velocity.x = initXVelocity;
    }

    public boolean isScrolledLeft() {
        return isScrolledLeft;
    }

    public boolean collides (Rabbit rabbit) {
        //return (Intersector.overlaps(rabbit.getHitBox(), hitBox));
        //Made my own Overlaps Method called Hits
        return Extras.hit(rabbit.getHitBox(), hitBox);
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public float getTailX() {
        return position.x + width;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }


}
