package com.tiagoalmeida.elementalrun.Sprites.Items;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;
import com.tiagoalmeida.elementalrun.Sprites.Player;

/**
 * Created by brentaureli on 8/28/15.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;

    public InteractiveTileObject(PlayScreen screen, MapObject object){
        this.object = object;
        this.screen = screen;
        this.world = screen.getWorld();
        this.map = screen.getMap();
        this.bounds = ((RectangleMapObject) object).getRectangle();

        BodyDef bdef = new BodyDef();
        FixtureDef fdef = new FixtureDef();
        PolygonShape shape = new PolygonShape();

        bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / ElementalRun.PPM, (bounds.getY() + bounds.getHeight() / 2) / ElementalRun.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / ElementalRun.PPM, bounds.getHeight() / 2 / ElementalRun.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }

    public abstract void use();

    public TiledMapTileLayer.Cell getCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(1);
        return layer.getCell((int)(body.getPosition().x * ElementalRun.PPM / 16),
                (int)(body.getPosition().y * ElementalRun.PPM / 16));
    }

    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
