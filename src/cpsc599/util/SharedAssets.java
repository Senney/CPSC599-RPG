package cpsc599.util;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static cpsc599.util.Logger.*;

public class SharedAssets {
    static {
        debug("Loading static shared assets in a very hacky manner.");
    }

    public static String PRIMARY_ASSET_FOLDER = "assets/tilesets/primary/";

    private static Texture menu_texture = new Texture(PRIMARY_ASSET_FOLDER + "Menus/menu_empty.png");
    private static TextureRegion[][] menu_regions = TextureRegion.split(menu_texture, 16, 16);

    // Required texture regions for the various portions of the Menu.
    // NOTE: This is a very hacky way to do it, but since the asset is shared between a few different places, it's
    // not overly the worst way to do it.
    // Just... don't do this in any other projects, okay?
    public static class MenuAssets {
        public static TextureRegion tl = menu_regions[0][0];
        public static TextureRegion tm = menu_regions[0][1];
        public static TextureRegion tr = menu_regions[0][2];
        public static TextureRegion ml = menu_regions[1][0];
        public static TextureRegion mm = menu_regions[1][1];
        public static TextureRegion mr = menu_regions[1][2];
        public static TextureRegion bl = menu_regions[2][0];
        public static TextureRegion bm = menu_regions[2][1];
        public static TextureRegion br = menu_regions[2][2];
    }
}
