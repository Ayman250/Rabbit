package com.ZamanGames.RabbitGame.gameworld;

import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameobjects.ScrollHandler;
import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;

/**
 * Created by Ayman on 6/6/2015.
 */
public class GameWorld {

    private Rabbit rabbit;
    private ScrollHandler scroller;
    private int rabbitWidth, rabbitHeight;

    private int gameWidth, gameHeight, groundY, score;
    private float scoreCounter, runTime = 0, initRHeight;
    private double resumingCounter;

    private boolean scoring, soundOn;

    private GameState currentState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, PAUSED, RESUMING
    }

    public GameWorld(int gameWidth, int gameHeight, float midPointY, int groundY) {


        currentState = GameState.READY;

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.groundY = groundY;

        rabbitWidth = 85;
        rabbitHeight = 90;
        rabbit = new Rabbit(this.gameWidth/4-rabbitWidth/40*9, this.groundY, rabbitWidth, rabbitHeight,this.groundY);
        scroller = new ScrollHandler(this, this.gameWidth, this.gameHeight, this.groundY);

        score = 0;
        scoreCounter = 0;

        scoring = true;

        soundOn = true;

        resumingCounter = 3;



    }

    public void update(float delta) {

        runTime += delta;
        switch (currentState) {
            case READY:
            case MENU:
                updateMenu(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case RESUMING:
                updateResuming(delta);
                break;
            case PAUSED:
                updatePaused(delta);
            default:
                break;
        }
    }

    private void updateReady(float delta) {
        rabbit.updateReady(runTime);
    }


    public void updateRunning(float delta) {
        if (delta > .15f) {
            delta = .15f;
        }

        rabbit.update(delta);
        scroller.update(delta);
        //adds point every 1/20th of a second
        scoreCounter += delta;
        if (scoring) {
            if (scoreCounter >= 1 / 10f) {
                scoreCounter -= 1 / 10f;
                score++;
            }
        }

        if (scroller.rabbitCollides()) {
            scroller.stop();
            stopMusic();
            rabbit.die();
            currentState = GameState.GAMEOVER;

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                currentState = GameState.HIGHSCORE;
            }
        }


    }

    public void updateMenu(float delta) {

    }

    public void updatePaused(float delta) {
        scroller.stop();
    }

    public void updateResuming(float delta) {
        resumingCounter -= delta;
        if (resumingCounter <= 0) {
            resume();
            resumingCounter = 3;
        }

    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void start() {
        currentState = GameState.RUNNING;
        AssetLoader.bgMusic.play();
    }

    public void startResuming() {
        currentState = GameState.RESUMING;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        scoring = true;
        rabbit.onRestart(this.groundY);
        scroller.onRestart();
        currentState = GameState.READY;

    }

    public void pause() {
        scroller.pause();
        rabbit.pause();
        stopScoring();
        pauseMusic();
        currentState = GameState.PAUSED;
    }

    public void resume() {
        scroller.resume();
        rabbit.resume();
        resumeScoring();
        playMusic();
        currentState = GameState.RUNNING;
    }

    public void menu() {
        currentState = GameState.MENU;
    }

    public boolean isSoundOn() {
        return soundOn;
    }

    public void startSound() {
        AssetLoader.bgMusic.setVolume(1);
        AssetLoader.click.setVolume( 1);
        soundOn = true;
    }

    public void stopSound() {
        AssetLoader.bgMusic.setVolume(0);
        AssetLoader.click.setVolume(0);
        soundOn = false;
    }

    public void stopMusic() {
        AssetLoader.bgMusic.stop();
    }

    public void pauseMusic() {
        AssetLoader.bgMusic.pause();
    }

    public void playMusic() {
        AssetLoader.bgMusic.play();
    }

    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }

    public boolean isReady() {
        return currentState == GameState.READY;
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }

    public boolean isRunning() {
        return currentState == GameState.RUNNING;
    }

    public boolean isMenu() {
        return currentState == GameState.MENU;
    }

    public boolean isResuming() {
        return currentState == GameState.RESUMING;
    }

    public boolean isPaused() {
        return currentState == GameState.PAUSED;
    }

    //Used in case scoring needs to be resumed while game is still running.
    public void startScoring() {
        scoring = true;
    }

    //Used if rabbit dies or game is paused
    public void stopScoring() {
        scoring = false;
    }

    public void resumeScoring () {
        scoring = true;
    }

    public Rabbit getRabbit() {
        return rabbit;
    }

    public ScrollHandler getScroller() {
        return scroller;
    }

    public int getGameWidth () {
        return this.gameWidth;
    }

    public int getGameHeight() {
        return this.gameHeight;

    }

    public int getResumingTime() {
        return (int) Math.ceil(resumingCounter);
    }
    public int getScore() {
        return score;
    }

}
