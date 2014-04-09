package cpsc599.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import cpsc599.assets.AnimatedSprite;
import sun.font.TrueTypeFont;

import static cpsc599.util.Logger.*;

public class SharedAssets {
    static {
        debug("Loading static shared assets in a very hacky manner.");
    }

    public static String SCRIPT = "assets/Script/";
    public static String PROLOGUE = SCRIPT + "prologue.xml";
    public static String CHAPTER_1 = SCRIPT + "chapter1.xml";
    public static String CHAPTER_2 = SCRIPT + "chapter2.xml";
    public static String CHAPTER_3 = SCRIPT + "chapter3.xml";
    public static String CHAPTER_4 = SCRIPT + "chapter4.xml";
    public static String CHAPTER_5 = SCRIPT + "chapter5.xml";

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

    public static Texture orangeHouse;
    public static Texture greenHouse;

    public static Texture hikariPortrait;
    public static Texture princePortrait;
    public static Texture seanPortrait;
    public static Texture sashaPortrait;
    public static Texture cowCubePortrait;
    public static Texture jackPortrait;

    public static BitmapFont font_14;
    public static BitmapFont font_12;

    public static AnimatedSprite hikariSprite;
    public static AnimatedSprite seanSprite;
    public static AnimatedSprite renSprite;
    public static AnimatedSprite sashaSprite;
    public static AnimatedSprite jackSprite;

    public static AnimatedSprite cowCubeSprite;
    public static TextureRegion healthShrine;


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
        sashaPortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/female2.png"));
        seanPortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/male1.png"));
        jackPortrait = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/male3.png"));

        hikariSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/female.png", 0, 0, 16, 16, 1, 0.1f);
        seanSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 3, 0, 16, 16, 1, 0.1f);
        sashaSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/female.png", 2, 0, 16, 16, 1, 0.1f);
        renSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 0, 0, 16, 16, 1, 0.1f);
        jackSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 2, 0, 16, 16, 1, 0.1f);

        healthShrine = loadTexture(PRIMARY_ASSET_FOLDER + "Items/healthshrine.png", true);

        loadEnemies();

        TextureRegion[][] houseRow = TextureRegion.split(new Texture(PRIMARY_ASSET_FOLDER + "Town/house.png"), 16, 16);
        orangeHouse = houseRow[0][1].getTexture();
        greenHouse = houseRow[0][3].getTexture();

        loadFont();

        loaded = true;
    }

    private static TextureRegion loadTexture(String filename, boolean flip) {
        Texture tex = new Texture(Gdx.files.internal(filename));
        TextureRegion region = new TextureRegion(tex);
        region.flip(false, flip);
        return region;
    }

    private static void loadEnemies() {
        cowCubeSprite = new AnimatedSprite("assets/tilesets/primary/Enemy/Monsters/cow_cube.png", 0,0,16,16,1,0.1f);
    }

    private static void loadFont() {
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("assets/prstartk.ttf"));
        font_14 = generator.generateFont(14);
        font_12 = generator.generateFont(12);
    }
}
