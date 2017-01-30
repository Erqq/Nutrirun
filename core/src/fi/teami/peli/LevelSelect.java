package fi.teami.peli;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * LevelSelect contains all the stuff in LevelSelect screen.
 *
 * The class LevelSelect contains all the stuff used in gameover screen
 * including the texts, retryButton etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class LevelSelect implements Screen {
    private SpriteBatch batch;
    private teami game;
    private Rectangle back;
    private Rectangle level1;
    private Rectangle level2;
    private Rectangle level3;
    private Rectangle level4;
    private Rectangle level5;
    public static boolean leveli2=false;
    public static boolean leveli3=false;
    public static boolean leveli4=false;
    public static boolean leveli5=false;
    public static int leveli = 1;
    private Vector3 touchpoint;
    private OrthographicCamera fontCamera;
    public float WORLD_HEIGHT_PIXELS =750f;
    public float WORLD_WIDTH_PIXELS = 1500f;
    private OrthographicCamera gameCamera;
    public float WORLD_HEIGHT_METERS =5f;
    public float WORLD_WIDTH_METERS = 10f;
    private Texture Background;
    private Texture Sound_On;
    private Texture Sound_Off;
    private Rectangle Soundrect;

    /**
     * Makes the needed rectangles and assets for the screen
     * @param g is the game
     */
    public LevelSelect (teami g){
        getlevel();
        putlevel();
        back = new Rectangle(50, 20f,400f,55f);
        level1 = new Rectangle(WORLD_WIDTH_PIXELS / 2f - 200f,405,310,75);
        level2 = new Rectangle(WORLD_WIDTH_PIXELS/2+300f,405,310,75);
        level3 = new Rectangle(WORLD_WIDTH_PIXELS/2-200f,255,320,75);
        level4 = new Rectangle(WORLD_WIDTH_PIXELS/2+300f,255,320,75);
        level5 = new Rectangle(WORLD_WIDTH_PIXELS/2+50f,105,320,75);
        Sound_Off= new Texture("Mute.png");
        Sound_On= new Texture("Volume.png");
        Soundrect= new Rectangle(1400,650,80f,80f);
        touchpoint = new Vector3();
        game = g;
        batch = game.getBatch();
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
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
     * Renders the Levelselect Screen
     * @param delta is the deltatime
     */
    @Override
    public void render(float delta) {

        if(MainMenuScreen.Sound_ON_OFF) {
            if (!MainMenuScreen.musiikki.isPlaying()) {
                MainMenuScreen.musiikki2.play();
                MainMenuScreen.musiikki2.setLooping(true);
            }
        }else {
            MainMenuScreen.musiikki2.pause();
            MainMenuScreen.musiikki.pause();
        }
        batch.setProjectionMatrix(gameCamera.combined);
        Gdx.gl.glClearColor(1, 1, 1, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(Background, 0, 0, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        batch.setProjectionMatrix(fontCamera.combined);
        if (MainMenuScreen.Sound_ON_OFF){
            batch.draw(Sound_On,Soundrect.x,Soundrect.y,80f,80f);
        }else if (MainMenuScreen.Sound_ON_OFF==false){
            batch.draw(Sound_Off,Soundrect.x,Soundrect.y,80f,80f);
        }
        game.getFont().draw(batch, teami.myBundle.get("levelSelectTitle"), WORLD_WIDTH_PIXELS / 2f - 350f, WORLD_HEIGHT_PIXELS - 40);
        game.getFont().draw(batch, teami.myBundle.get("level1"), level1.getX(), level1.getY() + 75);
        if(leveli2==false){
            game.getFont().setColor(Color.GRAY);
            game.getFont().draw(batch, teami.myBundle.get("level2"), level2.getX(),level2.getY()+75);
            game.getFont().setColor(Color.WHITE);
        }else{
            game.getFont().draw(batch, teami.myBundle.get("level2"), level2.getX(),level2.getY()+75);
        }
        if(leveli3==false){
            game.getFont().setColor(Color.GRAY);
            game.getFont().draw(batch, teami.myBundle.get("level3"), level3.getX(), level3.getY() + 75);
            game.getFont().setColor(Color.WHITE);
        }else{
            game.getFont().draw(batch, teami.myBundle.get("level3"), level3.getX(),level3.getY()+75);
        }
        if(leveli4==false){
            game.getFont().setColor(Color.GRAY);
            game.getFont().draw(batch, teami.myBundle.get("level4"), level4.getX(),level4.getY()+75);
            game.getFont().setColor(Color.WHITE);
        }else{
            game.getFont().draw(batch, teami.myBundle.get("level4"), level4.getX(),level4.getY()+75);
        }
        if(leveli5==false){
            game.getFont().setColor(Color.GRAY);
            game.getFont().draw(batch, teami.myBundle.get("level5"), level5.getX(),level5.getY()+75);
            game.getFont().setColor(Color.WHITE);
        }else{
            game.getFont().draw(batch, teami.myBundle.get("level5"), level5.getX(),level5.getY()+75);
        }

        game.getFont().draw(batch, teami.myBundle.get("back"), back.getX(), back.getY()+75);
        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.BACK)){
            game.setScreen(game.getMainMenu());
        }

        batch.end();
        if(Gdx.input.justTouched()) {
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));

            if (back.contains(touchpoint.x,touchpoint.y)){
                game.setScreen(game.getMainMenu());
            }
            if (level1.contains(touchpoint.x,touchpoint.y)){
                leveli=1;
                game.setScreen(game.getLevelInfo());
                Gdx.app.log("", "Toimii");
            }
            if(leveli2) {
                if (level2.contains(touchpoint.x, touchpoint.y)) {
                    leveli = 2;
                    game.setScreen(game.getLevelInfo());
                    Gdx.app.log("", "Toimiijoo");
                }
            }
            if(leveli3) {
                if (level3.contains(touchpoint.x, touchpoint.y)) {
                    leveli = 3;
                    game.setScreen(game.getLevelInfo());
                    Gdx.app.log("", "Toimiijoo3");
                }
            }
            if(leveli4) {
                if (level4.contains(touchpoint.x, touchpoint.y)) {
                    leveli = 4;
                    game.setScreen(game.getLevelInfo());
                    Gdx.app.log("", "Toimiijoo4");
                }
            }
            if (leveli5){
                if (level5.contains(touchpoint.x, touchpoint.y)){
                    leveli = 5;
                    game.setScreen(game.getLevelInfo());
                    Gdx.app.log("", "Toimiijoo5");
                }
            }

            if (Soundrect.contains(touchpoint.x, touchpoint.y)){
                MainMenuScreen.Sound_ON_OFF=!MainMenuScreen.Sound_ON_OFF;
                MainMenuScreen.putsound();
                Gdx.app.log("", "Toimii"+MainMenuScreen.Sound_ON_OFF);
            }
        }
    }

    /**
     * Puts some booleans to preferences.
     *
     */
    public static void putlevel(){
        MainMenuScreen.prefs.putBoolean("level2",leveli2);
        MainMenuScreen.prefs.putBoolean("level3",leveli3);
        MainMenuScreen.prefs.putBoolean("level4",leveli4);
        MainMenuScreen.prefs.putBoolean("level5",leveli5);
        MainMenuScreen.prefs.flush();
    }


    /**
     * Gets some booleans from the preferences.
     *
     */
    public static void getlevel(){
        if(MainMenuScreen.prefs.getBoolean("level2")!=leveli2){
            leveli2=MainMenuScreen.prefs.getBoolean("level2");
        }
        if(MainMenuScreen.prefs.getBoolean("level3")!=leveli3){
            leveli3=MainMenuScreen.prefs.getBoolean("level3");
        }
        if(MainMenuScreen.prefs.getBoolean("level4")!=leveli4){
            leveli4=MainMenuScreen.prefs.getBoolean("level4");
        }
        if(MainMenuScreen.prefs.getBoolean("level5")!=leveli5){
            leveli5=MainMenuScreen.prefs.getBoolean("level5");
        }
    }


    /**
     * Resizes the LevelSelect screen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }


    /**
     * Pauses the levelselect screen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the LevelSelect if needed
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
