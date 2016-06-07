package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.MapObject;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;


public class BlueDiamond extends InteractiveTileObject {

    public BlueDiamond(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FutureRun.BLUE_DIAMOND_BIT);
    }

    @Override
    public void use() {
        if(screen.game.withSound)
            screen.game.getAssets().get("Audio/Sounds/Star.wav", Sound.class).play();
        setCategoryFilter(FutureRun.DESTROY_BIT);
        getBlueCell().setTile(null);
        HUD.addScore(100);
    }
}
