package cpsc599.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import sun.font.TrueTypeFont;

import static cpsc599.util.Logger.*;

public class SharedAssets {
    static {
        debug("Loading static shared assets in a very hacky manner.");
    }

    public static String PRIMARY_ASSET_FOLDER = "assets/tilesets/primary/";
    public static boolean loaded = false;

    public static TextureRegion[][] menu_texture;
    public static Texture menu_pointer;
    public static Texture highlight;
    public static Texture highlight2;
    public static Texture highlight3;

    public static Texture doorSwitch;
    public static Texture defaultPortrait;

    public static Texture cowCube;

    public static Texture hikariPortrait;
    public static Texture princePortrait;

    public static BitmapFont font_14;
    public static BitmapFont font_12;


    public static void load() {
        if (loaded) return;

        Logger.debug("Loading shared assets...");
        Texture menu = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Menus/menu_empty.png"));
        menu_texture = TextureRegion.split(menu, 16, 16);

        menu_pointer = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Menus/pointer.png"));
        highlight = new Texture(Gdx.files.internal("assets/tilesets/" + "Enemy.png"));
        highlight2 = new Texture(Gdx.files.internal("assets/tilesets/" + "testsquare.png"));
        highlight3 = new Texture(Gdx.files.internal("assets/tilesets/" + "highlight-player.png"));

        cowCube = new Texture(Gdx.files.internal("assets/tilesets/cowcube.png"));

        doorSwitch = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Buttons/A_button3.png"));
        defaultPortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/enemy/Monster/cow_cube.png"));

        hikariPortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/hikari.png"));
        princePortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/prince.png"));

        loadFont();

        loaded = true;
    }

    private static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/prstartk.ttf"));
        font_14 = generator.generateFont(14);
        font_12 = generator.generateFont(12);
    }
}
