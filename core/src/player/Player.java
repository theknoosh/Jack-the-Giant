package player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
//import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import Helpers.GameInfo;

/**
 * Created by Darrell Payne on 8/5/17.
 */

public class Player extends Sprite{

    private World world;
    private Body body;

    private TextureAtlas playerAtlas;
    private Animation<TextureRegion> animation;
    private float elapsedTime;
    private boolean isWalking;

    public Player(World world, float x, float y){
        super(new Texture("Player/Player 1.png"));
        this.world = world;
        setPosition(x,y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animation/Player Animation.atlas");
    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f) / GameInfo.PPM, (getHeight() / 2f) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4f;
        fixtureDef.friction = 2f;
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.COLLECTABLE;


        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();
    }

    public void movePlayer(float x){

        if (x < 0 && !this.isFlipX()){
            this.flip(true,false);
        } else if (x > 0 && this.isFlipX()){
            this.flip(true,false);
        }

        body.setLinearVelocity(x,body.getLinearVelocity().y);
        isWalking = true;
    }

    public void drawPlayerIdle(SpriteBatch batch){
        if (!isWalking){
            batch.draw(this, getX() + getWidth() /2 - 10, getY() - getHeight() / 2);
        }
    }

    public void drawPlayerAnimation(SpriteBatch batch){

        if (isWalking){
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();

            for (TextureRegion frame : frames){
                if (body.getLinearVelocity().x < 0 && !frame.isFlipX()){
                    frame.flip(true,false);
                } else if (body.getLinearVelocity().x > 0 && frame.isFlipX()){
                    frame.flip(true,false);
                }
            }

            animation = new Animation<TextureRegion>(1f/10f,playerAtlas.getRegions());
            batch.draw(animation.getKeyFrame(elapsedTime,true), getX() + getWidth() /2 - 10, getY() - getHeight() / 2);
        }
    }

    public void updatePlayer(){

        if(body.getLinearVelocity().x > 0){
            // Going right
            setPosition((body.getPosition().x)* GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
        } else if(body.getLinearVelocity().x < 0){
            // going left
            setPosition((body.getPosition().x - 0.3f) * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
        }

    }

    public void setWalking(Boolean isWalking){
        this.isWalking = isWalking;
    }


} // Player
