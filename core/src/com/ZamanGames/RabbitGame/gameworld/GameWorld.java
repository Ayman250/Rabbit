package com.ZamanGames.RabbitGame.gameworld;

import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameobjects.ScrollHandler;
import com.ZamanGames.RabbitGame.gameobjects.Scrollable;
import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;

/**
 * Created by Ayman on 6/6/2015.
 */
public class GameWorld {

    private Rabbit rabbit, enemy1, enemy2;
    private ScrollHandler scroller;
    private int rabbitWidth, rabbitHeight;

    private int gameWidth, gameHeight, groundY, score;
    private float scoreCounter, runTime = 0, initRHeight;
    private double resumingCounter, dyingCounter;

    private boolean scoring, soundOn, collidedPolice, shouldShoot;

    private GameState currentState, previousState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, PAUSED, RESUMING, TITLE, DYINGPOLICE, DYINGHILL
    }

    public GameWorld(int gameWidth, int gameHeight, float midPointY, int groundY) {


        currentState = GameState.TITLE;

        this.gameWidth = gameWidth;
        this.gameHeight = gameHeight;
        this.groundY = groundY;

        rabbitWidth = 99;
        rabbitHeight = 129;
        rabbit = new Rabbit(-99, 300, rabbitWidth, rabbitHeight,this.groundY, false);
        enemy1 = new Rabbit(-199, 150, rabbitWidth, rabbitHeight,this.groundY, true);
        enemy2 = new Rabbit(-299, 150, rabbitWidth, rabbitHeight,this.groundY, true);
        scroller = new ScrollHandler(this, this.gameWidth, this.gameHeight, this.groundY);

        score = 0;
        scoreCounter = 0;

        scoring = true;

        soundOn = true;

        resumingCounter = 3;

        dyingCounter = 3;

        collidedPolice = false;

        shouldShoot = false;



    }

    public void update(float delta) {
        runTime += delta;
        scroller.updateClouds(delta);
        switch (currentState) {
            case READY:
                updateReady(runTime);
            case MENU:
                updateMenu(delta);
                break;
            case RUNNING:
                updateRunning(delta);
                break;
            case DYINGHILL:
                updateDyingHillUpdate(delta);
                break;
            case DYINGPOLICE:
                updateDyingPoliceCar(delta);
                break;
            case RESUMING:
                updateResuming(delta);
                break;
            case TITLE:
                updateTitle(runTime);
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
        System.out.println(enemy1.getX() + "," + enemy1.getY());
        System.out.println(enemy2.getX() + "," + enemy2.getY());
        if (delta > .15f) {
            delta = .15f;
        }

        rabbit.update(delta);
        enemy1.update(delta);
        enemy2.update(delta);
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
            previousState = currentState;
            if (collidedPolice) {
                currentState = GameState.DYINGPOLICE;
            } else {
                currentState = GameState.DYINGHILL;
            }

            if (score > AssetLoader.getHighScore()) {
                AssetLoader.setHighScore(score);
                previousState = currentState;
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

    public void updateTitle(float runTime) {
        rabbit.updateTitle(runTime);
    }

    public void updateDyingHillUpdate(float delta) {
        dyingCounter -= delta;
        if (dyingCounter <= 0) {
            currentState = GameState.GAMEOVER;
        }
        if (dyingCounter <= 2) {
            shouldShoot = true;
            AssetLoader.gunShot.play();
        }
    }

    public void updateDyingPoliceCar(float delta) {
        AssetLoader.policeSiren.play();
        dyingCounter -= delta;
        if (dyingCounter <= 0) {
            currentState = GameState.GAMEOVER;
        }
        if (dyingCounter <= 1.5) {
            AssetLoader.jailCell.play();
        }
    }

    public void ready() {
        currentState = GameState.READY;
    }

    public void start() {
        previousState = currentState;
        currentState = GameState.RUNNING;

    }

    public void startResuming() {
        previousState = currentState;
        currentState = GameState.RESUMING;
    }

    public void restart() {
        previousState = currentState;
        currentState = GameState.READY;
        score = 0;
        scoring = true;
        rabbit.onRestart(this.groundY, -99, 300);
        enemy1.onRestart(this.groundY, -199, 150);
        enemy2.onRestart(this.groundY, -299, 150);
        scroller.onRestart();
        currentState = GameState.READY;
        AssetLoader.bgMusic.play();
        dyingCounter = 3;
        resumingCounter = 3;

    }

    public void pause() {
        scroller.pause();
        rabbit.pause();
        stopScoring();
        pauseMusic();
        previousState = currentState;
        currentState = GameState.PAUSED;
    }

    public void resume() {
        scroller.resume();
        rabbit.resume();
        resumeScoring();
        playMusic();
        previousState = currentState;
        currentState = GameState.RUNNING;
    }

    public void menu() {
        previousState = currentState;
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

    public boolean isTitle() {
        return  currentState == GameState.TITLE;
    }

    public boolean isDyingPolice() {
        return currentState == GameState.DYINGPOLICE;
    }

    public boolean isDyingHill() {
        return currentState == GameState.DYINGHILL;
    }

    public GameState getPreviousState() {
        return previousState;
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

    public Rabbit getEnemy1() {
        return enemy1;
    }

    public Rabbit getEnemy2() {
        return enemy2;
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

    public boolean getCollidedPolice() {
        return collidedPolice;
    }

    public boolean getshouldShoot () {
        return shouldShoot;
    }

    public void setCollidedPolice(boolean collidedPolice) {
        this.collidedPolice = collidedPolice;
    }

}
