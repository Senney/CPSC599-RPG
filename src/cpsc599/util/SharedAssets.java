package cpsc599.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import static cpsc599.util.Logger.*;

public class SharedAssets {
    static {
        debug("Loading static shared assets in a very hacky manner.");
    }

    public static String PRIMARY_ASSET_FOLDER = "assets/tilesets/primary/";
    public static boolean loaded = false;

    public static TextureRegion[][] menu_texture;
    public static void load() {
        if (loaded) return;

        Logger.debug("Loading shared assets...");
        Texture menu = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Menus/menu_empty.png"));
        menu_texture = TextureRegion.split(menu, 16, 16);

        loaded = true;
    }

}
