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

    private List<Button> menuButtons, titleButtons, readyButtons, pausedButtons;

    private float scaleFactorX, scaleFactorY;

    private Button titlePlayButton, titleSettingsButton, titleHighscoresButton;
    private Button pauseButton, pausedSettingsButton, pausedHighscoresButton, pausedRestartButton;
    private Button readySettingsButton, readyHighscoresButton;
    private Button menuDoneButton, menuHighscoresButton;

    private MusicButton menuAudioButton;

    public InputHandler(GameWorld world, float scaleFactorX, float scaleFactorY) {
        this.world = world;
        rabbit = world.getRabbit();

        int midPointY = world.getGameHeight() / 2;

        this.scaleFactorX = scaleFactorX;
        this.scaleFactorY = scaleFactorY;

        pauseButton = new Button(50, 30, 80, 80,
                AssetLoader.pause, AssetLoader.pause);

        titlePlayButton = new Button(570, 300, 150, 150,
                AssetLoader.playButtonUp, AssetLoader.playButtonDown);
        titleSettingsButton = new Button(345, 300, 150, 150,
                AssetLoader.settingsButtonUp, AssetLoader.settingsButtonDown);
        titleHighscoresButton = new Button(795, 300, 150, 150,
                AssetLoader.highscoresButtonUp, AssetLoader.highscoresButtonDown);


        menuDoneButton = new Button(860, 440, 61, 63,
                AssetLoader.done, AssetLoader.done);
        menuAudioButton = new MusicButton(480, 296, 128, 128,
                AssetLoader.audioOn, AssetLoader.audioOn);
        menuHighscoresButton = new Button(672, 296, 128, 128,
                AssetLoader.highscoresButtonUp, AssetLoader.highscoresButtonDown);

        readyHighscoresButton = new Button(672, 296, 128, 128,
                AssetLoader.highscoresButtonUp, AssetLoader.highscoresButtonDown);
        readySettingsButton = new Button(350, 400, 128, 128,
                AssetLoader.settingsButtonUp, AssetLoader.settingsButtonDown);

        pausedHighscoresButton = new Button(768, 320, 128, 128,
                AssetLoader.highscoresButtonUp, AssetLoader.highscoresButtonDown);
        pausedSettingsButton = new Button(384, 320, 128, 128,
                AssetLoader.settingsButtonUp, AssetLoader.settingsButtonDown);
        pausedRestartButton = new Button(576, 320, 128, 128,
                AssetLoader.restartButtonUp, AssetLoader.restartButtonDown);


        menuButtons = new ArrayList<Button>();
        menuButtons.add(menuAudioButton);
        menuButtons.add(menuDoneButton);
        menuButtons.add(menuHighscoresButton);

        titleButtons = new ArrayList<Button>();
        titleButtons.add(titlePlayButton);
        titleButtons.add(titleSettingsButton);
        titleButtons.add(titleHighscoresButton);

        readyButtons = new ArrayList<Button>();
        readyButtons.add(readySettingsButton);

        pausedButtons = new ArrayList<Button>();
        pausedButtons.add(pausedSettingsButton);
        pausedButtons.add(pausedHighscoresButton);
        pausedButtons.add(pausedRestartButton);
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
            if (!pauseButton.isTouchDown(screenX, screenY)) {
            rabbit.onClick();}
        } else if (world.isPaused()) {
            boolean pausedButtonClicked = false;
            for (Button buttons : pausedButtons) {
                if (buttons.isTouchDown(screenX, screenY)){
                    pausedButtonClicked = true;
                }
            }
                if (!pausedButtonClicked) {
                    world.startResuming();

            }

        } else if (world.isMenu()) {
            for (Button buttons : menuButtons) {
                buttons.isTouchDown(screenX, screenY);
            }
        } else if (world.isReady()) {
            for (Button buttons : readyButtons) {
                buttons.isTouchDown(screenX, screenY);
            }
        }
        else if (world.isTitle()) {
            for (Button buttons : titleButtons) {
                buttons.isTouchDown(screenX, screenY);
            }
        }



        //System.out.println();
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenX = scaleX(screenX);
        screenY = scaleY(screenY);

        if (world.isRunning()) {
            if (pauseButton.isTouchUp(screenX, screenY)) {
                AssetLoader.click.play();
                world.pause();

            }
        } else if (world.isTitle()) {
            for (Button buttons : titleButtons) {
                buttons.isTouchUp(screenX, screenY);
            }
            System.out.println("title");
            if (titlePlayButton.isTouchUp(screenX, screenY)) {
                System.out.println("ready");
                world.start();
            } else if (titleHighscoresButton.isTouchUp(screenX, screenY)) {
            } else if (titleSettingsButton.isTouchUp(screenX, screenY)) {
            }
        } else if (world.isMenu()) {
            for (Button buttons : menuButtons) {
                buttons.isTouchUp(screenX, screenY);
            }
            if (menuDoneButton.isTouchUp(screenX, screenY)){
                AssetLoader.click.play();
                System.out.println(world.getPreviousState());
                if (world.getPreviousState() == GameWorld.GameState.READY) {
                    world.ready();
                }
                else if (world.getPreviousState() == GameWorld.GameState.PAUSED) {
                    world.pause();
                }
            } else if (menuAudioButton.isTouchUp(screenX, screenY)) {
                if (world.isSoundOn()) {
                    world.stopSound();
                    menuAudioButton.changeTexture(AssetLoader.audioOff);
                } else {
                    world.startSound();
                    AssetLoader.click.play();
                    menuAudioButton.changeTexture(AssetLoader.audioOn);
                }

            } else if (menuHighscoresButton.isTouchUp(screenX, screenY)) {

            }
        } else if (this.world.isReady()) {
            for (Button buttons : readyButtons) {
                buttons.isTouchUp(screenX, screenY);
            }
            if(readySettingsButton.isTouchUp(screenX, screenY)) {
                world.menu();
            }
            else if (menuHighscoresButton.isTouchUp(screenX, screenY)) {

            }
            else {
                AssetLoader.click.play();
                this.world.start();
            }

        } else  if (world.isPaused()) {
            for (Button buttons : pausedButtons) {
                buttons.isTouchUp(screenX, screenY);
            }
            if (pausedSettingsButton.isTouchUp(screenX, screenY)) {
                AssetLoader.click.play();
                world.menu();
            }

        } else if (world.isGameOver()) {
            //Reset all variables, go to GameState.Ready
            this.world.restart();
        }

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

    public List<Button> getTitleButtons() {
        return titleButtons;
    }

    public List<Button> getReadyButtons() {
        return readyButtons;
    }
    public List<Button> getPausedButtons() {
        return pausedButtons;
    }

    public Button getReadySettingsButton() {
        return readySettingsButton;
    }

    public Button getMenuHighscoresButton() {
        return menuHighscoresButton;
    }

    public Button getPauseButton() {
        return pauseButton;
    }

    public Button getTitlePlayButton() {
        return titlePlayButton;
    }

    public Button getAudioOnButton() {
        return menuAudioButton;
    }

    public Button getMenuDoneButton() {
        return menuDoneButton;
    }
}
