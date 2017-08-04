package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanctuaryofdarkness.jackthegiant.GameMain;

import Helpers.GameInfo;

/**
 * Created by Darrell Payne on 8/3/17.
 */

public class Gameplay implements Screen {

    private GameMain game;
    private Sprite[] bgs; // Array of backgrounds
    private OrthographicCamera mainCamera;
    private Viewport gameViewPort;
    private float lastYPosition;

    public Gameplay(GameMain game){
        this.game = game;
        mainCamera = new OrthographicCamera(GameInfo.WIDTH,GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f,GameInfo.HEIGHT / 2, 0);
        gameViewPort = new StretchViewport(GameInfo.WIDTH,GameInfo.HEIGHT);

        createBackgrounds();
    }

    void update(float dt){
        moveCamera();
        checkBackgroundsOutOfBounds();
    }

    private void moveCamera(){
        mainCamera.position.y -= 2;
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
        game.getBatch().end();

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();

    }

    @Override
    public void resize(int width, int height) {

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

    }
}
