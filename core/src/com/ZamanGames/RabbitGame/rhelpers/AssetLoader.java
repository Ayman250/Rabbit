package com.ZamanGames.RabbitGame.rhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by Ayman on 6/6/2015.
 */

public class AssetLoader {

    public static Texture ground, dirt, uiBackground;

    public static TextureAtlas spriteSheet, rabbitAnimation;

    public static TextureRegion hill, hillTop, hillBottom, rabbitJumped, background, water,
            policeCar, playButtonDown, playButtonUp, dust, cloud1, cloud2, treeTall, treeShort, settingsGear,
            audioOnButton, audioOnButtonPressed, audioOffButton, audioOffButtonPressed, settings, shoppingCart, pause, restart, hiScores, done, playButton, settingsButtonUp,
            settingsButtonDown, highscoresButtonUp, highscoresButtonDown, restartButtonUp, restartButtonDown, title, enemy1, enemy2, bars, bullet, star, emptyStar, largeStar, one, two, three;

    public static Animation runningAnimation, idleAnimation;

    public static BitmapFont gameFont, scoreFont, resumingFont, bgGameFont, bgScoreFont;

    public static Music bgMusic, click, jumpSound, jailCell, gunShot, policeSiren;

    public static Preferences prefs;


    public static void load() {
        spriteSheet = new TextureAtlas(Gdx.files.internal("data/SpriteSheet.txt"));
        rabbitAnimation = new TextureAtlas(Gdx.files.internal("data/RabbitAnimation.txt"));

        ground = new Texture(Gdx.files.internal("data/ground.png"));

        dirt = new Texture(Gdx.files.internal("data/ground_dirt.png"));

        rabbitJumped = new TextureRegion(rabbitAnimation.findRegion("Jumped"));

        uiBackground = new Texture(Gdx.files.internal("data/UIBackground.png"));

        playButtonUp = new TextureRegion(spriteSheet.findRegion("playButton"));
        playButtonDown = new TextureRegion(spriteSheet.findRegion("playButtonDown"));
        settingsButtonUp = new TextureRegion(spriteSheet.findRegion("settingsButton"));
        settingsButtonDown = new TextureRegion(spriteSheet.findRegion("settingsButtonDown"));
        highscoresButtonUp = new TextureRegion(spriteSheet.findRegion("highscoresButton"));
        highscoresButtonDown = new TextureRegion(spriteSheet.findRegion("highscoresButtonDown"));
        restartButtonUp = new TextureRegion(spriteSheet.findRegion("restartButton"));
        restartButtonDown = new TextureRegion(spriteSheet.findRegion("restartButtonDown"));
        title = new TextureRegion(spriteSheet.findRegion("title4"));

        background = new TextureRegion(spriteSheet.findRegion("background"));
        policeCar = new TextureRegion(spriteSheet.findRegion("policeCar"));
        hillTop = new TextureRegion(spriteSheet.findRegion("hillTop"));
        hill = new TextureRegion(spriteSheet.findRegion("hill"));
        hillBottom = new TextureRegion(spriteSheet.findRegion("hillBottom"));
        cloud1 = new TextureRegion(spriteSheet.findRegion("cloud1"));
        cloud2 = new TextureRegion(spriteSheet.findRegion("cloud2"));
        cloud1 = new TextureRegion(spriteSheet.findRegion("cloud1"));
        cloud2 = new TextureRegion(spriteSheet.findRegion("cloud2"));
        settingsGear = new TextureRegion(spriteSheet.findRegion("settings"));
        audioOffButton = new TextureRegion(spriteSheet.findRegion("audioOffButton"));
        audioOffButtonPressed = new TextureRegion(spriteSheet.findRegion("audioOffButtonPressed"));
        audioOnButton = new TextureRegion(spriteSheet.findRegion("audioOnButton"));
        audioOnButtonPressed = new TextureRegion(spriteSheet.findRegion("audioOnButtonPressed"));
        settings = new TextureRegion(spriteSheet.findRegion("settings"));
        shoppingCart = new TextureRegion(spriteSheet.findRegion("shoppingCart"));
        pause = new TextureRegion(spriteSheet.findRegion("pause"));
        restart = new TextureRegion(spriteSheet.findRegion("restart"));
        hiScores = new TextureRegion(spriteSheet.findRegion("leaderboardsComplex"));
        done = new TextureRegion(spriteSheet.findRegion("arrowLeft"));
        playButton = new TextureRegion(spriteSheet.findRegion("arrowLeft"));
        title = new TextureRegion(spriteSheet.findRegion("title4"));
        enemy1 = new TextureRegion(spriteSheet.findRegion("Enemy G1"));
        enemy2 = new TextureRegion(spriteSheet.findRegion("Enemy G2"));
        bars = new TextureRegion(spriteSheet.findRegion("bars"));
        bullet = new TextureRegion(spriteSheet.findRegion("bullet"));
        star = new TextureRegion(spriteSheet.findRegion("star"));
        emptyStar = new TextureRegion(spriteSheet.findRegion("emptyStar"));
        largeStar = new TextureRegion(spriteSheet.findRegion("largeStar"));
        one = new TextureRegion(spriteSheet.findRegion("1"));
        two = new TextureRegion(spriteSheet.findRegion("2"));
        three = new TextureRegion(spriteSheet.findRegion("3"));

        TextureRegion[] runFrames = {rabbitAnimation.findRegion("Frame01"),
                rabbitAnimation.findRegion("Frame02"), rabbitAnimation.findRegion("Frame03"), rabbitAnimation.findRegion("Frame04"), rabbitAnimation.findRegion("Frame05"),
                rabbitAnimation.findRegion("Frame06"), rabbitAnimation.findRegion("Frame09"), rabbitAnimation.findRegion("Frame08"), rabbitAnimation.findRegion("Frame07")};
//                spriteSheet.findRegion("Frame04"), spriteSheet.findRegion("Frame05"), spriteSheet.findRegion("Frame06"),
//                spriteSheet.findRegion("Frame07"), spriteSheet.findRegion("Frame08"), spriteSheet.findRegion("Frame09"),  };

        TextureRegion[] idleFrames = {rabbitAnimation.findRegion("Idle1"),
                rabbitAnimation.findRegion("Idle2"), rabbitAnimation.findRegion("Idle3"), rabbitAnimation.findRegion("Idle4")};


        runningAnimation = new Animation(.05f, runFrames);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

        idleAnimation = new Animation(.2f, idleFrames);
        idleAnimation.setPlayMode(Animation.PlayMode.LOOP_PINGPONG);


        hillTop.flip(false, true);
        hillBottom.flip(false, true);
        hiScores.flip(false, true);
        background.flip(false, true);
        done.flip(false, true);
        playButtonUp.flip(false, true);
        playButtonDown.flip(false, true);
        settingsButtonUp.flip(false, true);
        settingsButtonDown.flip(false, true);
        highscoresButtonUp.flip(false, true);
        highscoresButtonDown.flip(false, true);
        restartButtonUp.flip(false, true);
        restartButtonDown.flip(false, true);
        audioOnButton.flip(false, true);
        audioOnButtonPressed.flip(false, true);
        audioOffButton.flip(false, true);
        audioOffButtonPressed.flip(false, true);
        one.flip(false, true);
        two.flip(false, true);
        three.flip(false, true);

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/ridaz.mp3"));
        click = Gdx.audio.newMusic(Gdx.files.internal("data/click.ogg"));
        jumpSound = Gdx.audio.newMusic(Gdx.files.internal("data/jumpSound.wav"));
        gunShot = Gdx.audio.newMusic(Gdx.files.internal("data/gunShot.mp3"));
        jailCell = Gdx.audio.newMusic(Gdx.files.internal("data/jail.mp3"));
        policeSiren = Gdx.audio.newMusic(Gdx.files.internal("data/police.mp3"));


//        gameFont = new BitmapFont(Gdx.files.internal("data/gameFont.fnt"), true);


                // Create (or retrieve existing) preferences file
                prefs = Gdx.app.getPreferences("Rabbit Runner");

        ground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        dirt.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

        bgMusic.setLooping(true);
        bgMusic.setVolume(1f);
        click.setVolume(1);

        AssetLoader.bgMusic.play();

//        gameFont.getData().setScale(2f,2f);

        //Provide default high score of 0
        if (!prefs.contains("highScore1"))
            prefs.putInteger("highScore1", 0);

        if (!prefs.contains("highScore2"))
            prefs.putInteger("highScore2", 0);


        if (!prefs.contains("highScore3"))
            prefs.putInteger("highScore3", 0);

    }

    public static void createFonts() {
        FileHandle fontFile = Gdx.files.internal("data/bebasneue.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.flip = true;
        parameter.size = 72;
        gameFont = generator.generateFont(parameter);
        parameter.size = 80;
        bgGameFont = generator.generateFont(parameter);
        bgGameFont.setColor(Color.BLACK);
        parameter.size = 64;
        scoreFont = generator.generateFont(parameter);
        parameter.size = 64;
        bgScoreFont = generator.generateFont(parameter);
        bgScoreFont.setColor(Color.BLACK);
        parameter.size = 72;
        resumingFont = generator.generateFont(parameter);
        generator.dispose();

    }

    public void dispose() {
        ground.dispose();
        dirt.dispose();
        bgMusic.dispose();
        gameFont.dispose();
    }

    // Receives an integer and maps it to the String highScore in prefs
    public static void setHighScore(int val, int num) {
        if (num == 1) {
            prefs.putInteger("highScore1", val);
            prefs.flush();
        } else if (num == 2) {
            prefs.putInteger("highScore2", val);
            prefs.flush();
        } else if (num == 3) {
            prefs.putInteger("highScore3", val);
            prefs.flush();
        }

    }

    // Retrieves the current high score
    public static int getHighScore1() {
        return prefs.getInteger("highScore1");
    }
    public static int getHighScore2() {
        return prefs.getInteger("highScore2");
    }
    public static int getHighScore3() {
        return prefs.getInteger("highScore3");
    }


}
