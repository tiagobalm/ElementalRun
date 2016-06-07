package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;


public class EndOfWorldBlock extends InteractiveTileObject {

    public EndOfWorldBlock(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FutureRun.END_OF_WORLD);
    }

    @Override
    public void use() {}
}
