package cpsc599.assets;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.GdxRuntimeException;
import cpsc599.OrbGame;
import cpsc599.util.Logger;

/**
 * Basic class for an animated sprite.
 * Requires that all sprites for an animation be in the same row.
 */
public class AnimatedSprite {

    private Texture spriteSheet;
    private TextureRegion[] spriteFrames;
    private TextureRegion currentFrame;
    private Animation spriteAnimation;

    /**
     * Loads the specified sprite.
     * @param filename
     */
    public AnimatedSprite(String filename, int xoff, int yoff, int xsize, int ysize, int frames, float frameTime) {
        loadSprite(filename, xoff, yoff, xsize, ysize, frames, frameTime);
    }

    /**
     * Loads a sprite from the filesystem and parses it.
     * @param filename
     * @param xoff
     * @param yoff
     * @param xsize
     * @param ysize
     * @param frames
     * @param frameTime
     * @return True if the sprite was loaded successfully.
     */
    public boolean loadSprite(String filename, int xoff, int yoff, int xsize, int ysize, int frames, float frameTime) {
        Logger.debug("Loading sprite: " + filename);
        try {
            spriteSheet = new Texture(filename);
        } catch (NullPointerException ex) {
            Logger.fatal("Unable to load sprite at: " + filename);
            return false;
        } catch (GdxRuntimeException ex) {
            Logger.fatal("Unable to load sprite at: " + filename);
            return false;
        }
        TextureRegion[][] tempRegion = TextureRegion.split(spriteSheet, xsize, ysize);
        spriteFrames = new TextureRegion[frames];
        for (int i = 0; i < frames; i++) {
            spriteFrames[i] = tempRegion[yoff][xoff + i];
            spriteFrames[i].flip(false, true);
        }
        spriteAnimation = new Animation(frameTime, spriteFrames);
        return true;
    }

    public Texture getFrame(int frameCount) {
        if (frameCount > spriteFrames.length - 1) {
            Logger.error("Unable to get frame " + frameCount + " from animation.");
            return null;
        }

        return spriteFrames[frameCount].getTexture();
    }

    public void render(SpriteBatch batch, int x, int y) {
        if (this.spriteAnimation == null) {
            return;
        }

        currentFrame = spriteAnimation.getKeyFrame(OrbGame.frameTime, true);
        batch.draw(currentFrame, x, y);
    }
}
