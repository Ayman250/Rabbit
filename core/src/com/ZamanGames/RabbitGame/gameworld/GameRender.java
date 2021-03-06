package com.ZamanGames.RabbitGame.gameworld;

import com.ZamanGames.RabbitGame.gameobjects.Bullet;
import com.ZamanGames.RabbitGame.gameobjects.Cloud;
import com.ZamanGames.RabbitGame.gameobjects.Ground;
import com.ZamanGames.RabbitGame.gameobjects.Hill;
import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameobjects.ScrollHandler;
import com.ZamanGames.RabbitGame.gameobjects.Scrollable;
import com.ZamanGames.RabbitGame.gameobjects.Spike;
import com.ZamanGames.RabbitGame.gameobjects.Weed;
import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;
import com.ZamanGames.RabbitGame.rhelpers.InputHandler;
import com.ZamanGames.RabbitGame.ui.Button;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import java.util.List;
import java.util.Random;

/**
 * Created by Ayman on 6/6/2015.
 */
public class GameRender {
    private GameWorld world;
    private OrthographicCamera cam;
    private Rabbit rabbit, enemy1, enemy2;

    private SpriteBatch batch;

    private int gameHeight, gameWidth, groundY;

    private Random r;

//    private float dustTimer, dustTimeLeft;

    private Texture tGround, dirt, tPlayButtonUp, tPlayButtonDown, tPlayButton, tSettingsButton, tHighScoresButton, blood;

    private TextureRegion hillTop, hill, hillBottom,  rabbitJumped, spikes, dust, background, cloudToDraw, title, tEnemy1, tEnemy2, bars, bullet, star, emptyStar, largeStar, one, two, three, tWeed, high;

    private Animation runningAnimation, idleAnimation;

    private Cloud cloud1, cloud2, cloud3, cloud4;
    private Hill hill1, hill2, hill3, hill4;
    private Ground ground1, ground2;
    private Spike policeCar1, policeCar2, policeCar3;
    private ScrollHandler scroller;
    private Bullet bullet1, bullet2;
    private Weed weed;

    private List<Button> menuButtons, titleButtons, readyButtons, pausedButtons, leaderButtons;

    private Button playButton, settingsButton, audioButton, checkButton, hiScoreButton, pauseButton;

    private ShapeRenderer shapeRenderer;

    public GameRender(GameWorld world, int gameHeight, int gameWidth, int groundY) {
        this.world = world;
        this.gameHeight = gameHeight;
        this.gameWidth = gameWidth;
        this.groundY = groundY;

        cam = new OrthographicCamera();
        cam.setToOrtho(true, this.gameWidth, this.gameHeight);
        //cam.setToOrtho(true, 1080, 1920);

        batch = new SpriteBatch();
        batch.setProjectionMatrix(cam.combined);

        this.menuButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getMenuButtons();
        this.titleButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getTitleButtons();
        this.readyButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getReadyButtons();
        this.pausedButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getPausedButtons();
        this.pauseButton = ((InputHandler) Gdx.input.getInputProcessor())
                .getPauseButton();
        this.leaderButtons = ((InputHandler) Gdx.input.getInputProcessor())
                .getLeaderButtons();


        initGameObjects();
        initAssets();

        shapeRenderer = new ShapeRenderer();


        r = new Random();

//        dustTimer = 0;
//        dustTimeLeft = 0;

     }

    public void drawHillTops() {
        //convoluted monkey ass faggot way of drawing hills
        //width of hilltop should match width of hill and the height should be experiments with (2*width seems to work well)
        batch.draw(hillTop, hill1.getX(), hill1.getY() + hill1.getHeight() - (19), hill1.getWidth(), 21);
        batch.draw(hillTop, hill2.getX(), hill2.getY() + hill2.getHeight() - (19), hill2.getWidth(), 21);
        batch.draw(hillTop, hill3.getX(), hill3.getY() + hill3.getHeight() - (19), hill3.getWidth(), 21);
        batch.draw(hillTop, hill4.getX(), hill4.getY() + hill4.getHeight() - (19), hill4.getWidth(), 21);
    }

    public void drawHills() {
        //Please try to remember how this retarded way of drawing hills works!
        //
        batch.draw(hill, hill1.getX(), hill1.getY(), hill1.getWidth(), hill1.getHeight());
        batch.draw(hill, hill2.getX(), hill2.getY(), hill2.getWidth(), hill2.getHeight());
        batch.draw(hill, hill3.getX(), hill3.getY(), hill3.getWidth(), hill3.getHeight());
        batch.draw(hill, hill4.getX(), hill4.getY(), hill4.getWidth(), hill4.getHeight());
    }

    public void drawHillBottoms() {
        //draw((x coordinates of top and bottom match, y is shifted by the width (last parameter) so that it sits nicely on top of hill
        //width of hilltop should match width of hill and the height should be experiments with (2*width seems to work well)
        batch.draw(hillBottom, hill1.getX(), hill1.getY()-6, hill1.getWidth(), 20);
        batch.draw(hillBottom, hill2.getX(), hill2.getY()-6, hill2.getWidth(), 20);
        batch.draw(hillBottom, hill3.getX(), hill3.getY()-6, hill3.getWidth(), 20);
        batch.draw(hillBottom, hill4.getX(), hill4.getY()-6, hill4.getWidth(), 20);
    }

    public void drawBullets() {
        batch.draw(bullet, bullet1.getX(), bullet1.getY(), bullet1.getWidth()/2, bullet1.getHeight()/2);
        batch.draw(bullet, bullet2.getX(), bullet2.getY(), bullet2.getWidth()/2, bullet2.getHeight()/2);

    }

    public void drawGround() {
        switch (ground1.getGroundHeight()){
            case 0:
                batch.draw(tGround, ground1.getX(), ground1.getY() - 12, ground1.getWidth(), ground1.getHeight() + 12, 0, 0, 8, 1);
                break;
            case 1:
                batch.draw(tGround, ground1.getX(), ground1.getY()-12, ground1.getWidth(), ground1.getHeight() + 12, 0, 0, 8, 1);
                batch.draw(dirt, ground1.getX(), ground1.getY()+ground1.getHeight(), ground1.getWidth(), ground1.getHeight(), 0, 0, 8, 1);
                break;
            case 2:
                batch.draw(tGround, ground1.getX(), ground1.getY()-12, ground1.getWidth(), ground1.getHeight() + 12, 0, 0, 8, 1);
                batch.draw(dirt, ground1.getX(), ground1.getY()+ground1.getHeight(), ground1.getWidth(), ground1.getHeight(), 0, 0, 8, 1);
                batch.draw(dirt, ground1.getX(), ground1.getY()+2*ground1.getHeight(), ground1.getWidth(), ground1.getHeight(), 0, 0, 8, 1);
                break;
        }



        switch (ground2.getGroundHeight()){
            case 0:
                batch.draw(tGround, ground2.getX(), ground2.getY()-12, ground2.getWidth(), ground2.getHeight() + 12, 0, 0, 8, 1);
                //System.out.println("ground2 1");
                break;
            case 1:
                batch.draw(tGround, ground2.getX(), ground2.getY()-12, ground2.getWidth(), ground2.getHeight() + 12, 0, 0, 8, 1);
                batch.draw(dirt, ground2.getX(), ground2.getY()+ground2.getHeight(), ground2.getWidth(), ground2.getHeight(), 0, 0, 8, 1);
                //System.out.println("ground2 2");
                break;
            case 2:
                batch.draw(tGround, ground2.getX(), ground2.getY()-12, ground2.getWidth(), ground2.getHeight() + 12, 0, 0, 8, 1);
                batch.draw(dirt, ground2.getX(), ground2.getY()+ground2.getHeight(), ground2.getWidth(), ground2.getHeight(), 0, 0, 8, 1);
                batch.draw(dirt, ground2.getX(), ground2.getY()+2*ground2.getHeight(), ground2.getWidth(), ground2.getHeight(), 0, 0, 8, 1);
                //System.out.println("ground2 3");
                break;
        }
    }


    private void drawClouds() {
        if (cloud1.is1()) {
            cloudToDraw = AssetLoader.cloud1;
        } else {
            cloudToDraw = AssetLoader.cloud2;
        }
        batch.draw(cloudToDraw, cloud1.getX(), cloud1.getY(), cloud1.getWidth(), cloud1.getHeight());
        if (cloud2.is1()) {
            cloudToDraw = AssetLoader.cloud1;
        } else {
            cloudToDraw = AssetLoader.cloud2;
        }
        batch.draw(cloudToDraw, cloud2.getX(), cloud2.getY(), cloud2.getWidth(), cloud2.getHeight());
        if (cloud3.is1()) {
            cloudToDraw = AssetLoader.cloud1;
        } else {
            cloudToDraw = AssetLoader.cloud2;
        }
        batch.draw(cloudToDraw, cloud3.getX(), cloud3.getY(), cloud3.getWidth(), cloud3.getHeight());
        if (cloud4.is1()) {
            cloudToDraw = AssetLoader.cloud1;
        } else {
            cloudToDraw = AssetLoader.cloud2;
        }
        batch.draw(cloudToDraw, cloud4.getX(), cloud4.getY(), cloud4.getWidth(), cloud4.getHeight());

    }

    public void drawPoliceCars() {
        batch.draw(spikes, policeCar1.getX(), policeCar1.getY(), policeCar1.getWidth(), policeCar1.getHeight());
        batch.draw(spikes, policeCar2.getX(), policeCar2.getY(), policeCar2.getWidth(), policeCar2.getHeight());
        batch.draw(spikes, policeCar1.getX(), policeCar1.getY(), policeCar1.getWidth(), policeCar1.getHeight());
        batch.draw(spikes, policeCar2.getX(), policeCar2.getY(), policeCar2.getWidth(), policeCar2.getHeight());
    }

    private void drawEnemies() {
        batch.draw(tEnemy1, enemy1.getX(), enemy1.getY(), enemy1.getWidth(), enemy1.getHeight());
        batch.draw(tEnemy2, enemy2.getX(), enemy2.getY(), enemy2.getWidth(), enemy2.getHeight());
    }
    public void drawRabbit(float delta, float runTime) {
        if (world.isReady() || world.isTitle() || world.isRising() || world.isFalling() || world.isHigh()) {
            batch.draw(idleAnimation.getKeyFrame(runTime), rabbit.getX(), rabbit.getY(), rabbit.getWidth(), rabbit.getHeight());
        } else if (rabbit.inAir() || world.isPaused() || world.isMenu() || world.isDyingHill() || world.isGameOver()) {
            batch.draw(rabbitJumped, rabbit.getX(), rabbit.getY(), rabbit.getWidth(), rabbit.getHeight());
        } else {
            batch.draw(runningAnimation.getKeyFrame(runTime), rabbit.getX(), rabbit.getY(), rabbit.getWidth(), rabbit.getHeight());
//          dustTimer += delta;
//            if (dustTimer > 1f) {
//                dustTimer -= 1f;
//                dustTimeLeft = .3f;
//            }
//            if (dustTimeLeft > 0) {
//                dustTimeLeft -= delta;
//                batch.draw(dust, rabbit.getX() - 45, rabbit.getY() + 12, 72, -32);
//            }
//            if (dustTimeLeft < 0) {
//                dustTimeLeft = 0;
//            }

        }
    }

    private void drawScore() {
        if (world.isTitle()) {
            return;
        } else if (world.isGameOver() || world.isHighScore()) {
            if (world.isGameOver()) {
                drawStars();
                AssetLoader.gameFont.draw(batch, "GAME OVER",
                        gameWidth / 2 - 130, gameHeight / 2 + 80);
                AssetLoader.gameFont.draw(batch, "High Score: " + AssetLoader.getHighScore1(),
                        gameWidth / 2 - 180, gameHeight / 2 - 20);
            } else {
                AssetLoader.gameFont.draw(batch, "HIGH SCORE!",
                        gameWidth / 2 - 90, gameHeight / 2 - 40);

                String highScore = "" + AssetLoader.getHighScore1();

                int length = ("" + AssetLoader.getHighScore1()).length();
                AssetLoader.gameFont.draw(batch, highScore,
                        gameWidth / 2 - (3 * length), gameHeight / 2 - 90);
                return;
            }
        }


        {
            int length = ("" + world.getScore()).length();
            AssetLoader.bgScoreFont.draw(batch, "" + world.getScore() + " m",
                    gameWidth / 2 - (3 * length) * 5 + 2, gameHeight / 20 + 2);

            AssetLoader.scoreFont.draw(batch, "" + world.getScore() + " m",
                    gameWidth / 2 - (3 * length) * 5, gameHeight / 20);
        }
    }
    private void drawStars() {
        if (world.getScore() > 500) {
            batch.draw(star, gameWidth /2 - 180, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
            batch.draw(largeStar, gameWidth /2 - 45, gameHeight /2 - 100, star.getRegionWidth() * 2 / 4 + 5, -(star.getRegionHeight() * 2 / 4 + 5));
            batch.draw(star, gameWidth /2 + 80, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
        } else if (world.getScore() > 250) {
            batch.draw(star, gameWidth /2 - 180, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
            batch.draw(largeStar, gameWidth /2 - 45, gameHeight /2 - 100, star.getRegionWidth() * 2 / 4 + 5, -(star.getRegionHeight() * 2 / 4 + 5));
            batch.draw(emptyStar, gameWidth /2 + 80, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
        } else {
            batch.draw(star, gameWidth /2 - 180, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
            batch.draw(emptyStar, gameWidth /2 - 45, gameHeight /2 - 100, star.getRegionWidth() * 2 / 4 + 5, -(star.getRegionHeight() * 2 / 4 + 5));
            batch.draw(emptyStar, gameWidth /2 + 80, gameHeight /2 - 50, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
        }


    }
    private void drawBackgroundUI() {
        if (world.isGameOver() && world.getCollidedPolice()) {
            batch.draw(bars, 50, -20, bars.getRegionWidth() * 3 / 4, bars.getRegionHeight() * 3 / 4);
        }

        if ( world.isMenu() || world.isPaused() || world.isReady() || world.isLeaderBoard() || world.isHighScore() || world.isGameOver()) {
            batch.setColor(1F, 1F, 1F, 1F);
            batch.draw(AssetLoader.uiBackground, 320, 180, 640, 360);
        }

    }

    private void drawLeaderBoard() {
        if (world.isLeaderBoard()) {
            String highScore1 = "" + AssetLoader.getHighScore1();
            String highScore2 = "" + AssetLoader.getHighScore2();
            String highScore3 = "" + AssetLoader.getHighScore3();

            for (Button button : leaderButtons) {
                button.draw(batch);
            }
            //draw stars on top of leaderboard ui
            batch.draw(star, gameWidth /2 - 180, gameHeight /2 - 100, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));
            batch.draw(largeStar, gameWidth /2 - 45, gameHeight /2 - 150, star.getRegionWidth() * 2 / 4 + 5, -(star.getRegionHeight() * 2 / 4 + 5));
            batch.draw(star, gameWidth /2 + 80, gameHeight /2 - 100, star.getRegionWidth() * 2 / 4, -(star.getRegionHeight() * 2 / 4));

            //Draw Numbers and display HighScores
            batch.draw(one, 512, 280);
            int length1 = highScore1.length();
            AssetLoader.gameFont.draw(batch, highScore1,
                    620, 280);
            batch.draw(two, 512, 360);
            int length2 = highScore2.length();
            AssetLoader.gameFont.draw(batch, highScore2,
                    620, 360);
            batch.draw(three, 512, 440);
            int length3 = highScore3.length();
            AssetLoader.gameFont.draw(batch, highScore3,
                    620, 440);
        }
    }

    private void drawMenuUI() {
        if (world.isMenu()) {
            for (Button button : menuButtons) {
                button.draw(batch);
            }
        }
    }

    public void drawRunning() {
        if (world.isRunning()) {
            batch.setColor(1F, 1F, 1F, 1F);
            pauseButton.draw(batch);
        }
    }

    public void drawTitle() {
        if (world.isTitle()) {
            batch.draw(title, 16, 300, 1274, -216);
            for (Button button : titleButtons) {
                button.draw(batch);
            }

        }
    }

    public void drawPause() {
        if (world.isPaused()) {
            batch.setColor(1F, 1F, 1F, 1F);
            AssetLoader.gameFont.draw(batch, "Touch To Resume",
                    gameWidth / 2 - 180, gameHeight / 2 - 120);
            for (Button button : pausedButtons) {
                button.draw(batch);
            }
            batch.setColor(0.5F, 0.5F, 0.5F, 1F);
        }

    }

    public void drawReady() {
        if (world.isReady()) {
            AssetLoader.gameFont.draw(batch, "TOUCH TO START!",
                    gameWidth / 2 - 180, gameHeight / 2 - 120);
            for (Button button : readyButtons) {
                button.draw(batch);
            }
        }
    }

    private void drawBackground() {
        batch.draw(background, 0, gameHeight, gameWidth, -gameHeight);
    }

    public void drawResuming(){
        if (world.isResuming()) {
            batch.setColor(0.5F, 0.5F, 0.5F, 1F);
            AssetLoader.resumingFont.draw(batch, "" + world.getResumingTime(),
                    gameWidth / 2 - 30, gameHeight / 2 - 90);

        }
    }

    public void drawDyingPolice() {
        if (world.isDyingPolice()) {
            batch.draw(bars, 50, -20, bars.getRegionWidth()*3/4, bars.getRegionHeight()*3/4 );
        }
    }

    public void drawDyingHill() {
        if (world.isDyingHill()) {

        }
    }

    public void drawBlood() {
        if (world.isBloody()) {
            batch.draw(blood, 0, 0, 1280, 720);
        }
    }


    public void drawWeed() {
        if (!world.isHigh()) {
            batch.draw(tWeed, weed.getX(), weed.getY(), weed.getWidth(), weed.getHeight());
        }
    }

    public void drawHigh() {
        if (world.isHigh() || world.isRising()) {
            batch.draw(high, 0, gameHeight, gameWidth, -gameHeight);
        }
    }
    //might use runTime later for animations
    public void render(float delta, float runTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.begin();
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//        shapeRenderer.setColor(Color.BLUE);
//        shapeRenderer.rect(rabbit.getHitBox().getX(), rabbit.getHitBox().getY(), rabbit.getHitBox().getWidth(), rabbit.getHitBox().getHeight());
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(ground1.getHitBox().getX(), ground1.getHitBox().getY(), ground1.getHitBox().getWidth(), ground1.getHitBox().getHeight());
//        shapeRenderer.rect(ground2.getHitBox().getX(), ground2.getHitBox().getY(), ground2.getHitBox().getWidth(), ground2.getHitBox().getHeight());
//        shapeRenderer.setColor(Color.PINK);
//        shapeRenderer.rect(hill1.getHitBox().getX(), hill1.getHitBox().getY(), hill1.getHitBox().getWidth(), hill1.getHitBox().getHeight());
//        shapeRenderer.rect(hill2.getHitBox().getX(), hill2.getHitBox().getY(), hill2.getHitBox().getWidth(), hill2.getHitBox().getHeight());
//        shapeRenderer.setColor(Color.YELLOW);
//        shapeRenderer.rect(hill3.getHitBox().getX(), hill3.getHitBox().getY(), hill3.getHitBox().getWidth(), hill3.getHitBox().getHeight());
//        shapeRenderer.rect(hill4.getHitBox().getX(), hill4.getHitBox().getY(), hill4.getHitBox().getWidth(), hill4.getHitBox().getHeight());
//        shapeRenderer.setColor(Color.PURPLE);
//        shapeRenderer.rect(policeCar1.getHitBox().getX(), policeCar1.getHitBox().getY(), policeCar1.getHitBox().getWidth(), policeCar1.getHitBox().getHeight());
//        shapeRenderer.rect(policeCar2.getHitBox().getX(), policeCar2.getHitBox().getY(), policeCar2.getHitBox().getWidth(), policeCar2.getHitBox().getHeight());
        //Temporary Location
        //drawWater();

        drawBackground();
        drawClouds();
        drawWeed();
        drawGround();
        drawHills();
        drawHillBottoms();
        drawHillTops();
        drawPoliceCars();
        drawScore();
        drawEnemies();
        drawRabbit(delta, runTime);
        drawDyingPolice();
        drawBlood();
        drawBackgroundUI();
        drawBlood();
        drawTitle();
        drawPause();
        drawMenuUI();
        drawReady();
        drawRunning();
        drawLeaderBoard();
        drawResuming();
        drawBullets();
        drawHigh();
        //Shitty code to handle rabbit dying and bars showing over score
        if (rabbit.isDead()) {
            drawScore();
        }
//      -  shapeRenderer.end();540
        batch.end();

    }

    public void initGameObjects() {
        rabbit = world.getRabbit();
        scroller = world.getScroller();
        hill1 = scroller.getHill1();
        hill2 = scroller.getHill2();
        hill3 = scroller.getHill3();
        hill4 = scroller.getHill4();
        ground1 = scroller.getGround1();
        ground2 = scroller.getGround2();
        policeCar1 = scroller.getPoliceCar1();
        policeCar2 = scroller.getPoliceCar2();
        cloud1 = scroller.getCloud1();
        cloud2 = scroller.getCloud2();
        cloud3 = scroller.getCloud3();
        cloud4 = scroller.getCloud4();
        enemy1 = world.getEnemy1();
        enemy2 = world.getEnemy2();
        bullet1 = scroller.getBullet1();
        bullet2 = scroller.getBullet2();
        weed = scroller.getWeed();
        //parallaxBackground = new ParallaxBackground();
    }

    public void initAssets() {
        hillTop = AssetLoader.hillTop;
        hill = AssetLoader.hill;
        hillBottom = AssetLoader.hillBottom;
        background = AssetLoader.background;
        tGround  = AssetLoader.ground;
        rabbitJumped = AssetLoader.rabbitJumped;
        dirt = AssetLoader.dirt;
        spikes = AssetLoader.policeCar;
        runningAnimation = AssetLoader.runningAnimation;
        idleAnimation = AssetLoader.idleAnimation;
        dust = AssetLoader.dust;
        title = AssetLoader.title;
        tEnemy1 = AssetLoader.enemy1;
        tEnemy2 = AssetLoader.enemy2;
        bars = AssetLoader.bars;
        bullet = AssetLoader.bullet;
        star = AssetLoader.star;
        emptyStar = AssetLoader.emptyStar;
        largeStar = AssetLoader.largeStar;
        one = AssetLoader.one;
        two = AssetLoader.two;
        three = AssetLoader.three;
        blood = AssetLoader.blood;
        tWeed = AssetLoader.weed;
        high = AssetLoader.high;
    }
}
