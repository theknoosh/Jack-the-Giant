package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanctuaryofdarkness.jackthegiant.GameMain;

import Helpers.GameInfo;
import Scenes.Gameplay;
import Scenes.Highscore;

/**
 * Created by Darrell Payne on 8/6/17.
 */

public class MainMenuButtons {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private ImageButton playbtn;
    private ImageButton highScoreBtn;
    private ImageButton optionsBtn;
    private ImageButton quitBtn;
    private ImageButton musicBtn;

    public MainMenuButtons(GameMain game){
        this.game = game;
        gameViewport = new FillViewport(GameInfo.WIDTH,GameInfo.HEIGHT,new OrthographicCamera());
        stage = new Stage(gameViewport,game.getBatch());
        Gdx.input.setInputProcessor(stage);
        createAndPositionButtons();

        addAllListeners();

        stage.addActor(playbtn);
        stage.addActor(highScoreBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);
        stage.addActor(musicBtn);
    }

    void createAndPositionButtons(){

        playbtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/MainMenu/Start Game.png"))));
        highScoreBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/MainMenu/Highscore.png"))));
        optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/MainMenu/Options.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/MainMenu/Quit.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/MainMenu/Music On.png"))));

        playbtn.setPosition(GameInfo.WIDTH / 2 - 80, GameInfo.HEIGHT / 2 + 50, Align.center);
        highScoreBtn.setPosition(GameInfo.WIDTH / 2 - 60, GameInfo.HEIGHT / 2 - 20, Align.center);
        optionsBtn.setPosition(GameInfo.WIDTH / 2 - 40, GameInfo.HEIGHT / 2 - 90, Align.center);
        quitBtn.setPosition(GameInfo.WIDTH / 2 - 20, GameInfo.HEIGHT / 2 - 160, Align.center);
        musicBtn.setPosition(GameInfo.WIDTH - 13, 13, Align.bottomRight);

    }

    void addAllListeners(){
        playbtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Gameplay(game));
            }
        });

        highScoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Highscore(game));
            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Play buttons was pressed");
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Play buttons was pressed");
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                System.out.println("Play buttons was pressed");
            }
        });
    }

    public Stage getStage(){
        return this.stage;
    }

} // Main Menu buttons
