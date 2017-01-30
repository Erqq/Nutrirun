package fi.teami.peli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * MainMenuScreen contains all the stuff in Mainmenu.
 *
 * The class teami contains all the stuff used in Mainmenu
 * including the background, language select, mutebutton etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class MainMenuScreen implements Screen{

    private teami game;
    private Texture Background;
    private SpriteBatch batch;
    private Rectangle pelaaRect;
    private Vector3 touchpoint;

    private OrthographicCamera fontCamera;
    public float WORLD_HEIGHT_PIXELS =600f;
    public float WORLD_WIDTH_PIXELS = 1200f;
    private OrthographicCamera gameCamera;
    public float WORLD_HEIGHT_METERS =6f;
    public float WORLD_WIDTH_METERS = 12f;
    private Texture Sound_On;
    private Texture Sound_Off;
    private Rectangle Soundrect;
    private Rectangle LangRect;
    public static Boolean Sound_ON_OFF=true;
    public static Music musiikki;
    public static Music musiikki2;
    public static Preferences prefs;
    public static boolean Language;



    /**
     * Creater for all the stuff in this class.
     *
     *
     *@param g is the game
     */
    public MainMenuScreen (teami g){

        prefs = Gdx.app.getPreferences("My Preferences");
        musiikki = Gdx.audio.newMusic(Gdx.files.internal("MUS_Nutrirun_menu_003_intro.ogg"));
        musiikki2 = Gdx.audio.newMusic(Gdx.files.internal("MUS_Nutrirun_menu_003_loop.ogg"));
        getlanguage();
        putlanguage();
        getsound();
        putsound();
        if(Sound_ON_OFF) {
            musiikki.play();
        }else{
            musiikki.pause();
        }
        Sound_Off= new Texture("Mute.png");
        Sound_On= new Texture("Volume.png");
        Soundrect= new Rectangle(1120,520,64f,64f);
        LangRect= new Rectangle(20,520,64f,64f);
        touchpoint = new Vector3();
        pelaaRect = new Rectangle(600f, 120f,250f,80f);

        game = g;
        batch = game.getBatch();
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
        gameCamera = new OrthographicCamera();
        gameCamera.setToOrtho(false, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);
        Background = new Texture("startscreen.png");

    }

    /**
     * Sets the backbutton to work properly
     *
     *
     */
    @Override
    public void show() {
        Gdx.input.setCatchBackKey(false);
    }



    /**
     * Renders all the stuff in Mainmenuscreen
     *
     *@param delta is the deltatime
     */
    @Override
    public void render(float delta) {

        if(Sound_ON_OFF) {
            if (!musiikki.isPlaying()) {
                musiikki2.play();
                musiikki2.setLooping(true);
            }
        }else {
            musiikki.pause();
            musiikki2.pause();
        }
        batch.setProjectionMatrix(gameCamera.combined);
        CheckIfPressed();

        batch.begin();
        batch.draw(Background, 0, 0, WORLD_WIDTH_METERS, WORLD_HEIGHT_METERS);

        batch.setProjectionMatrix(fontCamera.combined);
        if (Sound_ON_OFF){
            batch.draw(Sound_On,Soundrect.x,Soundrect.y,64f,64f);
        }else if (Sound_ON_OFF==false){
            batch.draw(Sound_Off,Soundrect.x,Soundrect.y,64f,64f);
        }
        game.getFont().getData().setScale(0.5f);
        if (Language){
            game.getFont().draw(batch,"FI", LangRect.getX(), LangRect.getY() + 55);
        }else {
            game.getFont().draw(batch,"EN", LangRect.getX(), LangRect.getY() + 55);
        }
        game.getFont().getData().setScale(1);
        //game.getFont().draw(batch, "MAIN MENU", 150f, 400f);
        


        game.getFont().draw(batch, teami.myBundle.get("playButton"), pelaaRect.getX(), pelaaRect.getY() + 75);

        batch.end();


    }


    /**
     * Checks if the rectangles are touched.
     *
     *
     */
    public void CheckIfPressed(){
        if (Gdx.input.justTouched()) {
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if(pelaaRect.contains(touchpoint.x,touchpoint.y))
            {
                Gdx.app.log("","Toimii");

                game.setScreen(game.getLevelSelect());
            }

            if (Soundrect.contains(touchpoint.x,touchpoint.y)){
                Sound_ON_OFF=!Sound_ON_OFF;
                putsound();
                Gdx.app.log("", "Toimii" + Sound_ON_OFF);


            }
            if(LangRect.contains(touchpoint.x,touchpoint.y)){
                Language=!Language;
                putlanguage();


            }
        }
    }

    /**
     * Puts the sound boolean to references.
     */
    public static void putsound(){
        prefs.putBoolean("soundOn",Sound_ON_OFF);
        prefs.flush();
    }

    /**
     * Gets the sound boolean from the preferences.
     */
    public static void getsound(){
        if(prefs.getBoolean("soundOn")!=Sound_ON_OFF){
            Sound_ON_OFF=prefs.getBoolean("soundOn");
        }
    }

    /**
     * Puts the "enkku" boolean to preferences.
     */
    public static void putlanguage(){
        prefs.putBoolean("enkku",Language);
        prefs.flush();
        if(Language){

            Locale locale = teami.locale;
            Locale.setDefault(locale);
            teami.myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);


        }
        else{
            Locale locale = teami.defaultlocale;
            Locale.setDefault(locale);

            teami.myBundle = I18NBundle.createBundle(Gdx.files.internal("MyBundle"), locale);

        }

    }

    /**
     * Gets the "enkku" boolean from preferences and switches the language boolean to match it.
     */
    public static void getlanguage(){
        if(prefs.getBoolean("enkku")!=Language){
            Language=prefs.getBoolean("enkku");
        }
    }

    /**
     * Resizes the Mainmenuscreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the Mainmenuscreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Vokes up the screen if needed.
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
        musiikki.dispose();
        musiikki2.dispose();
        game.getFont().dispose();
        Sound_On.dispose();
        Sound_Off.dispose();
    }
}
