package eu.epiciv.menu.utils;

import com.badlogic.gdx.Gdx;

import java.awt.Button;

public class Constants {
    public final static int VIEWPORT_WIDTH = Gdx.graphics.getWidth();
    public final static int VIEWPORT_HEIGHT = Gdx.graphics.getHeight();

    public final static String GAME_TITLE = "EpiCiV";

    public final static int buttonSizeX = Gdx.graphics.getWidth() / 4;
    public final static int buttonSizeY = Gdx.graphics.getHeight() / 8;
    public final static int centerX = VIEWPORT_WIDTH / 2 - buttonSizeX / 2;
}