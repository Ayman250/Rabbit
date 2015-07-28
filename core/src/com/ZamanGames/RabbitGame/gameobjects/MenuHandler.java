package com.ZamanGames.RabbitGame.gameobjects;

import com.ZamanGames.RabbitGame.gameworld.GameWorld;
import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;
import com.ZamanGames.RabbitGame.ui.Button;

/**
 * Created by Ayman on 6/28/2015.
 */
public class MenuHandler {
    private Button playButton, settingsButton;

    public MenuHandler(GameWorld world) {
        playButton = new Button(world.getGameWidth() / 2 - AssetLoader.playButtonUp.getRegionWidth() / 2, world.getGameHeight() / 2 + 100, 210, 70,
                AssetLoader.playButtonUp, AssetLoader.playButtonDown);

    }
    public Button getPlayButton() {
        return playButton;
    }
}
