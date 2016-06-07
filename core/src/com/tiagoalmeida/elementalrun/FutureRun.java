package com.tiagoalmeida.elementalrun;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.tiagoalmeida.elementalrun.Screens.MainMenuScreen;
import com.tiagoalmeida.elementalrun.Screens.SplashScreen;
import com.tiagoalmeida.elementalrun.Tools.SaveHandler;

public class FutureRun extends Game {
	public static final int V_WIDTH = 1920;
	public static final int V_HEIGHT = 1080;
	public static final float PPM = 100;

	public static final short ORANGE_GROUND_BIT = 1;
	public static final short BLUE_GROUND_BIT = 2;
	public static final short BLACK_GROUND_BIT = 4;
	public static final short BLUE_DIAMOND_BIT = 8;
	public static final short ORANGE_DIAMOND_BIT = 16;
	public static final short PLAYER_BIT = 32;
	public static final short DESTROY_BIT = 64;
	public static final short PORTAL_BIT  = 128;
	public static final short END_OF_WORLD = 256;

	/* WARNING Using AssetManager in a static way can cause issues, especially on Android.
		Instead you may want to pass around AssetManager to those class that need it.
 	*/
	private static AssetManager assets;
	public SpriteBatch batch;

	public static OrthographicCamera camera;

	public SplashScreen splashScreen;

	public boolean withSound;

	/**
	 * Creates the main game.
	 */
	@Override
	public void create () {

		//Initalization of Sprite Batch and AssetManager
		batch = new SpriteBatch();
		assets = new AssetManager();

		// set the loaders for the generator and the fonts themselves
		FileHandleResolver resolver = new InternalFileHandleResolver();
		assets.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
		assets.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

		//Camera Initialization
		camera = new OrthographicCamera();
		camera.setToOrtho(false, V_WIDTH, V_HEIGHT);

		//Screen Singletons
		splashScreen = new SplashScreen(this);

		//Load Saved games
		SaveHandler.load();

		//Setting Sound to true
		withSound = true;

		setScreen(splashScreen);
	}

	/**
	 *
	 * @return Assests Manager.
     */
	public static AssetManager getAssets() {
		return assets;
	}

	/**
	 * Override of render method. Just calls super.render().
	 */
	@Override
	public void render () {
		super.render();
	}

	/**
	 * Dispose method. Disposes assests manager, spritebatch, splash screen and main application.
	 */
	@Override
	public void dispose() {
		System.out.println("Disposing Elemental Run");
		assets.dispose();
		batch.dispose();
		splashScreen.dispose();
		super.dispose();
	}

}
