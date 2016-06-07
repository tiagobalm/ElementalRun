package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * Orange diamond class. Extends Interactive Tile Object class.
 * @see InteractiveTileObject
 */
public class OrangeDiamond extends InteractiveTileObject {

    /**
     * Orange diamond constructor.
     * @param screen Current play screen.
     * @param object Map object holding the orange diamond information.
     */
    public OrangeDiamond(PlayScreen screen, MapObject object) {
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FutureRun.ORANGE_DIAMOND_BIT);
    }

    /**
     * Uses the orange diamond, i.e, eliminates the diamond from the world and increments score.
     */
    @Override
    public void use() {
        if(screen.game.withSound)
            screen.game.getAssets().get("Audio/Sounds/Star.wav", Sound.class).play();
        setCategoryFilter(FutureRun.DESTROY_BIT);
        getOrangeCell().setTile(null);
        HUD.addScore(100);
    }
}
