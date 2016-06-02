package com.tiagoalmeida.elementalrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.tiagoalmeida.elementalrun.Screens.MainMenu;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

public class ElementalRun extends Game {
	public static final int V_WIDTH = 400;
	public static final int V_HEIGHT = 208;
	public static final float PPM = 100;

	public static final short ORANGE_GROUND_BIT = 1;
	public static final short BLUE_GROUND_BIT = 2;
	public static final short BLACK_GROUND_BIT = 4;
	public static final short BLUE_DIAMOND_BIT = 8;
	public static final short ORANGE_DIAMOND_BIT = 16;
	public static final short PLAYER_BIT = 32;
	public static final short DESTROY_BIT = 64;

	public SpriteBatch batch;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
	Instead you may want to pass around AssetManager to those class that need it.
	 */

	private static AssetManager assets;

	public BitmapFont font;

	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();
		font.setColor(new Color(0,0,0,1));

		assets = new AssetManager();
		assets.load("audio/music/mario_music.ogg", Music.class);
		assets.load("audio/sounds/coin.wav", Sound.class);
		assets.load("audio/sounds/bump.wav", Sound.class);
		assets.load("audio/sounds/breakblock.wav", Sound.class);
		assets.load("audio/sounds/powerup_spawn.wav", Sound.class);
		assets.load("audio/sounds/powerup.wav", Sound.class);
		assets.finishLoading();

		setScreen(new MainMenu(this));
	}

	public static AssetManager getAssets() {
		return assets;
	}


	@Override
	public void dispose() {
		super.dispose();
		assets.dispose();
		batch.dispose();
	}

	@Override
	public void render () {
		super.render();

	}
}
