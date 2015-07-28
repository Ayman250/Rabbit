package com.ZamanGames.RabbitGame.gameobjects;

import com.ZamanGames.RabbitGame.gameworld.GameWorld;

import java.util.Random;

/**
 * Created by Ayman on 6/6/2015.
 */
public class ScrollHandler {

    private Hill hill1, hill2, hill3, hill4;
    private Ground ground1, ground2, rightGround;
    private Fence fence1, fence2;
    private Spike spike1, spike2, spike3;

    public static int SCROLL_SPEED;
    public static int HILL_GAP, TREE_GAP, CLOUD_GAP;

    private Random r;

    private Rabbit rabbit;

    private Water water1, water2;

    private Tree tree1, tree2, tree3, tree4;

    private Cloud cloud1, cloud2, cloud3, cloud4;

    private int gameWidth, gameHeight;

    private float groundY;

    private float spikeLocation, firstHillLocation, secondhillLocation;

    public GameWorld world;

    public ScrollHandler(GameWorld world, int gameWidth, int gameHeight, float groundY) {
        this.world = world;
        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.groundY = groundY;

        rabbit = this.world.getRabbit();

        SCROLL_SPEED = -gameWidth / 2;
        HILL_GAP = gameWidth / 2;
        TREE_GAP = gameWidth / 4;
        CLOUD_GAP = gameWidth / 4;

        //Set invisible at first
        ground1 = new Ground(0, groundY, 1300, 80, SCROLL_SPEED, groundY);
        ground2 = new Ground(ground1.getTailX(), groundY, gameWidth + 20, 80, SCROLL_SPEED, groundY);

        hill1 = new Hill(-1000, ground1.getY() - 10, 90, 140, SCROLL_SPEED, groundY);
        hill2 = new Hill(-1000, -720 - 202, 90, 140, SCROLL_SPEED, groundY);
        hill3 = new Hill(ground2.getTailX() - 400, ground1.getY() - 10, 90, 140, SCROLL_SPEED, groundY);
        hill4 = new Hill(-1000, -720 - 202, 90, 140, SCROLL_SPEED, groundY);


        water1 = new Water(0, groundY, 1300, 70, SCROLL_SPEED);
        water2 = new Water(water1.getTailX(), gameHeight, 1300, 70, SCROLL_SPEED);

        //initialize spikes to be invisble to player
        spike1 = new Spike(0, -200 - 10, 70, 30, SCROLL_SPEED);
        spike2 = new Spike(0, -200 - 10, 70, 30, SCROLL_SPEED);

        tree1 = new Tree(50, groundY, 100, 150, SCROLL_SPEED/2, false);
        tree2 = new Tree(tree1.getTailX() + TREE_GAP, groundY - 12, 100, 150, SCROLL_SPEED/2, true);
        tree3 = new Tree(tree2.getTailX() + TREE_GAP, groundY - 12, 100, 150, SCROLL_SPEED/2, false);
        tree4 = new Tree(tree3.getTailX() + TREE_GAP, groundY - 12, 100, 150, SCROLL_SPEED/2, false);

        cloud1 = new Cloud(50, 100, 150, 75, SCROLL_SPEED/3, true);
        cloud2 = new Cloud(cloud1.getTailX() + CLOUD_GAP, 150, 150, 75, SCROLL_SPEED/3, false);
        cloud3 = new Cloud(cloud2.getTailX() + CLOUD_GAP, 100, 150, 75, SCROLL_SPEED/3, true);
        cloud4 = new Cloud(cloud3.getTailX() + CLOUD_GAP, 120, 150, 75, SCROLL_SPEED/3, false);



        r = new Random();

    }

    public void update(float delta) {

        hill1.update(delta);
        hill2.update(delta);
        hill3.update(delta);


        water1.update(delta);
        water2.update(delta);

        ground1.update(delta);
        ground2.update(delta);

        spike1.update(delta);
        spike2.update(delta);

        tree1.update(delta);
        tree2.update(delta);
        tree3.update(delta);
        tree4.update(delta);

        cloud1.update(delta);
        cloud2.update(delta);
        cloud3.update(delta);
        cloud4.update(delta);

        if (hill1.rabbitOn(rabbit) && hill1.getY() > 0) {
            rabbit.changeHeight(hill1.getY() + hill1.getHeight() - 7);
        } else if (hill2.rabbitOn(rabbit) && hill2.getY() > 0) {
            rabbit.changeHeight(hill2.getY() + hill2.getHeight() - 7);
        } else if (hill3.rabbitOn(rabbit) && hill3.getY() > 0) {
            rabbit.changeHeight(hill3.getY() + hill3.getHeight() - 7);
//            System.out.println("onHill3");
        } else if (hill4.rabbitOn(rabbit) && hill4.getY() > 0) {
            rabbit.changeHeight(hill4.getY() + hill4.getHeight() + 7);
        } else if (ground1.rabbitOn(rabbit)) {
            rabbit.changeHeight(ground1.getY());
//            System.out.println("Ground1");
        } else if (ground2.rabbitOn(rabbit)) {
//            System.out.println("Ground2");
            rabbit.changeHeight(ground2.getY());
        }


        if (ground1.isScrolledLeft()) {
            //After reset hasSpike is changed to false so it is not rendered
            ground1.reset(ground2.getTailX(), 0);
            //fence1.changeHeight(ground1.getY());
            //50% chance of there being a spike on the ground
            //Same code logic for spike2
            if (r.nextInt(10) > 1) {
                ground1.newSpike(spike1);
                spikeLocation = r.nextInt(ground1.getWidth()) + ground1.getX() - spike1.getWidth();
                spike1.reset(spikeLocation, ground1.getY());
            }

            if (r.nextInt(10) > 1) {
                ground1.firstNewHill(hill1);
                firstHillLocation = r.nextInt(ground1.getWidth()) + ground1.getX() - hill1.getWidth();
                if (Math.abs(firstHillLocation - spikeLocation) > 400) {
                    hill1.reset(firstHillLocation, ground1.getY() - ground1.getGroundHeight() - 10);
                }

            }

            if (r.nextInt(10) > 1) {
                ground1.secondNewHill(hill2);
                secondhillLocation = r.nextInt(ground1.getWidth()) + ground1.getX() - hill2.getWidth();
                if (Math.abs(secondhillLocation - firstHillLocation) > 400 && Math.abs(secondhillLocation - spikeLocation) > 200) {
                    hill2.reset(secondhillLocation, ground1.getY() - ground1.getGroundHeight() - 10);
                }
            }

        } else if (ground2.isScrolledLeft()) {
            ground2.reset(ground1.getTailX(), 0);
            //Upon determination of spike/hill location the distance between nearest object will be determined.
            //If too close it will not be spawned

            if (r.nextInt(10) > 1) {
                ground2.newSpike(spike2);
                spikeLocation = r.nextInt(ground2.getWidth()) - ground2.getX() - spike2.getWidth();
                spike2.reset(spikeLocation, ground2.getY());
            }

            if (r.nextInt(10) > 1) {
                ground2.firstNewHill(hill3);
                firstHillLocation = r.nextInt(ground2.getWidth()) + ground2.getX() - hill3.getWidth();
                if (Math.abs(firstHillLocation - spikeLocation) > 400) {
                    hill3.reset(firstHillLocation, ground2.getY() + ground2.getGroundHeight() - 10);
                }
            }

            if (r.nextInt(10) > 1) {
                ground2.secondNewHill(hill4);
                secondhillLocation = r.nextInt(ground2.getWidth()) + ground2.getX() - hill4.getWidth();
                if (Math.abs(secondhillLocation - firstHillLocation) > 400 && Math.abs(secondhillLocation - spikeLocation) > 200) {
                    hill4.reset(secondhillLocation, ground2.getY() + ground2.getGroundHeight() - 10);
                }
            }

            if (r.nextInt(10) % 2 == 0) {

            }
            //fence2.changeHeight(ground2.getY());
        }

        if (cloud1.isScrolledLeft) {
            cloud1.reset(gameWidth, r.nextInt(120) + 60, r.nextBoolean());
        } else if (cloud2.isScrolledLeft) {
            cloud2.reset(gameWidth, r.nextInt(120) + 60, r.nextBoolean());
        }else if (cloud3.isScrolledLeft) {
            cloud3.reset(gameWidth, r.nextInt(120) + 60, r.nextBoolean());
        }else if (cloud4.isScrolledLeft) {
            cloud4.reset(gameWidth, r.nextInt(120) + 60, r.nextBoolean());
        }


        if (water1.isScrolledLeft()) {
            water1.reset(gameWidth - 2, 0);
        } else if (water2.isScrolledLeft()) {
            water2.reset(gameWidth - 2, 0);
        }
        //Determine which ground is on the right side and make sure hills spawn according to that one

        //when hill is reset, parameter is passed in to determine how high it will stand now

    }

    public void updateClouds(float delta) {

    }

    public void stop() {
        rabbit.die();
        ground1.stop();
        ground2.stop();
        spike1.stop();
        spike2.stop();
        water1.stop();
        water2.stop();
        tree1.stop();
        tree2.stop();
        tree3.stop();
        tree4.stop();
        world.stopScoring();
    }

    public void pause() {
        ground1.stop();
        ground2.stop();
        rabbit.pause();
        spike1.stop();
        spike2.stop();
        water1.stop();
        water2.stop();
        tree1.stop();
        tree2.stop();
        tree3.stop();
        tree4.stop();
    }

    public void resume() {
        ground1.resume();
        ground2.resume();
        spike1.resume();
        spike2.resume();
        water1.resume();
        water2.resume();
        tree1.resume();
        tree2.resume();
        tree3.resume();
        tree4.resume();
    }


    public boolean rabbitCollides() {
        if (spike1.collides(rabbit) || spike2.collides(rabbit)) {
            System.out.println("Hit spike");
            return true;
        } else if (ground1.collides(rabbit) || ground2.collides(rabbit)) {
            System.out.println("Hit ground");
            return true;
        } else if (hill1.collides(rabbit) || hill2.collides(rabbit) || hill3.collides(rabbit) || hill4.collides(rabbit)) {
            System.out.println("Hit hill");
            return true;
        }
        return false;
    }

    public void onRestart() {
        ground1.onReset(0, SCROLL_SPEED);
        ground2.onReset(ground1.getTailX(), SCROLL_SPEED);
        hill1.onReset(ground2.getTailX() - 150, -1000, 140, SCROLL_SPEED);
        hill2.onReset(hill1.getTailX() + HILL_GAP, -1000, 120, SCROLL_SPEED);
        hill3.onReset(ground2.getTailX() - 400, ground1.getY(), 140, SCROLL_SPEED);
        hill4.onReset(hill3.getTailX() + HILL_GAP, -1000, 120, SCROLL_SPEED);
        spike1.onReset(0, SCROLL_SPEED);
        spike2.onReset(0, SCROLL_SPEED);
        water1.onReset(0, SCROLL_SPEED);
        water2.onReset(0, SCROLL_SPEED);
        tree1.onRestart(50, r.nextBoolean());
        tree2.onRestart(tree1.getTailX() + HILL_GAP, r.nextBoolean());
        tree3.onRestart(tree2.getTailX() + HILL_GAP, r.nextBoolean());
        tree4.onRestart(tree3.getTailX() + HILL_GAP, r.nextBoolean());
        cloud1.onRestart(50, r.nextInt(100) + 80, r.nextBoolean());
        cloud2.onRestart(cloud1.getTailX() + CLOUD_GAP, r.nextInt(100) + 80, r.nextBoolean());
        cloud3.onRestart(cloud2.getTailX() + CLOUD_GAP, r.nextInt(100) + 80, r.nextBoolean());
        //So clouds don't double up on Restart.
        cloud4.onRestart(cloud3.getTailX() + CLOUD_GAP, -1000, r.nextBoolean());
    }

    public Hill getHill1() {
        return hill1;
    }

    public Hill getHill2() {
        return hill2;
    }

    public Hill getHill3() {
        return hill3;
    }

    public Hill getHill4() {
        return hill4;
    }

    public Ground getGround1() {
        return ground1;
    }

    public Ground getGround2() {
        return ground2;
    }

    public Spike getSpike1() {
        return spike1;
    }

    public Spike getSpike2() {
        return spike2;
    }

    public Spike getSpike3() {
        return spike3;
    }

    public Water getWater1() {
        return water1;
    }

    public Water getWater2() {
        return water2;
    }

    public Tree getTree1() {
        return tree1;
    }

    public Tree getTree2() {
        return tree2;
    }

    public Tree getTree3() {
        return tree3;
    }

    public Tree getTree4() {
        return tree4;
    }

    public Cloud getCloud1() {
        return cloud1;
    }

    public Cloud getCloud2() {
        return cloud2;
    }

    public Cloud getCloud3() {
        return cloud3;
    }

    public Cloud getCloud4() {
        return cloud4;
    }
}