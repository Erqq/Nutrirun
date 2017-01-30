package fi.teami.peli;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 * ParallaxLayer contains stuff that help the ParallaxBackground class.
 *
 *
 * @author Eerik Timonen
 * @version 2016.0509
 */
public class ParallaxLayer {
    public TextureRegion region ;
    public Vector2 parallaxRatio;
    public Vector2 startPosition;
    public Vector2 padding ;

    /**
     * Adds padding to parallaxlayer.
     *
     * @param padding is the padding used
     * @param parallaxRatio is the ratio of parallax
     * @param region is the textureregion
     */
    public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 padding){
        this(region, parallaxRatio, new Vector2(0,0),padding);
    }

    /**
     * Updates some stuff.
     *
     * @param padding is the padding used
     * @param parallaxRatio is the ratio of parallax
     * @param region is the textureregion
     * @param startPosition is the starting position
     */
    public ParallaxLayer(TextureRegion region,Vector2 parallaxRatio,Vector2 startPosition,Vector2 padding){
        this.region  = region;
        this.parallaxRatio = parallaxRatio;
        this.startPosition = startPosition;
        this.padding = padding;
    }
}

