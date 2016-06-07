package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * End the world block class. Extends Interactive Tile Object class.
 * @see InteractiveTileObject
 */
public class EndOfWorldBlock extends InteractiveTileObject {

    /**
     * End of the world block constructor.
     * @param screen Current play screen.
     * @param object Map object holding the block information.
     */
    public EndOfWorldBlock(PlayScreen screen, MapObject object){
        super(screen, object);
        fixture.setUserData(this);
        setCategoryFilter(FutureRun.END_OF_WORLD);
    }

    @Override
    public void use() {}
}
