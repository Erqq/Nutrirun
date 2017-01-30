package fi.teami.peli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.logging.Level;

/**
 * LevelInfo contains all the stuff in levelinfo screen.
 *
 * The class LevelInfo contains all the stuff used in LevelInfo
 * including the background, texts, backbutton etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class LevelInfo implements Screen{
    private SpriteBatch batch;
    private OrthographicCamera fontCamera;
    private OrthographicCamera camera;
    private Texture Background;
    private teami game;

    private Rectangle backToLevelSelect;
    private Rectangle play;
    public float WORLD_HEIGHT_PIXELS =1000f;
    public float WORLD_WIDTH_PIXELS = 2000f;
    public float WORLD_HEIGHT_METERS=5;
    public float WORLD_WIDTH_METERS=10;
    private Vector3 touchpoint;

    /**
     * Creater for all the stuff in this class.
     *
     *@param g is the game
     */
    public LevelInfo (teami g) {
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
        camera=new OrthographicCamera();
        camera.setToOrtho(false,WORLD_WIDTH_METERS,WORLD_HEIGHT_METERS);
        game = g;

        backToLevelSelect = new Rectangle(50,20,690f,55f);
        play = new Rectangle(1400,100,500,130);
        Background = new Texture("Selectbg.png");
        touchpoint = new Vector3();
        batch = game.getBatch();
    }








    /**
     * Sets the backbutton to work properly
     *
     *
     */
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(true);
    }


    /**
     * Renders all the stuff in Levelinfoscreen
     *
     *@param delta is the deltatime
     */
    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BACK)){
            game.setScreen(game.getLevelSelect());
        }
        if(MainMenuScreen.Sound_ON_OFF) {
            if (!MainMenuScreen.musiikki.isPlaying()) {
                MainMenuScreen.musiikki2.play();
                MainMenuScreen.musiikki2.setLooping(true);
            }
        }else {
            MainMenuScreen.musiikki2.pause();
            MainMenuScreen.musiikki.pause();
        }
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Background, 0, 0, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        batch.setProjectionMatrix(fontCamera.combined);
        if(LevelSelect.leveli==1) {
            game.getFont().draw(batch, teami.myBundle.get("level") + LevelSelect.leveli +" "+ teami.myBundle.get("level1goal"), WORLD_WIDTH_PIXELS / 2f - 400f, WORLD_HEIGHT_PIXELS / 2f + 490f);
        }else if(LevelSelect.leveli==2) {
            game.getFont().draw(batch, teami.myBundle.get("level") + LevelSelect.leveli +" "+ teami.myBundle.get("level2goal"), WORLD_WIDTH_PIXELS / 2f - 350f, WORLD_HEIGHT_PIXELS / 2f + 490f);
        }else if(LevelSelect.leveli==3) {
            game.getFont().draw(batch, teami.myBundle.get("level") + LevelSelect.leveli +" "+ teami.myBundle.get("level3goal"), WORLD_WIDTH_PIXELS / 2f - 350f, WORLD_HEIGHT_PIXELS / 2f + 490f);
        }else if(LevelSelect.leveli==4) {
            game.getFont().draw(batch, teami.myBundle.get("level") + LevelSelect.leveli +" "+ teami.myBundle.get("level4goal"), WORLD_WIDTH_PIXELS / 2f - 350f, WORLD_HEIGHT_PIXELS / 2f + 490f);
        }else if(LevelSelect.leveli==5) {
            game.getFont().draw(batch, teami.myBundle.get("level") + LevelSelect.leveli + " "+teami.myBundle.get("level5goal"), WORLD_WIDTH_PIXELS / 2f - 350f, WORLD_HEIGHT_PIXELS / 2f + 490f);
        }
        game.getFont().draw(batch, teami.myBundle.get("info1"), WORLD_WIDTH_PIXELS / 2f - 650f, WORLD_HEIGHT_PIXELS / 2f + 360f);
        game.getFont().draw(batch, teami.myBundle.get("info2"), WORLD_WIDTH_PIXELS / 2f - 650f, WORLD_HEIGHT_PIXELS / 2f + 250f);
        game.getFont().draw(batch, teami.myBundle.get("info3"), WORLD_WIDTH_PIXELS / 2f - 650f, WORLD_HEIGHT_PIXELS / 2f + 140f);


        game.getFont().draw(batch, teami.myBundle.get("info4"), WORLD_WIDTH_PIXELS / 2f - 650f, WORLD_HEIGHT_PIXELS / 2f + 30f);
        game.getFont().draw(batch, teami.myBundle.get("info5"), WORLD_WIDTH_PIXELS / 2f -650f, WORLD_HEIGHT_PIXELS / 2f -80f);

        game.getFont().getData().setScale(1.6f);
        game.getFont().draw(batch, teami.myBundle.get("playButton"), play.getX(),play.getY()+90);
        game.getFont().getData().setScale(1);
        game.getFont().draw(batch, teami.myBundle.get("levelSelect"), backToLevelSelect.getX(), backToLevelSelect.getY()+75);


        batch.end();
        if(Gdx.input.justTouched()) {
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (play.contains(touchpoint.x,touchpoint.y)){
                game.setScreen(game.getLevel1());
            }
            if (backToLevelSelect.contains(touchpoint.x,touchpoint.y)) {
                game.setScreen(game.getLevelSelect());

            }


        }
    }

    /**
     * Resizes the Levelinfoscreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the levelinfoscreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the levelinfoscreen if needed
     */
    @Override
    public void resume() {

    }

    /**
     * Called when this screen is no longer the current screen for a Game.
     */
    @Override
    public void hide() {

    }

    /**
     *Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        Background.dispose();

    }
}
