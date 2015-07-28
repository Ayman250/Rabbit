package com.ZamanGames.RabbitGame.gameobjects;

import com.ZamanGames.RabbitGame.rhelpers.Extras;

import java.util.Random;

/**
 * Created by Ayman on 6/9/2015.
 */
public class Ground extends Scrollable {

    //Hitbox for ground is handled slightly differently. Because of back-code, ground.y cannot be set to gameHeight
    //Hitbox.y for ground however will be set to gameHeight and ground.height will be set accordingly.

    private int groundHeight;
    private float initY;

    protected Spike spike;

    protected Hill hill1, hill2;

    private boolean hasSpike;

    private Random r;

    public Ground(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);
        this.height = height;
        hitBox.width = getWidth()/2;
        hitBox.y = 720;
        //Height for ground is not negative so it must be set to such specifically for the Hitbox which is handled differenyl
        hitBox.height = -height;
        r = new Random();
        initY = y;
        groundHeight = 0;
        hasSpike = false;

    }

    @Override
    public void reset(float newX, float newY) {
        groundHeight = r.nextInt(3);
        super.reset(newX, newY);
        //upon reset, set the heihgt of the hitbox to the height of the ground plus the dirt underneath it.
        hitBox.height = -(getHeight() * (1+groundHeight));
        hasSpike = false;
        //System.out.println(groundHeight);

    }

    public int getGroundHeight() {
        return groundHeight;
    }



    public boolean rabbitOn(Rabbit rabbit) {
        return (rabbit.getX() + (rabbit.getWidth() - 10) >= getX() && rabbit.getX() + rabbit.getWidth() / 2 < getX() + getWidth());

    }

    public boolean isOn(float xPosition) {
        return xPosition >= getX() && xPosition < getX() + getWidth();

    }

    @Override
    public void update(float delta) {
        super.update(delta);
    }

    //Overrides the collides.
    //Collides with ground is true if rabbit hitBox intersects ground hitBox && rabbit is not on top of ground
    @Override
    public boolean collides(Rabbit rabbit) {
//      return Intersector.overlaps(rabbit.getHitBox(), hitBox) && rabbit.getY() - 10 > getY();
        return Extras.hit(rabbit.getHitBox(), hitBox) && rabbit.getY() - 10 > getY();

    }

    /*Called when game is reset (different from reset which is called when
    ground passes screen*/
    public void onReset(float x, float scrollSpeed) {
        position.x = x;
        velocity.x = scrollSpeed;
        groundHeight = 0;
    }

    public void firstNewHill(Hill newHill) {
        hill1 = newHill;
    }

    public void secondNewHill(Hill newHill) {
        hill2 = newHill;
    }

    public void newSpike (Spike newSpike) {
        spike = newSpike;
    }

    public float getY() {
        //System.out.println(initY * groundHeight);
        return initY - height*groundHeight;
    }

}
