package com.ZamanGames.RabbitGame.rhelpers;

import com.badlogic.gdx.math.Rectangle;

/**
 * Created by Ayman on 7/8/2015.
 */
public final class Extras {
    public static boolean hit(Rectangle r1, Rectangle r2) {
        return r1.x + r1.width >= r2.x && r1.x <= r2.x + r2.width && r1.y >= r2.y + r2.height && r1.y + r1.height <= r2.y;
    }
}
