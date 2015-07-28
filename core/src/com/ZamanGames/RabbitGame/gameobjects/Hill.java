package com.ZamanGames.RabbitGame.gameobjects;

import com.badlogic.gdx.math.Rectangle;

import java.util.Random;

/**
 * Created by Ayman on 6/6/2015.
 */
public class Hill extends Scrollable {
    private Random r;

    private float groundY;


    public Hill(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        hitBox = new Rectangle(x, y, width, -(height - 5));
        r = new Random();
        this.groundY = groundY;
        //This is so that rabbit can slide of hill earlier
        hitBox.width -= 20;
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        hitBox.y = position.y;



    }

    public boolean rabbitOn(Rabbit rabbit) {
        return (rabbit.getX() + (rabbit.getWidth()) >= getX() && getX() + getWidth() > rabbit.getX() + 50);

    }


    public void onReset(float x, float y, int height, float scrollSpeed) {
        position.x = x;
        position.y = y;
        velocity.x = scrollSpeed;
        this.height = -height;
        hitBox.height = this.height;

    }

    @Override
    public void reset(float newX, float newY) {
        super.reset(newX, newY);
        height = -(r.nextInt(100) + 100);
        position.y = newY;
        hitBox.height = height;
        //System.out.println(newY);
       }

}
