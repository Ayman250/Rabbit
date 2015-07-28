package com.ZamanGames.RabbitGame.screens;

import com.ZamanGames.RabbitGame.gameworld.GameRender;
import com.ZamanGames.RabbitGame.gameworld.GameWorld;
import com.ZamanGames.RabbitGame.rhelpers.InputHandler;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

/**
 * Created by Ayman on 6/6/2015.
 */

public class GameScreen implements Screen {

    private GameWorld world;
    private GameRender renderer;
    public float gameWidth;
    public float gameHeight;
    public float runTime;

    public GameScreen() {

        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float gameWidth = 1280;
        float gameHeight = 720;

        int midPointY = (int) (gameHeight / 2);
        int groundY = (int) (gameHeight * 9 / 10);

        world = new GameWorld((int) gameWidth, (int) gameHeight, midPointY, groundY);


        Gdx.input.setInputProcessor(new InputHandler(world, screenWidth / gameWidth, screenHeight / gameHeight) );
        renderer = new GameRender(world, (int) gameHeight, (int) gameWidth, groundY);
        runTime = 0;

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        world.update(delta);

        runTime += delta;
        renderer.render(delta, runTime);

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("Pause");
    }

    @Override
    public void pause() {
        world.pause();
    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
