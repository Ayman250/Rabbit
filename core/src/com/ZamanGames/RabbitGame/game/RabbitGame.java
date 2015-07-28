package com.ZamanGames.RabbitGame.game;

import com.ZamanGames.RabbitGame.rhelpers.AssetLoader;
import com.ZamanGames.RabbitGame.screens.GameScreen;
import com.badlogic.gdx.Game;

/**
 * Created by Ayman on 6/6/2015.
 */
public class RabbitGame extends Game {
    @Override
    public void create() {
        AssetLoader.load();
        AssetLoader.createFonts();
        setScreen(new GameScreen());

    }


    @Override
    public void dispose() {
        super.dispose();
       //AssetLoader.dispose();
    }
}
