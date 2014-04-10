package cpsc599.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import cpsc599.assets.AnimatedSprite;
import sun.font.TrueTypeFont;

import java.util.HashMap;

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
    public static TextureRegion menu_pointer;
    public static TextureRegion cursorNormal;
    public static TextureRegion cursorHighlighted;
    public static TextureRegion highlight;
    public static TextureRegion highlight2;
    public static TextureRegion highlight3;

    public static TextureRegion doorOpen, doorClosed;
    public static TextureRegion doorSwitch;
    public static TextureRegion defaultPortrait;

    public static Texture cowCube;

    public static TextureRegion orangeHouse;
    public static TextureRegion greenHouse;

    public static TextureRegion hikariPortrait;
    public static TextureRegion princePortrait;
    public static TextureRegion seanPortrait;
    public static TextureRegion sashaPortrait;
    public static TextureRegion cowCubePortrait;
    public static TextureRegion jackPortrait;
    public static TextureRegion prisonerPortrait;

    public static BitmapFont font_14;
    public static BitmapFont font_12;

    public static AnimatedSprite hikariSprite;
    public static AnimatedSprite seanSprite;
    public static AnimatedSprite renSprite;
    public static AnimatedSprite sashaSprite;
    public static AnimatedSprite jackSprite;
    public static AnimatedSprite prisonerSprite;

    public static AnimatedSprite cowCubeSprite;
    public static TextureRegion healthShrine;
    public static TextureRegion armour;
    public static TextureRegion sword;
    public static TextureRegion shield;
    public static TextureRegion amulet;

    private static HashMap<String, Texture> textureCache;

    public static void load() {
        if (loaded) return;

        Logger.debug("Loading shared assets...");
        textureCache = new HashMap<String, Texture>();

        Texture menu = new Texture(Gdx.files.internal(PRIMARY_ASSET_FOLDER + "Menus/menu_empty.png"));
        menu_texture = TextureRegion.split(menu, 16, 16);

        menu_pointer = loadTexture(PRIMARY_ASSET_FOLDER + "Menus/pointer.png");
        highlight = loadTexture("assets/tilesets/" + "Enemy.png");
        highlight2 = loadTexture("assets/tilesets/" + "testsquare.png");
        highlight3 = loadTexture("assets/tilesets/" + "highlight-player.png");

        cowCube = new Texture(Gdx.files.internal("assets/tilesets/cowcube.png"));

        doorSwitch = loadTexture(PRIMARY_ASSET_FOLDER + "Buttons/A_button3.png");
        defaultPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/enemy/Monster/cow_cube.png");

        hikariPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/hikari.png");
        princePortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/prince.png");
        sashaPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/female2.png");
        seanPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/male1.png");
        jackPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/male3.png");
        prisonerPortrait = loadTexture(PRIMARY_ASSET_FOLDER + "Character_profile_box/hero/male2.png");

        hikariSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/female.png", 0, 0, 16, 16, 1, 0.1f);
        seanSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 3, 0, 16, 16, 1, 0.1f);
        sashaSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/female.png", 2, 0, 16, 16, 1, 0.1f);
        renSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 0, 0, 16, 16, 1, 0.1f);
        jackSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 2, 0, 16, 16, 1, 0.1f);
        prisonerSprite = new AnimatedSprite(PRIMARY_ASSET_FOLDER + "CharacterDesign/male.png", 1, 0, 16, 16, 1, 0.1f);

        cursorNormal = loadTextureRegion("assets/tilesets/cursor.png", 16, 16, 0, 0, false);
        cursorHighlighted = loadTextureRegion("assets/tilesets/cursor.png", 16, 16, 1, 0, false);

        healthShrine = loadTexture(PRIMARY_ASSET_FOLDER + "Items/healthshrine.png", true);
        armour = loadTexture(PRIMARY_ASSET_FOLDER + "Items/armour.png", true);
        sword = loadTexture(PRIMARY_ASSET_FOLDER + "Items/sword1.png", true);
        shield = loadTexture(PRIMARY_ASSET_FOLDER + "Items/shield3.png", true);
        amulet = loadTexture(PRIMARY_ASSET_FOLDER + "Items/amulet1.png", true);
        doorOpen = loadTextureRegion(PRIMARY_ASSET_FOLDER + "Town/walls.png", 16, 16, 0, 0, true);
        doorClosed = loadTextureRegion(PRIMARY_ASSET_FOLDER + "Town/walls.png", 16, 16, 2, 2, true);


        loadEnemies();

        TextureRegion[][] houseRow = TextureRegion.split(new Texture(PRIMARY_ASSET_FOLDER + "Town/house.png"), 16, 16);
        orangeHouse = loadTextureRegion(PRIMARY_ASSET_FOLDER + "Town/house.png", 16, 16, 0, 1, true);
        greenHouse = loadTextureRegion(PRIMARY_ASSET_FOLDER + "Town/house.png", 16, 16, 0, 3, true);

        loadFont();

        loaded = true;
    }

    private static Texture loadTextureFile(String filename) {
        Texture tex = null;
        if (textureCache.containsKey(filename)) {
            Logger.debug("Retrieving texture from cache: " + filename);
            tex = textureCache.get(filename);
        } else {
            Logger.debug("Caching Texture: " + filename);
            tex = new Texture(Gdx.files.internal(filename));
            textureCache.put(filename, tex);
        }
        return tex;
    }

    private static TextureRegion loadTextureRegion(String filename, int w, int h, int x, int y, boolean flip) {
        Texture tex = loadTextureFile(filename);
        TextureRegion[][] region = TextureRegion.split(tex, w, h);
        TextureRegion target = region[y][x];
        target.flip(false, flip);
        return target;
    }

    private static TextureRegion loadTexture(String filename) {
        return loadTexture(filename, false);
    }

    private static TextureRegion loadTexture(String filename, boolean flip) {
        Texture tex = loadTextureFile(filename);
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
