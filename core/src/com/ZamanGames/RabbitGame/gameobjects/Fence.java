package com.ZamanGames.RabbitGame.gameobjects;

/**
 * Created by Ayman on 6/11/2015.
 */
public class Fence extends Scrollable{

    public Fence(float x, float y, int width, int height, float scrollSpeed, float groundY) {
        super(x, y, width, height, scrollSpeed);

        }
    //called whenever the ground's method is called from scroll handler
    //new fence is built on top of ground that calls it with appropriate height
    //kind of makeshift but works
    public void changeHeight(float newY) {
        position.y = newY - height;
    }
}
