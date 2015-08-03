package com.ZamanGames.RabbitGame.rhelpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
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

    public static TextureAtlas spriteSheet;

    public static TextureRegion hill, hillTop, hillBottom, rabbitJumped, background, water,
            spikes, playButtonDown, playButtonUp, dust, cloud1, cloud2, treeTall, treeShort, settingsGear,
            audioOff, audioOn, settings, shoppingCart, pause, restart, hiScores, done, playButton, settingsButtonUp,
            settingsButtonDown, highscoresButtonUp, highscoresButtonDown, restartButtonUp, restartButtonDown, title;

    public static Animation runningAnimation;

    public static BitmapFont gameFont, scoreFont, resumingFont;

    public static Music bgMusic, click;

    public static Preferences prefs;


    public static void load() {
        spriteSheet = new TextureAtlas(Gdx.files.internal("data/SpriteSheet.txt"));

        ground = new Texture(Gdx.files.internal("data/ground.png"));

        dirt = new Texture(Gdx.files.internal("data/ground_dirt.png"));

        rabbitJumped = new TextureRegion(spriteSheet.findRegion("Frame01"));

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
        spikes = new TextureRegion(spriteSheet.findRegion("Spikes"));
        hillTop = new TextureRegion(spriteSheet.findRegion("hillTop"));
        hill = new TextureRegion(spriteSheet.findRegion("hill"));
        hillBottom = new TextureRegion(spriteSheet.findRegion("hillBottom"));
        cloud1 = new TextureRegion(spriteSheet.findRegion("cloud1"));
        cloud2 = new TextureRegion(spriteSheet.findRegion("cloud2"));
        cloud1 = new TextureRegion(spriteSheet.findRegion("cloud1"));
        cloud2 = new TextureRegion(spriteSheet.findRegion("cloud2"));
        settingsGear = new TextureRegion(spriteSheet.findRegion("settings"));
        audioOff = new TextureRegion(spriteSheet.findRegion("audioOff"));
        audioOn = new TextureRegion(spriteSheet.findRegion("audioOn"));
        settings = new TextureRegion(spriteSheet.findRegion("settings"));
        shoppingCart = new TextureRegion(spriteSheet.findRegion("shoppingCart"));
        pause = new TextureRegion(spriteSheet.findRegion("pause"));
        restart = new TextureRegion(spriteSheet.findRegion("restart"));
        hiScores = new TextureRegion(spriteSheet.findRegion("leaderboardsComplex"));
        done = new TextureRegion(spriteSheet.findRegion("checkmark"));
        playButton = new TextureRegion(spriteSheet.findRegion("checkmark"));

        TextureRegion[] runFrames = {spriteSheet.findRegion("Frame01"), spriteSheet.findRegion("Frame02"), spriteSheet.findRegion("Frame03"),
                spriteSheet.findRegion("Frame04"), spriteSheet.findRegion("Frame05"), spriteSheet.findRegion("Frame06"),
                spriteSheet.findRegion("Frame07"), spriteSheet.findRegion("Frame08"), spriteSheet.findRegion("Frame09"),  };

        runningAnimation = new Animation(.03f, runFrames);
        runningAnimation.setPlayMode(Animation.PlayMode.LOOP);

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

        bgMusic = Gdx.audio.newMusic(Gdx.files.internal("data/bgMusic.ogg"));
        click = Gdx.audio.newMusic(Gdx.files.internal("data/click.ogg"));

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
        if (!prefs.contains("highScore")) {
            prefs.putInteger("highScore", 0);
        }

}

    public static void createFonts() {
        FileHandle fontFile = Gdx.files.internal("data/bebasneue.ttf");
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(fontFile);
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.flip = true;
        parameter.size = 72;
        gameFont = generator.generateFont(parameter);
        parameter.size = 64;
        scoreFont = generator.generateFont(parameter);
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
    public static void setHighScore(int val) {
        prefs.putInteger("highScore", val);
        prefs.flush();
    }

    // Retrieves the current high score
    public static int getHighScore() {
        return prefs.getInteger("highScore");
    }


}
