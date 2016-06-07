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
import com.tiagoalmeida.elementalrun.FutureRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;

/**
 * Interactive Tile Object abstract class.
 */
public abstract class InteractiveTileObject {
    protected World world;
    protected TiledMap map;
    protected Rectangle bounds;
    protected Body body;
    protected PlayScreen screen;
    protected MapObject object;

    protected Fixture fixture;

    /**
     * Interactive Tile Object constructor.
     * @param screen Current play screen.
     * @param object Map object holding the object information.
     */
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
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / FutureRun.PPM, (bounds.getY() + bounds.getHeight() / 2) / FutureRun.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / FutureRun.PPM, bounds.getHeight() / 2 / FutureRun.PPM);
        fdef.shape = shape;
        fdef.isSensor = true;
        fixture = body.createFixture(fdef);
    }

    /**
     * Abstract method use.
     */
    public abstract void use();

    /**
     *
     * @return the cell of a orange diamond.
     */
    public TiledMapTileLayer.Cell getOrangeCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(5);
        return layer.getCell((int)(body.getPosition().x * FutureRun.PPM / 64),
                (int)(body.getPosition().y * FutureRun.PPM / 64));
    }

    /**
     *
     * @return the cell of a blue diamond.
     */
    public TiledMapTileLayer.Cell getBlueCell() {
        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(6);
        return layer.getCell((int)(body.getPosition().x * FutureRun.PPM / 64),
                (int)(body.getPosition().y * FutureRun.PPM / 64));
    }

    /**
     * Sets the category bit mask of the object to the passed argument.
     * @param filterBit Short representing the category bit mask.
     */
    public void setCategoryFilter(short filterBit){
        Filter filter = new Filter();
        filter.categoryBits = filterBit;
        fixture.setFilterData(filter);
    }

}
