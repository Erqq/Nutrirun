package fi.teami.peli;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.utils.I18NBundle;

import java.util.Locale;

import javax.security.auth.login.AppConfigurationEntry;
import javax.security.auth.login.Configuration;

/**
 * teami contains all the screens and basic stuff.
 *
 * The class teami contains all screens that are used in
 * the game. It also contains fonts and localization.
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */

public class teami extends Game {
	SpriteBatch batch;

	private MainMenuScreen mainMenuScreen;
    private Level1 level1Screen;
    private VictoryScreen victoryScreen;
    private GameOverScreen gameOverScreen;
    private SplashScreen splashScreen;
    FreeTypeFontGenerator generator;
    private LevelSelect levelSelect;
    private LevelInfo levelInfo;
    BitmapFont font12;

    private float width;
    private float height;
    public static Locale locale;
    public static Locale defaultlocale;
    public static I18NBundle myBundle;


    /**
     * Getter for mainmenu screen.
     *
     *
     *
     * @return Main menu screen
     */
    public MainMenuScreen getMainMenu () {

        return mainMenuScreen;}

    /**
     * Getter for levelinfo screen.
     *
     *
     *
     * @return levelinfo screen
     */
    public LevelInfo getLevelInfo(){
        levelInfo = new LevelInfo(this);
        return levelInfo;
    }

    /**
     * Getter for the game screen.
     *
     *
     *
     * @return Game screen
     */
    public Level1 getLevel1() {
        level1Screen = new Level1(this);
        return level1Screen;
    }

    /**
     * Getter for gameover screen.
     *
     *
     *
     * @return Game over screen
     */
    public GameOverScreen getGameOverScreen(){
        gameOverScreen = new GameOverScreen(this);
        return gameOverScreen;
    }

    /**
     * Getter for victory screen.
     *
     *
     *
     * @return Victory screen
     */
    public VictoryScreen getVictoryScreen(){
        victoryScreen = new VictoryScreen(this);
        return victoryScreen;
    }

    /**
     * Getter for levelselect screen.
     *
     *
     *
     * @return Levelselect screen
     */
    public LevelSelect getLevelSelect(){
        levelSelect = new LevelSelect(this);
        return levelSelect;
    }


    /**
     * Getter for Spritebatch.
     *
     *
     *
     * @return Spritebatch
     */
    public SpriteBatch getBatch() {
        return batch;
    }



    /**
     * Creates all the stuff.
     *
     *
     *
     */
	@Override
	public void create () {
		batch = new SpriteBatch();

            defaultlocale = new Locale("en","GB");

            locale = new Locale("fi", "FI");


        generator = new FreeTypeFontGenerator(Gdx.files.internal("jaapokki_regular.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 100;
        parameter.borderColor = Color.BLACK;
        parameter.color=Color.WHITE;
        parameter.shadowOffsetX=5;
        parameter.shadowOffsetY=5;
        parameter.shadowColor=Color.DARK_GRAY;
        parameter.borderWidth = 3;

        font12 = generator.generateFont(parameter); // font size 12 pixels

        GlyphLayout layout = new GlyphLayout();

        width = layout.width;
        height = layout.height;
        mainMenuScreen = new MainMenuScreen(this);
        splashScreen = new SplashScreen(this);
        setScreen(splashScreen);
	}


    /**
     * Getter for the font.
     *
     *
     *
     * @return Font
     */
    public BitmapFont getFont(){ return font12;}


    /**
     * Renders the screens.
     *
     *
     *
     */
	@Override
	public void render () {
		super.render();
	}
}
