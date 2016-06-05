package com.tiagoalmeida.elementalrun.Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.tiagoalmeida.elementalrun.ElementalRun;
import com.tiagoalmeida.elementalrun.Screens.PlayScreen;
import com.tiagoalmeida.elementalrun.Sprites.Items.BlueDiamond;
import com.tiagoalmeida.elementalrun.Sprites.Items.OrangeDiamond;

public class B2WorldCreator {
    private World world;
    private TiledMap map;

    public B2WorldCreator(PlayScreen screen) {

        BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;
        world = screen.getWorld();
        map = screen.getMap();

        //create Black Ground bodies/fixtures
        for(MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / ElementalRun.PPM,(rect.getY() + rect.getHeight() / 2) / ElementalRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / ElementalRun.PPM, rect.getHeight() / 2 / ElementalRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = ElementalRun.BLACK_GROUND_BIT;
            fdef.friction = 0;
            fdef.restitution = 0;
            body.createFixture(fdef);
        }

        //create Orange Ground bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / ElementalRun.PPM, (rect.getY() + rect.getHeight() / 2) / ElementalRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / ElementalRun.PPM, rect.getHeight() / 2 / ElementalRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = ElementalRun.ORANGE_GROUND_BIT;
            fdef.friction = 0;
            fdef.restitution = 0;
            body.createFixture(fdef);
        }

        //create Blue Ground bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / ElementalRun.PPM, (rect.getY() + rect.getHeight() / 2) / ElementalRun.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / ElementalRun.PPM, rect.getHeight() / 2 / ElementalRun.PPM);
            fdef.shape = shape;
            fdef.filter.categoryBits = ElementalRun.BLUE_GROUND_BIT;
            fdef.friction = 0;
            fdef.restitution = 0;
            body.createFixture(fdef);
        }

        /*//create Orange diamonds bodies/fixtures
        for(MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
            new OrangeDiamond(screen, object);
        }

        //create Blue diamonds bodies/fixtures
        for(MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
            new BlueDiamond(screen, object);
        }*/
        shape.dispose();
    }
}
