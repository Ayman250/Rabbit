package com.ZamanGames.RabbitGame.gameworld;

import com.ZamanGames.RabbitGame.gameobjects.Cloud;
import com.ZamanGames.RabbitGame.gameobjects.Ground;
import com.ZamanGames.RabbitGame.gameobjects.Hill;
import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameobjects.ScrollHandler;
import com.ZamanGames.RabbitGame.gameobjects.Scrollable;
import com.ZamanGames.RabbitGame.gameobjects.Spike;
import com.ZamanGames.RabbitGame.gameobjects.Tree;
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
    private Rabbit rabbit;

    private SpriteBatch batch;

    private int gameHeight, gameWidth, groundY;

    private Random r;

//    private float dustTimer, dustTimeLeft;

    private Texture tGround, dirt, tPlayButtonUp, tPlayButtonDown, title, tPlayButton, tSettingsButton, tHighScoresButton;

    private TextureRegion hillTop, hill, hillBottom,  rabbitJumped, spikes, dust, background, treeTall, treeShort, treeToDraw, cloudToDraw;

    private Animation runningAnimation;

    private Scrollable water1, water2;
    private Cloud cloud1, cloud2, cloud3, cloud4;
    private Tree tree1, tree2, tree3, tree4;
    private Hill hill1, hill2, hill3, hill4;
    private Ground ground1, ground2;
    private Spike spike1, spike2, spike3;
    private ScrollHandler scroller;
    private ParallaxBackground parallaxBackground;

    private List<Button> menuButtons, titleButtons, readyButtons, pausedButtons;

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

//        this.settingsButton = ((InputHandler) Gdx.input.getInputProcessor())
//            .getReadySettingsButton();
//        this.hiScoreButton = ((InputHandler) Gdx.input.getInputProcessor())
//                .getMenuHighscoresButton();
//
//        this.playButton =
//                ((InputHandler) Gdx.input.getInputProcessor())
//                        .getTitlePlayButton();

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
        batch.draw(hillTop, hill1.getX(), hill1.getY() + hill1.getHeight() - (19), hill1.getWidth(), 20);
        batch.draw(hillTop, hill2.getX(), hill2.getY() + hill2.getHeight() - (19), hill2.getWidth(), 20);
        batch.draw(hillTop, hill3.getX(), hill3.getY() + hill3.getHeight() - (19), hill3.getWidth(), 20);
        batch.draw(hillTop, hill4.getX(), hill4.getY() + hill4.getHeight() - (19), hill4.getWidth(), 20);
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
        batch.draw(hillBottom, hill1.getX(), hill1.getY(), hill1.getWidth(), 12);
        batch.draw(hillBottom, hill2.getX(), hill2.getY(), hill2.getWidth(), 12);
        batch.draw(hillBottom, hill3.getX(), hill3.getY(), hill3.getWidth(), 12);
        batch.draw(hillBottom, hill4.getX(), hill4.getY(), hill4.getWidth(), 12);
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

//    public void drawWater() {
//        batch.draw(water, water1.getX(), water1.getY(), water1.getWidth(), water1.getHeight(), 0, 0, 8, 1);
//        batch.draw(water, water2.getX(), water2.getY(), water2.getWidth(), water2.getHeight(), 0, 0, 8, 1);
//
//    }

    public void drawTrees() {
        //Randomly determines which tree should be drawn 50/50 chance
        if (tree1.isTall()) {
            treeToDraw = treeTall;
        } else{
            treeToDraw = treeShort;
        }
        batch.draw(treeToDraw, tree1.getX(), tree1.getY(), tree1.getWidth(), tree1.getHeight());
        if (tree2.isTall()) {
            treeToDraw = treeTall;
        } else{
            treeToDraw = treeShort;
        }
        batch.draw(treeToDraw, tree2.getX(), tree2.getY(), tree2.getWidth(), tree2.getHeight());
        if (tree3.isTall()) {
            treeToDraw = treeTall;
        } else{
            treeToDraw = treeShort;
        }
        batch.draw(treeToDraw, tree3.getX(), tree3.getY(), tree3.getWidth(), tree3.getHeight());
        if (tree4.isTall()) {
            treeToDraw = treeTall;
        } else{
            treeToDraw = treeShort;
        }
        batch.draw(treeToDraw, tree4.getX(), tree4.getY(), tree4.getWidth(), tree4.getHeight());
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

    public void drawSpikes() {
        batch.draw(spikes, spike1.getX(), spike1.getY(), spike1.getWidth(), spike1.getHeight());
        batch.draw(spikes, spike2.getX(), spike2.getY(), spike2.getWidth(), spike2.getHeight());
        batch.draw(spikes, spike1.getX(), spike1.getY(), spike1.getWidth(), spike1.getHeight());
        batch.draw(spikes, spike2.getX(), spike2.getY(), spike2.getWidth(), spike2.getHeight());
    }

    public void drawRabbit(float delta, float runTime) {
        if (rabbit.inAir() || world.isReady() || world.isPaused() || world.isMenu() || world.isTitle()){
            batch.draw(rabbitJumped, rabbit.getX(), rabbit.getY(), rabbit.getWidth(), rabbit.getHeight());
        }
        else{
            batch.draw(runningAnimation.getKeyFrame(runTime), rabbit.getX(), rabbit.getY(), rabbit.getWidth(), rabbit.getHeight());
//            dustTimer += delta;
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
                AssetLoader.gameFont.draw(batch, "GAME OVER",
                        gameWidth / 2 - 100, gameHeight / 2);
                AssetLoader.gameFont.draw(batch, "GAME OVER",
                        gameWidth / 2 - 100, gameHeight / 2);
            } else {
                AssetLoader.gameFont.draw(batch, "HIGH SCORE!",
                        gameWidth / 2 - 90, gameHeight / 2 - 40);

                String highScore = "" + AssetLoader.getHighScore();

                int length = ("" + AssetLoader.getHighScore()).length();
                AssetLoader.gameFont.draw(batch, highScore,
                        gameWidth / 2 - (3 * length), gameHeight / 2 - 40 - 50);
                return;
            }
        }


        {
            int length = ("" + world.getScore()).length();
            AssetLoader.scoreFont.draw(batch, "" + world.getScore() + " m",
                    gameWidth / 2 - (3 * length) * 5, gameHeight / 20);
        }
    }

    private void drawBackgroundUI() {
        if ( world.isMenu() || world.isPaused() || world.isReady()) {
            batch.setColor(1F, 1F, 1F, 1F);
            batch.draw(AssetLoader.uiBackground, 320, 180, 640, 360);
        }
    }

    private void drawMenuUI() {
        if (world.isMenu()) {
            for (Button button : menuButtons) {
                System.out.println(button);
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
//        shapeRenderer.rect(spike1.getHitBox().getX(), spike1.getHitBox().getY(), spike1.getHitBox().getWidth(), spike1.getHitBox().getHeight());
//        shapeRenderer.rect(spike2.getHitBox().getX(), spike2.getHitBox().getY(), spike2.getHitBox().getWidth(), spike2.getHitBox().getHeight());
        //Temporary Location
        //drawWater();

        drawBackground();
        drawClouds();

        drawGround();
        drawHills();
        drawHillTops();
        drawHillTops();
        drawHillBottoms();
        drawSpikes();
        drawScore();
        drawRabbit(delta, runTime);
        drawBackgroundUI();
        drawTitle();
        drawPause();
        drawMenuUI();
        drawReady();
        drawRunning();
        drawResuming();

//        shapeRenderer.end();540
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
        spike1 = scroller.getSpike1();
        spike2 = scroller.getSpike2();
        water1 = scroller.getWater1();
        water2 = scroller.getWater2();
        tree1 = scroller.getTree1();
        tree2 = scroller.getTree2();
        tree3 = scroller.getTree3();
        tree4 = scroller.getTree4();
        cloud1 = scroller.getCloud1();
        cloud2 = scroller.getCloud2();
        cloud3 = scroller.getCloud3();
        cloud4 = scroller.getCloud4();
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
        spikes = AssetLoader.spikes;
        runningAnimation = AssetLoader.runningAnimation;
        dust = AssetLoader.dust;
        treeTall = AssetLoader.treeTall;
        treeShort = AssetLoader.treeShort;
        title = AssetLoader.title;
    }

}
