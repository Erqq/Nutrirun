package fi.teami.peli;

/**
 * Created by c4vstenm on 29.3.2016.
 */

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Does some stuff to help the playercharacter animation.
 *
 * @author Vilho Stenman
 */
public class Utilities {

    public static TextureRegion[] toTextureArray( TextureRegion [][]tableRow, int columns, int rows ) {
        int index = 0;
        TextureRegion [] frames= new TextureRegion[columns * rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                frames[index++] = tableRow[i][j];}
        }
        return frames;
    }



}

