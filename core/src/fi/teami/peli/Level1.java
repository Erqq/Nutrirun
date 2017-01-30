package fi.teami.peli;

import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import sun.security.util.Length;

/**
 * Level1 contains all the ingame/playable stuff.
 *
 * The class Level1 contains all the stuff used ingame
 * including the background, drawing gamecharacter, pausebutton,
 * foodcounting, drawing food, rendering tiledmap etc...
 *
 * @author Vilho Stenman and Eerik Timonen
 * @version 2016.0509
 */
public class Level1 implements Screen {
    private static int omenaCount;
    public static int badfoodcount;
    public static float Playersize=7;
    private teami game;
    public static Music musiikki;
    public static Music musiikki2;
    public static Music tasoLapiMusa;
    public static Sound hyppy;
    private Sound syonti;
    private Sound kasvu;
    private Body PlayerBody;
    private TiledMap tiledMap;
    private TiledMapRenderer tiledMapRenderer;
    private SpriteBatch batch;
    private Box2DDebugRenderer debugRenderer;
    private OrthographicCamera camera;
    public static OrthographicCamera fontCamera;
    private Body Wall;
    public static int proteiiniCount;
    public static int hiilariCount;
    public static int salaattiCount;
    public static int juomaCount;
    public static Vector3 touchpoint;
    private Body Collectable;
    public float WORLD_HEIGHT_METERS =6f;
    public float WORLD_WIDTH_METERS = 12f;
    public float WORLD_HEIGHT_PIXELS =600f;
    public float WORLD_WIDTH_PIXELS = 1200f;
    private Texture taustaTexture;
    private ParallaxBackground pBackground;
    private TextureRegion bg;
    private TextureAtlas atlas;
    private World world;
    private Texture playerTexture;
    private boolean IsInAir=false;
    public static boolean paused ;
    Array<Body> removalBodies = new Array<Body>();
    private Texture omenaTexture;
    private Texture muroTexture;
    private Texture hodariTexture;
    private Texture banaaniTexture;
    private Texture donitsiTexture;
    private Texture pizzaTexture;
    private Texture ranskisTexture;
    private Texture mansikkaTexture;
    private Texture juustoTexture;
    private Texture hamppariTexture;
    private Texture munakasTexture;
    private Texture perunaTexture;
    private Texture kalaTexture;
    private Texture salaattiTexture;
    private Texture esTexture;
    private Texture suklaaTexture;
    private Texture puuroTexture;
    private Texture maitoTexture;
    private Texture croisanttiTexture;
    private Texture leipaTexture;
    private Texture lihaTexture;
    private Texture pauseNappi;
    public Boolean SoundOn;
    public static boolean kuoli;
    private Rectangle backToMainMenu;
    private Rectangle backToLevelSelect;
    Array<Body> edibleBodies = new Array<Body>();
    public static Rectangle pausenappi;
    public static boolean tapped;
    public static Music kuolema;


    /**
     * Creater for all the stuff in this class.
     *
     *@param g is the game
     */
    public Level1 (teami g){
        tapped = false;
        kuoli = false;
        paused=false;
        SoundOn=MainMenuScreen.Sound_ON_OFF;
        proteiiniCount = 0;
        hiilariCount = 0;
        salaattiCount = 0;
        juomaCount = 0;
        kuolema = Gdx.audio.newMusic(Gdx.files.internal("mus_nutrirun_death_001.ogg"));
        backToMainMenu = new Rectangle(WORLD_WIDTH_PIXELS/2-150, WORLD_HEIGHT_PIXELS/2,205f,30f);
        backToLevelSelect = new Rectangle(WORLD_WIDTH_PIXELS/2-150, WORLD_HEIGHT_PIXELS/2-100,355f,30f);
        hyppy = Gdx.audio.newSound(Gdx.files.internal("sfx_jump_001.ogg"));
        syonti = Gdx.audio.newSound(Gdx.files.internal("sfx_powerup_001.ogg"));
        kasvu = Gdx.audio.newSound(Gdx.files.internal("sfx_get_fat.ogg"));
        musiikki = Gdx.audio.newMusic(Gdx.files.internal("MUS_Ruokaloikka_level_002_loop.ogg"));
        musiikki2 = Gdx.audio.newMusic(Gdx.files.internal("MUS_Ruokaloikka_level_002_intro.ogg"));
        tasoLapiMusa = Gdx.audio.newMusic(Gdx.files.internal("mus_levelclear.ogg"));
        MainMenuScreen.musiikki.stop();
        MainMenuScreen.musiikki2.stop();
        if(SoundOn) {
            musiikki2.play();
            musiikki.setLooping(true);
        }else {
            musiikki2.pause();
            musiikki.pause();
        }
        omenaCount = 0;

        atlas = new TextureAtlas();

        atlas.addRegion("Tausta1",new Texture("Tausta1.png"),0,0,1200,670);
        atlas.addRegion("Tausta2",new Texture("Tausta2.png"),0,0,1200,670);
        atlas.addRegion("Tausta3",new Texture("Tausta3.png"),0,0,1200,670);
        atlas.addRegion("Tausta4",new Texture("Tausta4.png"),0,0,1200,670);

        pBackground = new ParallaxBackground(new ParallaxLayer[]{
                new ParallaxLayer(atlas.findRegion("Tausta4"),new Vector2(0f,0f),new Vector2(0, 0)),
                new ParallaxLayer(atlas.findRegion("Tausta3"),new Vector2(0.1f,0),new Vector2(0, 0)),
                new ParallaxLayer(atlas.findRegion("Tausta2"),new Vector2(0.05f,0),new Vector2(0, 0)),
                new ParallaxLayer(atlas.findRegion("Tausta1"),new Vector2(0,0),new Vector2(0,0),new Vector2(0, 0))
        }, 1200, 670,new Vector2(150,0));
        pausenappi= new Rectangle(10f,520f,80f,80f);
        game = g;
        touchpoint = new Vector3();
        world = new World(new Vector2(0, -9.8f), true);
        PlayerBody=Player.createPlayer(world);
        batch = game.getBatch();
        if (LevelSelect.leveli==1) {
            tiledMap = new TmxMapLoader().load("ekamappi.tmx");
        }
        if(LevelSelect.leveli==2){
            tiledMap = new TmxMapLoader().load("kolmasmappi.tmx");
        }
        if(LevelSelect.leveli==3){
            tiledMap = new TmxMapLoader().load("viidesmappi.tmx");
        }
        if(LevelSelect.leveli==4){
            tiledMap = new TmxMapLoader().load("neljasmappi.tmx");
        }
        if(LevelSelect.leveli==5){
            tiledMap = new TmxMapLoader().load("kuudesmappi.tmx");
        }

        tiledMapRenderer = new OrthogonalTiledMapRenderer(tiledMap,1/100f);
        camera = new OrthographicCamera();
        camera.setToOrtho(false,
                WORLD_WIDTH_METERS,
                WORLD_HEIGHT_METERS);
        fontCamera = new OrthographicCamera();
        fontCamera.setToOrtho(false, WORLD_WIDTH_PIXELS, WORLD_HEIGHT_PIXELS);
        transformWallsToBodies("Lattialayer", "Wall");
        transformWallsToBodies("Omenalayer", "Omena");
        transformWallsToBodies("Hodarilayer", "Hodari");
        transformWallsToBodies("Banaanilayer", "Banaani");
        transformWallsToBodies("Murolayer", "Muro");
        transformWallsToBodies("Donitsilayer", "Donitsi");
        transformWallsToBodies("Pizzalayer", "Pizza");
        transformWallsToBodies("Ranskislayer", "Ranskis");
        transformWallsToBodies("Mansikkalayer", "Mansikka");
        transformWallsToBodies("Juustolayer", "Juusto");
        transformWallsToBodies("Hampparilayer", "Hamppari");
        transformWallsToBodies("Munakaslayer", "Munakas");
        transformWallsToBodies("Perunalayer", "Peruna");
        transformWallsToBodies("Kalalayer", "Kala");
        transformWallsToBodies("Salaattilayer", "Salaatti");
        transformWallsToBodies("Eslayer", "Es");
        transformWallsToBodies("Suklaalayer", "Suklaa");
        transformWallsToBodies("Puurolayer", "Puuro");
        transformWallsToBodies("Maitolayer", "Maito");
        transformWallsToBodies("Croisanttilayer", "Croisantti");
        transformWallsToBodies("Leipälayer", "Leipa");
        transformWallsToBodies("Lihalayer", "Liha");



        omenaTexture = new Texture(Gdx.files.internal("omena.png"));
        muroTexture = new Texture(Gdx.files.internal("muro.png"));
        hodariTexture = new Texture(Gdx.files.internal("hodari.png"));
        banaaniTexture = new Texture(Gdx.files.internal("banaani.png"));
        donitsiTexture= new Texture(Gdx.files.internal("donitsi.png"));
        pizzaTexture= new Texture(Gdx.files.internal("pizza.png"));
        ranskisTexture= new Texture(Gdx.files.internal("ranskalaiset.png"));
        mansikkaTexture= new Texture(Gdx.files.internal("mansikka.png"));
        juustoTexture= new Texture(Gdx.files.internal("juusto.png"));
        hamppariTexture= new Texture(Gdx.files.internal("hamppari.png"));
        munakasTexture= new Texture(Gdx.files.internal("munakas.png"));
        perunaTexture= new Texture(Gdx.files.internal("peruna.png"));
        kalaTexture= new Texture(Gdx.files.internal("kala.png"));
        salaattiTexture= new Texture(Gdx.files.internal("salaatti.png"));
        esTexture= new Texture(Gdx.files.internal("es.png"));
        suklaaTexture= new Texture(Gdx.files.internal("suklaa.png"));
        puuroTexture= new Texture(Gdx.files.internal("puuro.png"));
        maitoTexture= new Texture(Gdx.files.internal("maito.png"));
        croisanttiTexture= new Texture(Gdx.files.internal("croisantti.png"));
        leipaTexture= new Texture(Gdx.files.internal("leipa.png"));
        lihaTexture= new Texture(Gdx.files.internal("liha.png"));
        pauseNappi = new Texture(Gdx.files.internal("Pause.png"));



        debugRenderer = new Box2DDebugRenderer();


        world.setContactListener(

                /**
                 * Listens the contacts made by user.
                 *
                 */
                new ContactListener() {
                    /**
                     *
                     * Is called when a contact begins.
                     *
                     * @param contact is the contact made by user
                     */
            @Override
            public void beginContact(Contact contact) {
                Body body1 = contact.getFixtureA().getBody();
                Body body2 = contact.getFixtureB().getBody();

                if (body1 == PlayerBody && body2.getUserData() == "Wall" || body1.getUserData() == "Wall" && body2 == PlayerBody) {
                    IsInAir = false;

                }
                if (body1 == PlayerBody && body2.getUserData() == "Omena" || body2 == PlayerBody && body1.getUserData() == "Omena" ||
                        body1 == PlayerBody && body2.getUserData() == "Hodari" || body2 == PlayerBody && body1.getUserData() == "Hodari" ||
                        body1 == PlayerBody && body2.getUserData() == "Donitsi" || body2 == PlayerBody && body1.getUserData() == "Donitsi" ||
                        body1 == PlayerBody && body2.getUserData() == "Pizza" || body2 == PlayerBody && body1.getUserData() == "Pizza" ||
                        body1 == PlayerBody && body2.getUserData() == "Muro" || body2 == PlayerBody && body1.getUserData() == "Muro" ||
                        body1 == PlayerBody && body2.getUserData() == "Ranskis" || body2 == PlayerBody && body1.getUserData() == "Ranskis" ||
                        body1 == PlayerBody && body2.getUserData() == "Mansikka" || body2 == PlayerBody && body1.getUserData() == "Mansikka" ||
                        body1 == PlayerBody && body2.getUserData() == "Juusto" || body2 == PlayerBody && body1.getUserData() == "Juusto" ||
                        body1 == PlayerBody && body2.getUserData() == "Hamppari" || body2 == PlayerBody && body1.getUserData() == "Hamppari" ||
                        body1 == PlayerBody && body2.getUserData() == "Munakas" || body2 == PlayerBody && body1.getUserData() == "Munakas" ||
                        body1 == PlayerBody && body2.getUserData() == "Peruna" || body2 == PlayerBody && body1.getUserData() == "Peruna" ||
                        body1 == PlayerBody && body2.getUserData() == "Kala" || body2 == PlayerBody && body1.getUserData() == "Kala" ||
                        body1 == PlayerBody && body2.getUserData() == "Salaatti" || body2 == PlayerBody && body1.getUserData() == "Salaatti" ||
                        body1 == PlayerBody && body2.getUserData() == "Es" || body2 == PlayerBody && body1.getUserData() == "Es" ||
                        body1 == PlayerBody && body2.getUserData() == "Suklaa" || body2 == PlayerBody && body1.getUserData() == "Suklaa" ||
                        body1 == PlayerBody && body2.getUserData() == "Puuro" || body2 == PlayerBody && body1.getUserData() == "Puuro" ||
                        body1 == PlayerBody && body2.getUserData() == "Maito" || body2 == PlayerBody && body1.getUserData() == "Maito" ||
                        body1 == PlayerBody && body2.getUserData() == "Croisantti" || body2 == PlayerBody && body1.getUserData() == "Croisantti" ||
                        body1 == PlayerBody && body2.getUserData() == "Leipa" || body2 == PlayerBody && body1.getUserData() == "Leipa" ||
                        body1 == PlayerBody && body2.getUserData() == "Liha" || body2 == PlayerBody && body1.getUserData() == "Liha" ||
                        body1 == PlayerBody && body2.getUserData() == "Banaani" || body2 == PlayerBody && body1.getUserData() == "Banaani") {

                    contact.setEnabled(false);

                    if(body2 != PlayerBody){
                        removalBodies.add(contact.getFixtureB().getBody());
                    }
                    else {removalBodies.add(contact.getFixtureA().getBody());}

                    if (body1.getUserData() == "Hodari" || body2.getUserData() == "Hodari" ||
                            body1.getUserData() == "Muro" || body2.getUserData() == "Muro" ||
                            body1.getUserData() == "Donitsi" || body2.getUserData() == "Donitsi" ||
                            body1.getUserData() == "Pizza" || body2.getUserData() == "Pizza" ||
                            body1.getUserData() == "Ranskis" || body2.getUserData() == "Ranskis" ||
                            body1.getUserData() == "Hamppari" || body2.getUserData() == "Hamppari" ||
                            body1.getUserData() == "Suklaa" || body2.getUserData() == "Suklaa" ||
                            body1.getUserData() == "Croisantti" || body2.getUserData() == "Croisantti" ||
                            body1.getUserData() == "Es" || body2.getUserData() == "Es") {
                        badfoodcount++;
                    }
                    if (body1.getUserData() == "Mansikka" || body2.getUserData() == "Mansikka" ||
                            body1.getUserData() == "Juusto" || body2.getUserData() == "Juusto" ||
                            body1.getUserData() == "Munakas" || body2.getUserData() == "Munakas" ||
                            body1.getUserData() == "Peruna" || body2.getUserData() == "Peruna" ||
                            body1.getUserData() == "Kala" || body2.getUserData() == "Kala" ||
                            body1.getUserData() == "Salaatti" || body2.getUserData() == "Salaatti" ||
                            body1.getUserData() == "Puuro" || body2.getUserData() == "Puuro" ||
                            body1.getUserData() == "Maito" || body2.getUserData() == "Maito" ||
                            body1.getUserData() == "Leipa" || body2.getUserData() == "Leipa" ||
                            body1.getUserData() == "Omena" || body2.getUserData() == "Omena" ||
                            body1.getUserData() == "Banaani" || body2.getUserData() == "Banaani" ||
                            body1.getUserData() == "Liha" || body2.getUserData() == "Liha") {
                        if(SoundOn) {
                            syonti.play();
                        }
                        Gdx.app.log("",""+body1.getUserData());
                        Gdx.app.log("", "" + body2.getUserData());
                    }

                    if (body1.getUserData() == "Liha" || body2.getUserData() == "Liha" ||
                            body1.getUserData() == "Kala" || body2.getUserData() == "Kala" ||
                            body1.getUserData() == "Hamppari" || body2.getUserData() == "Hamppari" ||
                            body1.getUserData() == "Hodari" || body2.getUserData() == "Hodari" ||
                            body1.getUserData() == "Munakas" || body2.getUserData() == "Munakas" ||
                            body1.getUserData() == "Juusto" || body2.getUserData() == "Juusto" ||
                            body1.getUserData() == "Pizza" || body2.getUserData() == "Pizza") {
                        proteiiniCount++;

                    }
                    if (body1.getUserData() == "Peruna" || body2.getUserData() == "Peruna" ||
                            body1.getUserData() == "Puuro" || body2.getUserData() == "Puuro" ||
                            body1.getUserData() == "Leipa" || body2.getUserData() == "Leipa" ||
                            body1.getUserData() == "Croisantti" || body2.getUserData() == "Croisantti" ||
                            body1.getUserData() == "Ranskis" || body2.getUserData() == "Ranskis" ||
                            body1.getUserData() == "Hamppari" || body2.getUserData() == "Hamppari" ||
                            body1.getUserData() == "Hodari" || body2.getUserData() == "Hodari" ||
                            body1.getUserData() == "Donitsi" || body2.getUserData() == "Donitsi" ||
                            body1.getUserData() == "Pizza" || body2.getUserData() == "Pizza") {
                        hiilariCount++;

                    }
                    if (body1.getUserData() == "Maito" || body2.getUserData() == "Maito" ||
                            body1.getUserData() == "Es" || body2.getUserData() == "Es") {
                        juomaCount++;

                    }
                    if (body1.getUserData() == "Salaatti" || body2.getUserData() == "Salaatti" ||
                            body1.getUserData() == "Banaani" || body2.getUserData() == "Banaani" ||
                            body1.getUserData() == "Omena" || body2.getUserData() == "Omena" ||
                            body1.getUserData() == "Mansikka" || body2.getUserData() == "Mansikka") {
                        salaattiCount++;

                    }


                }

            }

                    /**
                     *
                     * Is called when a contact ends.
                     *
                     * @param contact is the contact made by user
                     */
                    @Override
            public void endContact(Contact contact) {
                Body body1 = contact.getFixtureA().getBody();
                Body body2 = contact.getFixtureB().getBody();
                if (body1 == PlayerBody && body2.getUserData() == "Wall" || body1.getUserData() == "Wall" && body2 == PlayerBody) {
                    IsInAir = true;

                }

            }

                    /**
                     *
                     * Is called before contact is solved.
                     *
                     * @param contact is the contact made by user
                     * @param oldManifold is something I don't know about
                     */
                    @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }

                    /**
                     *
                     * Is called after contact is solved.
                     *
                     * @param contact is the contact made by user
                     * @param impulse is something I don't know about
                     */
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {

            }
        });


    }


    /**
     *
     * Transforms walls to bodies.
     *
     * @param layer is the name of the layer in tmx
     * @param userData is the data set to the layer
     */
    private void transformWallsToBodies(String layer, String userData) {
        // Let's get the collectable rectangles layer
        MapLayer collisionObjectLayer = tiledMap.getLayers().get(layer);

        // All the rectangles of the layer
        MapObjects mapObjects = collisionObjectLayer.getObjects();

        // Cast it to RectangleObjects array
        Array<RectangleMapObject> rectangleObjects = mapObjects.getByType(RectangleMapObject.class);

        // Iterate all the rectangles
        for (RectangleMapObject rectangleObject : rectangleObjects) {
            Rectangle tmp = rectangleObject.getRectangle();

            // SCALE given rectangle down if using world dimensions!
            Rectangle rectangle = scaleRect(tmp, 1 / 100f);
            if(!userData.equals("Wall")){
                createEdibleBody(rectangle, userData);
            }
            else{createStaticBody(rectangle, userData);}

        }
    }


    /**
     *
     * Creates a static body.
     *
     * @param rect is the rectangle that we are converting
     * @param userData is the data set to the rectangle
     */
    public void createStaticBody(Rectangle rect, String userData) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;

        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);

        Wall = world.createBody(myBodyDef);

        Wall.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);

        Wall.createFixture(groundBox, 0.0f);
    }

    /**
     *
     * Creates foodbodies from rectangles.
     *
     * @param rect is the rectangle we are converting into a body
     * @param userData is the data set to the rectangle
     */
    public void createEdibleBody(Rectangle rect, String userData) {
        BodyDef myBodyDef = new BodyDef();
        myBodyDef.type = BodyDef.BodyType.StaticBody;
        float x = rect.getX();
        float y = rect.getY();
        float width = rect.getWidth();
        float height = rect.getHeight();

        float centerX = width/2 + x;
        float centerY = height/2 + y;

        myBodyDef.position.set(centerX, centerY);

        Collectable = world.createBody(myBodyDef);

        Collectable.setUserData(userData);
        // Create shape
        PolygonShape groundBox = new PolygonShape();

        // Real width and height is 2 X this!
        groundBox.setAsBox(width / 2, height / 2);

        Collectable.createFixture(groundBox, 0.0f);
        Collectable.getFixtureList().get(0).setSensor(true);



    }


    /**
     *
     * Scales the rectangle to it to be the correct size.
     *
     * @param r is the rectangle we are scaling
     * @param scale is the amount of scalement
     */
    private Rectangle scaleRect(Rectangle r, float scale) {
        Rectangle rectangle = new Rectangle();
        rectangle.x      = r.x * scale;
        rectangle.y      = r.y * scale;
        rectangle.width  = r.width * scale;
        rectangle.height = r.height * scale;
        return rectangle;
    }


    private double accumulator = 0;
    private float TIME_STEP = 1 / 60f;

    /**
     *
     * Moves the world to the next frame.
     *
     * @param deltaTime is the deltatime
     */
    private void doPhysicsStep(float deltaTime) {

        float frameTime = deltaTime;

        // If it took ages (over 4 fps, then use 4 fps)
        // Avoid of "spiral of death"
        if(deltaTime > 1 / 4f) {
            frameTime = 1 / 4f;
        }

        accumulator += frameTime;

        while (accumulator >= TIME_STEP) {
            // It's a fixed time step!
            world.step(TIME_STEP, 8, 3);
            accumulator -= TIME_STEP;
        }
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
     * Renders all the stuff in gamescreen
     *
     *@param delta is the deltatime
     */
    @Override
    public void render(float delta) {

        batch.setProjectionMatrix(fontCamera.combined);
        if (paused){
            pBackground.render(delta);
            batch.begin();

            Player.checkUserInput(IsInAir, paused, game);
            paused = Player.pause2;
            checkIfPaused();

            moveCamera();
            game.getFont().getData().setScale(0.5f);
            game.getFont().draw(batch, teami.myBundle.get("levelSelect"), backToLevelSelect.getX(), backToLevelSelect.getY() + 35);
            game.getFont().draw(batch, teami.myBundle.get("restart"), backToMainMenu.getX(), backToMainMenu.getY() + 35);
            game.getFont().getData().setScale(1f);
            game.getFont().getData().setScale(0.5f);
            game.getFont().draw(batch, teami.myBundle.get("protein") + proteiiniCount, 1100, 570);
            game.getFont().draw(batch, teami.myBundle.get("carbohydrate") + hiilariCount, 1000, 570);
            game.getFont().draw(batch, teami.myBundle.get("vegetable") + salaattiCount, 900, 570);
            game.getFont().draw(batch, teami.myBundle.get("drink") + juomaCount, 800, 570);
            game.getFont().getData().setScale(1f);
            batch.draw(pauseNappi, 0, 520, 80f, 80f);

            batch.end();
            if(paused){
                musiikki.pause();
            }
            else{
                if(SoundOn) {
                    if (!musiikki2.isPlaying()) {
                        musiikki.play();
                    }
                }else {
                    musiikki.pause();
                }
            }

        }
        else if (tapped==false){
            checkIfPaused();
            batch.setProjectionMatrix(fontCamera.combined);
            pBackground.render(delta);
            batch.begin();
            drawPlayer();
            game.getFont().getData().setScale(0.5f);
            game.getFont().setColor(Color.GREEN);
            game.getFont().draw(batch, teami.myBundle.get("gameInfo2"), 10, 595);
            game.getFont().draw(batch, teami.myBundle.get("gameInfo3"), 10, 540);
            game.getFont().setColor(Color.ORANGE);
            game.getFont().draw(batch, teami.myBundle.get("gameInfo4"), 10, 450);
            game.getFont().draw(batch, teami.myBundle.get("gameInfo5"), 10, 395);
            game.getFont().getData().setScale(0.85f);
            game.getFont().setColor(Color.PINK);
            game.getFont().draw(batch, teami.myBundle.get("gameInfo1"), 10, 300);
            game.getFont().setColor(Color.WHITE);
            game.getFont().getData().setScale(1f);
            batch.setProjectionMatrix(camera.combined);

            tiledMapRenderer.setView(camera);


            drawEdibles();
            camera.update();

            fontCamera.update();
            tiledMapRenderer.render();
            batch.end();
            doPhysicsStep(Gdx.graphics.getDeltaTime());
            if(paused){
                musiikki.pause();
            }
            else{
                if(SoundOn) {
                    if (!musiikki2.isPlaying()) {
                        musiikki.play();
                    }
                }else {
                    musiikki.pause();
                }
            }

        }
        else {

            if(paused){
                musiikki.pause();
            }
            else{
                if(SoundOn) {
                    if (!musiikki2.isPlaying()) {
                        musiikki.play();
                    }
                }else {
                    musiikki.pause();
                }
            }

            batch.setProjectionMatrix(fontCamera.combined);

            //Gdx.gl.glClearColor(0, 0, 1, 1);
           // Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            pBackground.render(delta);
            checkIfPaused();

            Player.Playermovement();

            batch.begin();
            game.getFont().getData().setScale(0.5f);
            game.getFont().setColor(Color.RED);
            game.getFont().draw(batch, teami.myBundle.get("protein") + proteiiniCount, 1100, 570);
            game.getFont().setColor(Color.GOLDENROD);
            game.getFont().draw(batch, teami.myBundle.get("carbohydrate") + hiilariCount, 1000, 570);
            game.getFont().setColor(Color.GREEN);
            game.getFont().draw(batch, teami.myBundle.get("vegetable") + salaattiCount, 900, 570);
            game.getFont().setColor(Color.WHITE);
            game.getFont().draw(batch, teami.myBundle.get("drink") + juomaCount, 800, 570);
            game.getFont().getData().setScale(1f);

            drawPlayer();
            batch.draw(pauseNappi, 0, 520, 80f, 80f);


            batch.setProjectionMatrix(camera.combined);

            tiledMapRenderer.setView(camera);


            drawEdibles();

            badfoodspeed();


            //Gdx.app.log("", "" + PlayerBody.getPosition().x);

            camera.update();

            fontCamera.update();
            tiledMapRenderer.render();
            clearCollectable();
            Player.checkUserInput(IsInAir, paused, game);
            paused = Player.pause2;
            Player.checkIfPlayerIsDead(game);

            batch.end();

            //debugRenderer.render(world, camera.combined);
            moveCamera();
            doPhysicsStep(Gdx.graphics.getDeltaTime());


        }
    }

    /**
     * Draws the playerCharacter.
     *
     *
     */
    private void drawPlayer() {
        if(Playersize<=6){
            if (PlayerBody.getPosition().x<WORLD_WIDTH_METERS/2f){
                batch.draw(Player.currentFrameTexture2, PlayerBody.getPosition().x * 100f - 40f, PlayerBody.getPosition().y * 100f - 25f, Player.playerTexture2.getWidth() / Playersize, Player.playerTexture2.getHeight() / 2.8f);

            }
            else if(PlayerBody.getPosition().y<WORLD_HEIGHT_METERS/2f){
                batch.draw(Player.currentFrameTexture2, 565f,PlayerBody.getPosition().y*100f-25f,Player.playerTexture2.getWidth()/Playersize,Player.playerTexture2.getHeight()/2.8f);

            }

            else{
                batch.draw(Player.currentFrameTexture2, 565f,275f,Player.playerTexture2.getWidth()/Playersize,Player.playerTexture2.getHeight()/2.8f);

            }

            Player.stateTime += Gdx.graphics.getDeltaTime();
            Player.currentFrameTexture2 = Player.walkAnimation2.getKeyFrame(Player.stateTime, true);
        }
        else{

            if (PlayerBody.getPosition().x<WORLD_WIDTH_METERS/2f){
             batch.draw(Player.currentFrameTexture, PlayerBody.getPosition().x * 100f - 40f, PlayerBody.getPosition().y * 100f - 25f, Player.playerTexture.getWidth() / Playersize, Player.playerTexture.getHeight() / 2.8f);

            }
            else if(PlayerBody.getPosition().y<WORLD_HEIGHT_METERS/2f){
             batch.draw(Player.currentFrameTexture, 565f,PlayerBody.getPosition().y*100f-25f,Player.playerTexture.getWidth()/Playersize,Player.playerTexture.getHeight()/2.8f);

            }

            else{
                batch.draw(Player.currentFrameTexture, 565f,275f,Player.playerTexture.getWidth()/Playersize,Player.playerTexture.getHeight()/2.8f);

            }

            Player.stateTime += Gdx.graphics.getDeltaTime();
            Player.currentFrameTexture = Player.walkAnimation.getKeyFrame(Player.stateTime, true);
        }
    }

    /**
     * Checks if the game is paused.
     *
     *
     */
    private void checkIfPaused() {
        if (Gdx.input.justTouched()) {
            tapped=true;
            fontCamera.unproject(touchpoint.set(Gdx.input.getX(), Gdx.input.getY(), 0));
            if (pausenappi.contains(touchpoint.x, touchpoint.y)) {


                paused = !paused;

            }
            if(paused){
                if (backToLevelSelect.contains(touchpoint.x,touchpoint.y)) {
                    badfoodcount=0;
                    Player.speed=3;
                    Playersize=7;
                    game.setScreen(game.getLevelSelect());

                }
                if (backToMainMenu.contains(touchpoint.x,touchpoint.y)){
                    badfoodcount=0;
                    Player.speed=3;
                    Playersize=7;
                    game.setScreen(game.getLevel1());


                }
            }
        }
    }

    /**
     * Counts the apples.
     *
     *This method is never used but I'm too scared to remove it
     */
    public static int getOmenaCount(){
        return omenaCount;
    }

    /**
     * Readjusts the playerspeed, playersize and plays a sound whenever you eat a bad food.
     *
     *
     */
    public void badfoodspeed(){
        if (badfoodcount==2&&Playersize!=6){
            Player.speed=2.5f;
            Playersize=6;
            if(SoundOn) {
                kasvu.play();
            }
        }
        if (badfoodcount==3&&Playersize!=5){
            Player.speed=2.25f;
            Playersize=5;
            if(SoundOn) {
                kasvu.play();
            }
        }
        if (badfoodcount==4&&Playersize!=4){
            Player.speed=1.75f;
            Playersize=4;
            if(SoundOn) {
                kasvu.play();
            }
        }
        if (badfoodcount==5&&Playersize!=3){
            Player.speed=1.5f;
            Playersize=3;
            if(SoundOn) {
                kasvu.play();
            }
        }
        if (badfoodcount==6&&Playersize!=2){
            Player.speed=1.25f;
            Playersize=2;
            if(SoundOn) {
                kasvu.play();
            }
        }


    }

    /**
     * Draws the eatable stuff.
     *
     * This method is so awful looking that I got ebola from it
     *
     */
    private void drawEdibles() {
        world.getBodies(edibleBodies);
        //System.out.println(world.getBodyCount());
       // System.out.println(edibleBodies.size);


        for (Body body: edibleBodies){


                if (body.getUserData() == "Omena") {
                    batch.draw(omenaTexture,
                            body.getPosition().x - 0.15f,
                            body.getPosition().y - 0.15f,
                            omenaTexture.getWidth() / 70f,
                            omenaTexture.getHeight() / 70f);
                }
                if (body.getUserData() == "Hodari") {
                    batch.draw(hodariTexture,
                            body.getPosition().x - 0.15f,
                            body.getPosition().y - 0.15f,
                            hodariTexture.getWidth() / 70f,
                            hodariTexture.getHeight() / 70f);
                }
                if (body.getUserData() == "Muro") {
                    batch.draw(muroTexture,
                            body.getPosition().x - 0.15f,
                            body.getPosition().y - 0.15f,
                            muroTexture.getWidth() / 70f,
                            muroTexture.getHeight() / 70f);
                }
                if (body.getUserData() == "Banaani") {
                    batch.draw(banaaniTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        banaaniTexture.getWidth() / 70f,
                        banaaniTexture.getHeight() / 70f);
                }
            if (body.getUserData() == "Donitsi") {
                batch.draw(donitsiTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        donitsiTexture.getWidth() / 70f,
                        donitsiTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Pizza") {
                batch.draw(pizzaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        pizzaTexture.getWidth() / 70f,
                        pizzaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Ranskis") {
                batch.draw(ranskisTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        ranskisTexture.getWidth() / 70f,
                        ranskisTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Mansikka") {
                batch.draw(mansikkaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        mansikkaTexture.getWidth() / 70f,
                        mansikkaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Juusto") {
                batch.draw(juustoTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        juustoTexture.getWidth() / 70f,
                        juustoTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Hamppari") {
                batch.draw(hamppariTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        hamppariTexture.getWidth() / 70f,
                        hamppariTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Munakas") {
                batch.draw(munakasTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        munakasTexture.getWidth() / 70f,
                        munakasTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Peruna") {
                batch.draw(perunaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        perunaTexture.getWidth() / 70f,
                        perunaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Kala") {
                batch.draw(kalaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        kalaTexture.getWidth() / 70f,
                        kalaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Salaatti") {
                batch.draw(salaattiTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        salaattiTexture.getWidth() / 70f,
                        salaattiTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Es") {
                batch.draw(esTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        esTexture.getWidth() / 70f,
                        esTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Suklaa") {
                batch.draw(suklaaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        suklaaTexture.getWidth() / 70f,
                        suklaaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Puuro") {
                batch.draw(puuroTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        puuroTexture.getWidth() / 70f,
                        puuroTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Maito") {
                batch.draw(maitoTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        maitoTexture.getWidth() / 70f,
                        maitoTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Croisantti") {
                batch.draw(croisanttiTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        croisanttiTexture.getWidth() / 70f,
                        croisanttiTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Leipa") {
                batch.draw(leipaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        leipaTexture.getWidth() / 70f,
                        leipaTexture.getHeight() / 70f);
            }
            if (body.getUserData() == "Liha") {
                batch.draw(lihaTexture,
                        body.getPosition().x - 0.15f,
                        body.getPosition().y - 0.15f,
                        lihaTexture.getWidth() / 70f,
                        lihaTexture.getHeight() / 70f);
            }




        }
    }


    /**
     * Moves the camera.
     *
     *
     */
    private void moveCamera() {
        if(PlayerBody.getPosition().x>WORLD_WIDTH_METERS/2f) {
            camera.position.set(PlayerBody.getPosition().x,
                    WORLD_HEIGHT_METERS/2f,
                    0);
            if(PlayerBody.getPosition().y>WORLD_HEIGHT_METERS/2f){
                camera.position.set(PlayerBody.getPosition().x,
                        PlayerBody.getPosition().y,
                        0);
            }

        }
        else if(PlayerBody.getPosition().y>WORLD_HEIGHT_METERS/2f){
            if(PlayerBody.getPosition().y>WORLD_HEIGHT_METERS/2f){
                camera.position.set(WORLD_WIDTH_METERS/2f,
                        PlayerBody.getPosition().y,
                        0);
            }
        }



    }
    public static void pauseMusic(){

        musiikki.stop();
    }


    /**
     * Resizes the gamescreen.
     *
     *@param width is the width of the screen/window
     *@param height is the height of the screen/window
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * Pauses the gamescreen if needed
     */
    @Override
    public void pause() {

    }

    /**
     * Resumes the gamescreen if needed
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
        tiledMap.dispose();
        atlas.dispose();
        hyppy.dispose();
        syonti.dispose();
        kasvu.dispose();
        musiikki.dispose();
        muroTexture.dispose();
        omenaTexture.dispose();
        hodariTexture.dispose();
        tasoLapiMusa.dispose();
        taustaTexture.dispose();
        banaaniTexture.dispose();
        musiikki2.dispose();
        salaattiTexture.dispose();
        suklaaTexture.dispose();
        esTexture.dispose();
        ranskisTexture.dispose();
        pauseNappi.dispose();
        perunaTexture.dispose();
        pizzaTexture.dispose();
        puuroTexture.dispose();
        playerTexture.dispose();
        donitsiTexture.dispose();
        hamppariTexture.dispose();
        juustoTexture.dispose();
        kalaTexture.dispose();
        mansikkaTexture.dispose();
        munakasTexture.dispose();
        leipaTexture.dispose();
        lihaTexture.dispose();
        croisanttiTexture.dispose();
        maitoTexture.dispose();
        tasoLapiMusa.dispose();

        world.dispose();
    }

    /**
     * Clears all the edibles when they are eaten.
     *
     *
     */
    public void clearCollectable() {


        for(Body body: removalBodies) {
            world.destroyBody(body);


        }

        removalBodies.clear();
    }
}
