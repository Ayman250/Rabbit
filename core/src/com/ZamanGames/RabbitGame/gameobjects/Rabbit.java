package com.ZamanGames.RabbitGame.gameobjects;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Ayman on 6/6/2015.
 */
public class Rabbit {

    private Vector2 position;
    private Vector2 velocity;
    private Vector2 acceleration;

    private float height, width, groundY, initY, initGroundY;

    private float initXVelocity, initYVelocity;

    private float delta, timeLeft;

    private boolean isDead, screenHeld, upAllowed;

    private Rectangle hitBox;

    public Rabbit(float x, float y, int width, int height, int groundY) {
        this.height = -height;
        this.width = width;
        this.groundY = groundY;

        position = new Vector2(x, y);
        velocity = new Vector2(0, 0);
        acceleration = new Vector2(0, 2000);
        hitBox = new Rectangle(x, y, width, -height);

        isDead = false;

        initY = y;
        initGroundY = groundY;

        screenHeld = false;
        //determines if screen holding will cause upwards acceleration of rabbit
        upAllowed = true;
    }

    public void update(float delta) {
        velocity.add(acceleration.cpy().scl(delta));
        this.delta = delta;

        position.add(velocity.cpy().scl(delta));
        if (position.y > this.groundY) {
            position.y = this.groundY;
        }
        //If rabbit is on the ground set velocity in y to 0;
        if (!inAir()) {
            velocity.y = 0;
            upAllowed = true;
        }
        hitBox.x = position.x;
        hitBox.y = position.y;

        if (screenHeld) {
            jump();
        }

    }

    public void updateReady(float runTime) {
        position.y = 2 * (float) Math.sin(7 * runTime) + initY;
    }

    public void jump() {
        //if the rabbit lands reset upAllowed to false so game knows to activate jump mechanics when pressed

        if (upAllowed && timeLeft > 0) {
            velocity.add(0, -delta*2000);
            timeLeft -=delta;
        }
    }

    public void onClick() {
        if (!inAir()) {
            velocity.add(0, -600);
            timeLeft = .4f;

        }
        screenHeld = true;
    }

    public void onRelease() {
        screenHeld = false;
        if (inAir()) {
            upAllowed = false;
        }
    }

    public void changeHeight(float newY) {
        groundY = newY;
    }

    public void pause() {
        initYVelocity = velocity.y;
        initXVelocity = velocity.x;
        velocity.x = 0;
        velocity.y = 0;
    }

    public void resume() {

        velocity.x = initXVelocity;
        velocity.y = initYVelocity;
    }

    public void die() {
        isDead = true;
        velocity.x = 0;
    }

    public void onRestart(int y) {
        position.y = y;
        velocity.x = 0;
        velocity.y = 0;
        acceleration.x = 0;
        acceleration.y = 2000;
        groundY = initGroundY;
        isDead = false;
        screenHeld = true;
        upAllowed = true;
    }

    public boolean inAir() {
        return position.y < groundY;
    }

    public boolean isDead() {
        return isDead;
    }

    public Rectangle getHitBox() {
        return hitBox;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

}
