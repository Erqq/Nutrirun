package fi.teami.peli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.Input;


/**
 * VictoryScreen contains all the stuff in Victory screen.
 *
 * The class VictoryScreen contains all the stuff used in Victoryscreen
 * including the texts, nextlevelbutton etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class VictoryScreen implements Screen {
    private teami game;
    private SpriteBatch batch;
    private Rectangle restartRect;
    private Rectangle mainMenuRect;
    private Vector3 touchpoint;
    private OrthographicCamera fontCamera;
    public float WORLD_HEIGHT_PIXELS =500f;
    public float WORLD_WIDTH_PIXELS = 1000f;
    private OrthographicCamera gameCamera;
    public float WORLD_HEIGHT_METERS =5f;
    public float WORLD_WIDTH_METERS = 10f;
    private Texture Background;


    /**
     * Creater for all the stuff in this class.
     *
     *@param g is the game
     */
    public VictoryScreen(teami g){
        unlocklevel();
        Level1.Playersize = 7;
        Background = new Texture("Selectbg.png");
        touchpoint = new Vector3();
        restartRect = new Rectangle(600f, 10f,330f,80f);
        mainMenuRect = new Rectangle(50f, 10f,230f,120f);
        game = g;
        batch = game.getBatch();
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);

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
     * Renders all the stuff in Victoryscreen
     *
     *@param delta is the deltatime
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(gameCamera.combined);
        if (Gdx.input.isKeyPressed(Input.Keys.BACK)){
            game.setScreen(game.getLevelSelect());
        }

        batch.begin();
        batch.draw(Background, 0, 0, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        batch.setProjectionMatrix(fontCamera.combined);
        game.getFont().getData().setScale(0.5f);
        game.getFont().draw(batch, teami.myBundle.get("victory"), 300f, 480f);



        if(LevelSelect.leveli==5){
            game.getFont().draw(batch, teami.myBundle.get("victory3"), 150f, 340f);

        }
        else{
            game.getFont().draw(batch, teami.myBundle.get("victory1"), 150f, 340f);
            game.getFont().draw(batch, teami.myBundle.get("victory2"), 150f, 280f);
            game.getFont().draw(batch, teami.myBundle.get("next"), restartRect.getX(), restartRect.getY() + 75);
        }
        game.getFont().draw(batch, teami.myBundle.get("mainMenu"),mainMenuRect.getX(), mainMenuRect.getY()+75);
        game.getFont().getData().setScale(1f);
        batch.end();
        if(Gdx.input.justTouched()) {
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(restartRect.contains(touchpoint.x,touchpoint.y)&&LevelSelect.leveli!=5)
            {
                Gdx.app.log("","Toimii");
                if(LevelSelect.leveli<5) {
                    ++LevelSelect.leveli;
                }
                game.setScreen(game.getLevel1());
            }
            if (mainMenuRect.contains(touchpoint.x, touchpoint.y)){
                game.setScreen(game.getMainMenu());
            }
        }

    }

    /**
     * Unlocks the next level if you complete the previous one.
     *
     *
     */
    public static void unlocklevel(){
        if(LevelSelect.leveli==1){
            MainMenuScreen.prefs.putBoolean("level2",true);
            MainMenuScreen.prefs.flush();
        }
        if(LevelSelect.leveli==2){
            MainMenuScreen.prefs.putBoolean("level3",true);
            MainMenuScreen.prefs.flush();
        }
        if(LevelSelect.leveli==3){
            MainMenuScreen.prefs.putBoolean("level4",true);
            MainMenuScreen.prefs.flush();
        }
        if(LevelSelect.leveli==4){
            MainMenuScreen.prefs.putBoolean("level5", true);
            MainMenuScreen.prefs.flush();
        }
    }

    /**
     * Resizes the Victoryscreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the victoryscreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the victoryscreen if needed
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
