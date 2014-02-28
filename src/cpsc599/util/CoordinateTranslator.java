package cpsc599.util;

/**
 * Simple static class to translate between game coordinates to real coordinates.
 */
public class CoordinateTranslator {
    public static int TILE_SIZE = 16;

    public static int translate(int x) {
        return x * TILE_SIZE;
    }
}
