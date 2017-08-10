package huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeType;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FillViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.sanctuaryofdarkness.jackthegiant.GameMain;

import Helpers.GameInfo;
import Scenes.Highscore;
import Scenes.MainMenu;

/**
 * Created by Darrell Payne on 8/9/17.
 */

public class HighscoreButtons {

    private GameMain game;
    private Stage stage;
    private Viewport gameViewport;

    private Label scoreLabel, coinLabel;

    private ImageButton backButton;

    public HighscoreButtons(GameMain game){

        this.game = game;

        gameViewport = new FillViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(gameViewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionUIElements();

        stage.addActor(backButton);
        stage.addActor(scoreLabel);
        stage.addActor(coinLabel);

    }

    void  createAndPositionUIElements(){

        backButton = new ImageButton(new SpriteDrawable(new Sprite(new Texture("Buttons/Options/Back.png"))));

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("Fonts/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;
        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);

        scoreLabel = new Label("100", new Label.LabelStyle(scoreFont, Color.WHITE));
        coinLabel = new Label("100", new Label.LabelStyle(coinFont, Color.WHITE));

        backButton.setPosition(17,17, Align.bottomLeft);

        scoreLabel.setPosition(GameInfo.WIDTH / 2f - 30,GameInfo.HEIGHT / 2f - 120);
        coinLabel.setPosition(GameInfo.WIDTH / 2f - 30, GameInfo.HEIGHT / 2f - 215);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

    }

    public Stage getStage(){
        return this.stage;
    }

} // Highscore buttons






