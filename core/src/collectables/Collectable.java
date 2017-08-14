package collectables;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Helpers.GameInfo;

/**
 * Created by Darrell Payne on 8/13/17.
 */

public class Collectable extends Sprite{

    private World world;
    private Body body;
    private String name;
    private Fixture fixture;

    public Collectable(World world, String name){
        super(new Texture("Collectables/" + name + ".png"));

        this.world = world;
        this.name = name;
    }

    void createCollectableBody(){

        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set((getX() - getWidth() / 2) / GameInfo.PPM, (getY() - getHeight()) / GameInfo.PPM);

        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2) / GameInfo.PPM, (getHeight() / 2) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void setCollectablePosition(float x, float y){

        setPosition(x,y);
        createCollectableBody();
    }

} // Collectable
