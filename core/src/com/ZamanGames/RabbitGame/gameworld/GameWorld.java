package com.ZamanGames.RabbitGame.gameworld;

import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameobjects.ScrollHandler;
import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.MathUtils;

/**
 * Created by Ayman on 6/6/2015.
 */
public class GameWorld {

    private Rabbit rabbit, enemy1, enemy2;
    private ScrollHandler scroller;
    private int rabbitWidth, rabbitHeight;

    private int gameWidth, gameHeight, groundY, score;
    private float scoreCounter, runTime = 0, initRHeight, highCounter, risingCounter, fallingCounter;
    private double resumingCounter, dyingCounter;

    private boolean scoring, soundOn, collidedPolice, shouldShoot, bloody, high, rising, falling;

    private GameState currentState, previousState;

    public enum GameState {
        MENU, READY, RUNNING, GAMEOVER, HIGHSCORE, PAUSED, RESUMING, TITLE, DYINGPOLICE, DYINGHILL, LEADERBOARD, RISING, FALLING, HIGH;
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
        resumingCounter = 3;
        dyingCounter = 2;
        highCounter = 0;

        scoring = true;
        soundOn = true;
        collidedPolice = false;
        shouldShoot = false;
        bloody = false;
        high = false;
        rising = false;
        falling = false;



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
            case RISING:
                updateRising(delta);
                break;
            case HIGH:
                updateHigh(delta);
                break;
            case FALLING:
                updateFalling(delta);
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
            case LEADERBOARD:
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

            if (score > AssetLoader.getHighScore1()) {
                AssetLoader.setHighScore(score, 1);
                } else if (score > AssetLoader.getHighScore2()) {
                    AssetLoader.setHighScore(score, 2);
                } else if (score > AssetLoader.getHighScore3()) {
                    AssetLoader.setHighScore(score, 3);
                }
            //??????????? below
                previousState = currentState;
        }

    }

    public void updateRising(float delta) {
/*Issues arise with allowing rabbit to update whil game is paused
Solution will be to set delta to 0 for scroller update while rabbit is rising. This will effectively prevent the scroller
from updating
 */
        rabbit.update(delta);
        if (rabbit.getY() > 200) {
//            scroller.risePause();
            //Yvelocity must be changed from the velocoity it was in the the rising started
            rabbit.setYVelocity(-180);
            delta = 0;
            scroller.update(delta);
        } else {

            getHigh();
//            scroller.resume();
            rabbit.setYAcceleration(0);
            rabbit.setYVelocity(0);
            currentState = GameState.HIGH;

        }
    }

    public void updateHigh(float delta) {
        rabbit.update(delta);
        scroller.update(delta);
        //for some fucking reason if you don't click the screen (even though gamestate is not even on runnign and clicking shouldn't do anything...
        //rabbit is in some downsmode always showing in air animation state not fucking sure why at all.
        //Has to do with the grounY for rabbit changing when it's high... Weird... I'll get back to this
        highCounter -= delta;
        rabbit.onClick();
        rabbit.onRelease();;
        //scoring while high
        scoreCounter += delta;
        if (scoring) {
            if (scoreCounter >= 1 / 10f) {
                scoreCounter -= 1 / 10f;
                score++;
            }
        }

        if (highCounter > 0){
            rabbit.setY(200 + 30*MathUtils.sin(1));
        } else {
            currentState = GameState.FALLING;
        }

    }

    public void updateFalling(float delta) {
        rabbit.update(delta);
        scroller.update(delta);
        if (!rabbit.inAir()) {
            currentState = GameState.RUNNING;
        }

    }


    public void highStuff(float delta) {
        highCounter -= delta;

        if (highCounter > 0){
            high = true;
        } else {
            high = false;
        }

        //if rabbit is high  make it float in the sky...

        if (isHigh()) {
            rabbit.setY(200 + 30*MathUtils.sin(1));
        }
    }

    public void fall() {
        falling = true;
    }

    public void rise() {
        currentState = GameState.RISING;

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
        if (dyingCounter <= 1.5) {
            shouldShoot = true;
            AssetLoader.gunShot.play();
        }
        scroller.updateDyingHill(delta);
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
        previousState = currentState;
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
        dyingCounter = 2;
        resumingCounter = 3;
        collidedPolice = false;
        bloody = false;

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

    public void highScore() {
        previousState = currentState;
        currentState = GameState.HIGHSCORE;
    }

    public boolean isRising() {
        return currentState == GameState.RISING;
    }

    public boolean isFalling() {
        return currentState == GameState.FALLING;
    }

    public void leaderBoard() {
        previousState = currentState;
        currentState = GameState.LEADERBOARD;
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

    public void setBloody() {
        bloody = true;
    }

    public void getHigh() {
        highCounter = 10;
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

    public boolean isLeaderBoard() {
        return currentState == GameState.LEADERBOARD;
    }

    public boolean isDyingPolice() {
        return currentState == GameState.DYINGPOLICE;
    }

    public boolean isDyingHill() {
        return currentState == GameState.DYINGHILL;
    }

    public boolean isBloody() {
        return bloody;
    }

    public boolean isHigh() {
        return high;
    }

    public GameState getCurrentState() {
        return currentState;
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

    public boolean shouldShoot() {
        return shouldShoot;
    }

    public void setCollidedPolice(boolean collidedPolice) {
        this.collidedPolice = collidedPolice;
    }

}
