package com.ZamanGames.RabbitGame.gameobjects;

import com.ZamanGames.RabbitGame.rhelpers.Extras;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ayman on 6/22/2015.
 */
public class Spike extends Scrollable {

    private Rectangle hitBox1, hitBox2, hitBox3;
    public Spike(float x, float y, int width, int height, float scrollSpeed) {
        super(x, y, width, height, scrollSpeed);
        //to give the spike hitBox a little leeWay width and Height are made a little smaller
        hitBox1 = new Rectangle(x, y-.5f*height, width*.4f, -(height-5));
        hitBox2 = new Rectangle(x + width*.4f, y, width*.3f, -(height-5));
        hitBox3 = new Rectangle(x + width*.7f, y-.5f*height, width*.3f, -(height-5));
    }

    @Override
    public void reset(float newX, float newY) {
        super.reset(newX, newY);
        position.y = newY;
        hitBox1.x = position.x;
        hitBox1.y = position.y-.5f*height;
        hitBox2.x = position.x+ width*.4f;
        hitBox2.y = position.y;
        hitBox3.x = position.x+ width*.7f;
        hitBox3.y = position.y-.5f*height;
    }

    @Override
    public void update(float delta) {
        position.add(velocity.cpy().scl(delta));

        //If no longer visible FUCKIT
        if (position.x + width <= 0 ) {
            isScrolledLeft
                    = true;
        }
        hitBox1.x = position.x;
        hitBox2.x = position.x + width*.4f;
        hitBox3.x = position.x + width*.7f;
        initXVelocity = velocity.x;

    }

    //This onReset method needs to be fixed
    public void onReset(float x, float scrollSpeed) {
        position.x = x;
        position.y = -1000;
        velocity.x = scrollSpeed;
        hitBox.x = x;
        hitBox.y = -1000;

    }

    @Override
    public boolean collides(Rabbit rabbit) {
        return (Extras.hit(rabbit.getHitBox(), hitBox1) || Extras.hit(rabbit.getHitBox(), hitBox2) || Extras.hit(rabbit.getHitBox(), hitBox3));

    }
}
