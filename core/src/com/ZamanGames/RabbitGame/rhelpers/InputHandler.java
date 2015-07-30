package com.ZamanGames.RabbitGame.rhelpers;

import com.ZamanGames.RabbitGame.gameobjects.Rabbit;
import com.ZamanGames.RabbitGame.gameworld.GameWorld;
import com.ZamanGames.RabbitGame.ui.Button;
import com.ZamanGames.RabbitGame.ui.MusicButton;
import com.badlogic.gdx.InputProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman on 6/6/2015.
 */
//Servs As menu Handler as well
public class InputHandler implements InputProcessor {

    private Rabbit rabbit;
    private GameWorld world;

    private List<Button> menuButtons;

    private float scaleFactorX, scaleFactorY;

    private Button playButton, settingsButton, doneButton, hiScoreButton, pauseButton;

    private MusicButton audioButton;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        rabbit = world.getRabbit();

        int midPointY = world.getGameHeight() / 2;

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        settingsButton = new Button(world.getGameWidth() / 2 - AssetLoader.settingsGear.getRegionWidth() / 2 - 40, world.getGameHeight() - 300, 80, 80,
                AssetLoader.settingsGear, AssetLoader.settingsGear);
        hiScoreButton = new Button(world.getGameWidth() / 2 - AssetLoader.hiScores.getRegionWidth() / 2 + 40, world.getGameHeight() - 328, 128, 128,
                AssetLoader.hiScores, AssetLoader.hiScores);
        playButton = new Button(world.getGameWidth() / 2 - AssetLoader.playButtonUp.getRegionWidth() / 2, world.getGameHeight()/2 - 150, 200, 200,
                AssetLoader.playButton, AssetLoader.playButton);
        doneButton = new Button(world.getGameWidth() / 2 - AssetLoader.playButtonUp.getRegionWidth() / 2, world.getGameHeight()/2 - 150, 200, 200,
                AssetLoader.done, AssetLoader.done);
        pauseButton = new Button(50, 30, 80, 80, AssetLoader.pause, AssetLoader.pause);

        audioButton = new MusicButton(world.getGameWidth() / 2 - AssetLoader.audioOn.getRegionWidth() / 2 - 100, world.getGameHeight() - 328, 128, 128,
                AssetLoader.audioOn, AssetLoader.audioOn);

        menuButtons = new ArrayList<Button>();
        menuButtons.add(audioButton);
        menuButtons.add(doneButton);
        menuButtons.add(hiScoreButton);



    }


    @Override
    public boolean keyDown(int keycode) {

        if (this.world.isReady()) {
            this.world.start();
        }
        rabbit.onClick();

        if (this.world.isGameOver() || this.world.isHighScore()) {
            //Reset all variables, go to GameState.Ready
            this.world.restart();
        }
        //System.out.println();
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        rabbit.onRelease();
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        //System.out.println(screenX + " " + screenY);
        if (world.isRunning()) {
            if (pauseButton.isTouchDown(screenX, screenY)) {
                AssetLoader.click.play();
                world.pause();

            } else{
            rabbit.onClick();}
        } else if (world.isMenu()) {
            if (doneButton.isTouchDown(screenX, screenY)){
                AssetLoader.click.play();
                System.out.println(world.getPreviousState());
                if (world.getPreviousState() == GameWorld.GameState.READY) {
                    world.ready();
                }
                else if (world.getPreviousState() == GameWorld.GameState.PAUSED) {
                    world.pause();
                }
            } else if (audioButton.isTouchDown(screenX, screenY)) {
                if (world.isSoundOn()) {
                    world.stopSound();
                    audioButton.changeTexture(AssetLoader.audioOff);
                } else {
                    world.startSound();
                    AssetLoader.click.play();
                    audioButton.changeTexture(AssetLoader.audioOn);
                }

            }
        } else if (this.world.isReady()) {

            if(settingsButton.isTouchDown(screenX, screenY)) {
                world.menu();
            }
            else if (hiScoreButton.isTouchDown(screenX, screenY)) {

            }
            else {
                AssetLoader.click.play();
                this.world.start();
            }

        } else  if (world.isPaused()) {
            if (settingsButton.isTouchDown(screenX, screenY)) {
                AssetLoader.click.play();
                world.menu();
            } else {
                world.startResuming();
            }

            System.out.println("resumed");
        } else if (world.isGameOver()) {
            //Reset all variables, go to GameState.Ready
            this.world.restart();
        }





        //System.out.println();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        rabbit.onRelease();
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    private int scaleX(int screenX) {
        return (int) (screenX / scaleFactorX);
    }

    //Methods required because touch inputs are based on device screen size not game coordinates
    private int scaleY(int screenY) {
        return (int) (screenY / scaleFactorY);
    }

    public List<Button> getMenuButtons() {
        return menuButtons;
    }

    public Button getSettingsButton() {
        return settingsButton;
    }

    public Button getHiScoreButton() {
        return hiScoreButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getAudioOnButton() {
        return audioButton;
    }

    public Button getDoneButton() {
        return doneButton;
    }
}
