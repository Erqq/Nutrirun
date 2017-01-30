package fi.teami.peli;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;

import java.util.Arrays;


/**
 * Player contains alot of stuff that relate to playerCharacter.
 *
 * The class Player contains alot of stuff that relate to playerCharacter
 * including the animation, body, movement etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public  class Player {
    public static Body PlayerBody;
    public static boolean pause2;
    public static boolean air2;
    public static float speed=3f;
    public static Texture playerTexture;
    public static Texture playerTexture2;
    static Animation walkAnimation;
    static Animation walkAnimation2;
    static TextureRegion currentFrameTexture;
    static TextureRegion currentFrameTexture2;
    static float stateTime;

    /**
     * Creates alot of stuff that relate to playercharacter.
     *
     * @param x indicates the game
     * @return Returns the playerbody
     */
    public static Body createPlayer(World x){
        playerTexture = new Texture(Gdx.files.internal("freimit2.png"));
        playerTexture2 = new Texture(Gdx.files.internal("Laski2.png"));
        createAnimation();
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.DynamicBody;
        myBodyDef.position.set(1, 1);
        PlayerBody = x.createBody(myBodyDef);

        FixtureDef playerFixtureDef = new FixtureDef();
        playerFixtureDef.density = 0.5f;
        playerFixtureDef.restitution = 0.01f;
        playerFixtureDef.friction = 0.1f;
        CircleShape circleShape = new CircleShape();
        circleShape.setRadius(0.25f);
        playerFixtureDef.shape = circleShape;
        PlayerBody.createFixture(playerFixtureDef);

        return PlayerBody;
    }


    /**
     * Creates the animation for the playercharacter.
     *
     */
    public static void createAnimation() {
        final int FRAME_COLS = 3;
        final int FRAME_ROWS = 1;

        /** CREATE THE WALK ANIM **/

        // Calculate the tile width from the sheet
        int tileWidth = playerTexture.getWidth() / FRAME_COLS;
        int tileWidth2 = playerTexture2.getWidth() / FRAME_COLS;

        // Calculate the tile height from the sheet
        int tileHeight = playerTexture.getHeight() / FRAME_ROWS;
        int tileHeight2 = playerTexture2.getHeight() / FRAME_ROWS;

        // Create 2D array from the texture (REGIONS of a TEXTURE).
        TextureRegion[][] tmp1 = TextureRegion.split(playerTexture, tileWidth, tileHeight);
        TextureRegion[][] tmp2 = TextureRegion.split(playerTexture2, tileWidth2, tileHeight2);

        // Transform the 2D array to 1D
        TextureRegion[] allFrames = fi.teami.peli.Utilities.toTextureArray(tmp1, FRAME_COLS, FRAME_ROWS);
        TextureRegion[] allFrames2 = fi.teami.peli.Utilities.toTextureArray(tmp2, FRAME_COLS, FRAME_ROWS);

        walkAnimation = new Animation(4 / 60f, allFrames);
        walkAnimation2 = new Animation(4 / 60f, allFrames2);

        currentFrameTexture = walkAnimation.getKeyFrame(stateTime, true);
        currentFrameTexture2 = walkAnimation2.getKeyFrame(stateTime, true);

    }

    /**
     * Checks if player drops off from the level
     *
     * @param x is the game
     */
    public static void checkIfPlayerIsDead(teami x) {
        if(PlayerBody.getPosition().y<-1||PlayerBody.getPosition().x>255){

            try {

                Thread.sleep(1000);
                if(PlayerBody.getPosition().x>255&&Level1.hiilariCount>0&&Level1.salaattiCount>0&&Level1.proteiiniCount>0&&Level1.juomaCount>0){
                    Level1.pauseMusic();
                    if(MainMenuScreen.Sound_ON_OFF) {
                        Level1.tasoLapiMusa.play();
                    }
                    x.setScreen(x.getVictoryScreen());
                }
                else if (PlayerBody.getPosition().y<-1){
                    Level1.pauseMusic();
                    Level1.kuoli = true;
                    x.setScreen(x.getGameOverScreen());
                    if(MainMenuScreen.Sound_ON_OFF) {
                        Level1.kuolema.play();
                    }
                }
                else{
                    Level1.pauseMusic();
                    x.setScreen(x.getGameOverScreen());
                    if(MainMenuScreen.Sound_ON_OFF) {
                        Level1.kuolema.play();
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
            }
            speed=3;
            Level1.badfoodcount=0;
        }
    }

    /**
     * Makes the player to constantly move
     */
    public static void Playermovement(){
        Vector2 vel = PlayerBody.getLinearVelocity();
        float desiredvel = speed;
        float velChange = desiredvel - vel.x;
        float force = PlayerBody.getMass() * velChange / (1 / 60.0f);
        PlayerBody.applyForce(new Vector2(force, 0), PlayerBody.getWorldCenter(), true);
    }

    /**
     *  Checks if user touches the screen
     * @param air checks if player is in air
     * @param pause checks if game is paused
     * @param x is the game
     */
    public static void checkUserInput(  boolean air,boolean pause,teami x) {


        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)||Gdx.input.getAccelerometerY()<-2f) {
            PlayerBody.applyForceToCenter(new Vector2(-1f, 0), true);
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)|| Gdx.input.getAccelerometerY()>2f) {
            PlayerBody.applyForceToCenter(new Vector2(1f, 0), true);
        }

        Level1.fontCamera.unproject(Level1.touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
        if(Gdx.input.isKeyPressed(Input.Keys.UP)&&!Level1.pausenappi.contains(Level1.touchpoint.x,Level1.touchpoint.y)|| Gdx.input.isTouched()&&!Level1.pausenappi.contains(Level1.touchpoint.x,Level1.touchpoint.y)&&Level1.tapped&&PlayerBody.getPosition().x>1.5f) {
            if (air==false &&  PlayerBody.getLinearVelocity().y ==0) {

                PlayerBody.applyLinearImpulse(new Vector2(0, 0.54f), PlayerBody.getWorldCenter(), true);

                air=true;
                if(MainMenuScreen.Sound_ON_OFF) {
                   if(!pause) {
                       Level1.hyppy.play();
                   }
                }
            }
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)||Gdx.input.isKeyJustPressed(Input.Keys.BACK)) {
            pause = !pause;
        }
        if(Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            Level1.pauseMusic();
            x.setScreen(x.getGameOverScreen());
            speed=3;
            Level1.badfoodcount=0;
        }

        pause2=pause;
        air2=air;
    }






}
