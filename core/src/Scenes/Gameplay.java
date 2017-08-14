package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanctuaryofdarkness.jackthegiant.GameMain;

import Clouds.Cloud;
import Clouds.CloudsController;
import Helpers.GameInfo;
import huds.UIHud;
import player.Player;

/**
 * Created by Darrell Payne on 8/3/17.
 */

public class Gameplay implements Screen, ContactListener {

    private GameMain game;
    private Sprite[] bgs; // Array of backgrounds
    private OrthographicCamera mainCamera;

    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;

    private World world;

    private Viewport gameViewPort;
    private float lastYPosition;

    UIHud hud;

    private CloudsController cloudsController;

    private Player player;

    public Gameplay(GameMain game){
        this.game = game;
        mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f,GameInfo.HEIGHT / 2, 0);
        gameViewPort = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false,GameInfo.WIDTH / GameInfo.PPM, GameInfo.HEIGHT / GameInfo.PPM);
        box2DCamera.position.set(GameInfo.WIDTH / 2, GameInfo.HEIGHT / 2, 0);

        debugRenderer = new Box2DDebugRenderer();

        hud = new UIHud(game);

        world = new World(new Vector2(0,-9.8f), true);
        world.setContactListener(this);

        cloudsController = new CloudsController(world);

        player = cloudsController.positionThePlayer(player);

        createBackgrounds();
    }

    void handleInput(float dt){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.movePlayer(-2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.movePlayer(2);
        }else {
            player.setWalking(false);
        }
    }

    void update(float dt){
        handleInput(dt);
//        moveCamera();
        checkBackgroundsOutOfBounds();
        cloudsController.setCameraY(mainCamera.position.y);
        cloudsController.createAndArrangeClouds();
    }

    private void moveCamera(){
        mainCamera.position.y -= 1.5f;
    }

    private void createBackgrounds(){
        bgs = new Sprite[3]; // 3 entries will be in array
        for (int i = 0; i < bgs.length; i++){
            bgs[i] = new Sprite(new Texture("Backgrounds/Game BG.png"));
            bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
            lastYPosition = Math.abs(bgs[i].getY());
        }
    }

    private void drawBackgrounds(){
        for (int i = 0; i < bgs.length; i++){
            game.getBatch().draw(bgs[i], bgs[i].getX(),bgs[i].getY());
        }
    }

    private void checkBackgroundsOutOfBounds() {
        for (int i = 0; i < bgs.length; i++){
            if ((bgs[i].getY() - bgs[i].getHeight() / 2f - 5) > mainCamera.position.y){
                float newPosition = bgs[i].getHeight() + lastYPosition;
                bgs[i].setPosition(0,-newPosition);
                lastYPosition = Math.abs(newPosition);
            }
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.getBatch().begin();
        drawBackgrounds();

        cloudsController.drawClouds(game.getBatch());
        cloudsController.drawCollectables(game.getBatch());

        player.drawPlayerIdle(game.getBatch());
        player.drawPlayerAnimation(game.getBatch());

        game.getBatch().end();

        debugRenderer.render(world, box2DCamera.combined);

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();

        player.updatePlayer();
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);

    }

    @Override
    public void resize(int width, int height) {
        gameViewPort.update(width,height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        for (int i = 0; i < bgs.length; i++){
            bgs[i].getTexture().dispose();
        }
        player.getTexture().dispose();
        debugRenderer.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture playerBody, collectable;

        if (contact.getFixtureA().getUserData() == "Player"){
            playerBody = contact.getFixtureA();
            collectable = contact.getFixtureB();
        } else {
            playerBody = contact.getFixtureB();
            collectable = contact.getFixtureA();
        }

        if (collectable.getUserData() == "Coin"){
            // Collided with coin
            System.out.println("COIN");
            collectable.setUserData("Remove");
            cloudsController.removeCollectables();
        }
        if (collectable.getUserData() == "Life"){
            // Collided with life
            System.out.println("LIFE");
            collectable.setUserData("Remove");
            cloudsController.removeCollectables();
        }

    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
} // Gameplay
