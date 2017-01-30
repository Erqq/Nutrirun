package fi.teami.peli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

import java.util.logging.Level;

/**
 * GameOverScreen contains all the stuff in GameOver screen.
 *
 * The class GameOverScreen contains all the stuff used in gameover screen
 * including the texts, retryButton etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class GameOverScreen implements Screen {
    private teami game;
    private SpriteBatch batch;
    private Rectangle restartRect;
    private Rectangle mainMenuRect;
    private Vector3 touchpoint;
    private OrthographicCamera fontCamera;
    private OrthographicCamera font2Camera;
    public float WORLD_HEIGHT_PIXELS =500f;
    public float WORLD_WIDTH_PIXELS = 1000f;
    private OrthographicCamera gameCamera;
    public float WORLD_HEIGHT_METERS =5f;
    public float WORLD_WIDTH_METERS = 10f;
    private boolean istouched;
    private int omenaCount;
    private Texture Background;


    /**
     * Creater for all the stuff in this class.
     *
     *@param g is the game
     */
    public GameOverScreen (teami g){
        Level1.Playersize = 7;


        istouched=false;
        omenaCount = Level1.getOmenaCount();
        touchpoint = new Vector3();
        restartRect = new Rectangle(350f, 140f,390f,70f);
        mainMenuRect = new Rectangle(350f, 30f,440f,70f);
        game = g;
        batch = game.getBatch();
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
        font2Camera = new OrthographicCamera();
        font2Camera.setToOrtho(false, WORLD_WIDTH_PIXELS*2, WORLD_HEIGHT_PIXELS*2);
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        Background = new Texture("Selectbg.png");
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
     * Renders all the stuff in gameoverscreen
     *
     *@param delta is the deltatime
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameCamera.combined);
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BACK)){
            game.setScreen(game.getLevelSelect());
        }

        batch.begin();
        batch.draw(Background, 0, 0, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        batch.setProjectionMatrix(fontCamera.combined);
        game.getFont().getData().setScale(0.8f);
        game.getFont().draw(batch, teami.myBundle.get("gameOver"), 150f, 495f);




        game.getFont().draw(batch, teami.myBundle.get("restart"), restartRect.getX(), restartRect.getY() + 75f);
        game.getFont().draw(batch, teami.myBundle.get("mainMenu"), mainMenuRect.getX(),mainMenuRect.getY() +75f);
        if(Level1.kuoli) {

            game.getFont().setColor(Color.RED);
            game.getFont().draw(batch, teami.myBundle.get("gameOver1"), 200f, 380f);
            game.getFont().draw(batch,teami.myBundle.get("gameOver2"), 200f, 300f);
            game.getFont().setColor(Color.WHITE);
        }


        else{batch.setProjectionMatrix(font2Camera.combined);
            game.getFont().draw(batch, teami.myBundle.get("gameOver3"), 50f*2, 420f*2);
            game.getFont().draw(batch, teami.myBundle.get("gameOver4"), 50f*2, 350f*2);
            game.getFont().draw(batch, teami.myBundle.get("gameOver5"), 50f*2, 290f * 2);
        }
        game.getFont().getData().setScale(1);
        batch.end();
        if(Gdx.input.justTouched()) {
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
           if (istouched==false) {
               if (restartRect.contains(touchpoint.x, touchpoint.y)) {
                   Gdx.app.log("", "Toimii");
                    istouched=true;
                   game.setScreen(game.getLevel1());
               }
               if (mainMenuRect.contains(touchpoint.x, touchpoint.y)) {
                   istouched=true;
                   game.setScreen(game.getMainMenu());
               }
           }
        }

    }

    /**
     * Resizes the Gameoverscreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the gameoverscreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the gameoverscreen if needed
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
        game.getFont().dispose();
    }
}
