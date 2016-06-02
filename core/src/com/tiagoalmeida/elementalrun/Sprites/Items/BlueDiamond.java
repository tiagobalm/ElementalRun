package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Scenes.HUD;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * Created by brentaureli on 8/28/15.
 */
public class BlueDiamond extends InteractiveTileObject {
    private static TiledMapTileSet tileSet;

    public BlueDiamond(PlayScreen screen, MapObject object){
        super(screen, object);
        tileSet = map.getTileSets().getTileSet("tiles");
        fixture.setUserData(this);
        setCategoryFilter(ElementalRun.BLUE_DIAMOND_BIT);
    }

    @Override
    public void use() {
        setCategoryFilter(ElementalRun.DESTROY_BIT);
        getCell().setTile(null);
        HUD.addScore(100);
    }
}
