package fi.teami.peli;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Timer;

/**
 * SplashScreen draws the splashscreen.
 *
 *
 * @author Eerik Timonen
 * @version 2016.0509
 */
public class SplashScreen implements Screen{
    private teami game;
    private Texture Background;
    private OrthographicCamera gameCamera;
    public float WORLD_HEIGHT_METERS =6f;
    public float WORLD_WIDTH_METERS = 12f;
    private float delay=3;
    private SpriteBatch batch;

    /**
     * Creates some stuff.
     * @param g is the game
     */
    public SplashScreen(final teami g){
        game = g;
        batch = game.getBatch();
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        Background = new Texture("Splash.png");
        Timer.schedule(new Timer.Task(){
            @Override
            public void run() {
                game.setScreen(game.getMainMenu());
            }
        }, delay);
    }

    /**
     * Does nothing
     *
     *
     */
    @Override
    public void show() {

    }

    /**
     * Renders the Splashscreen
     * @param delta is the deltatime
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameCamera.combined);
        batch.begin();
        batch.draw(Background,0,0,WORLD_WIDTH_METERS,WORLD_HEIGHT_METERS);
        batch.end();
    }

    /**
     * Resizes the Spalshscreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the splashscreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the splashscreen if needed
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
