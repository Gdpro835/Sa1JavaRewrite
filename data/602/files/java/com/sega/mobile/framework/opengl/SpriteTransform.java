package com.sega.mobile.framework.opengl;

/** MIDP sprite transforms, in destination corner order TL, TR, BR, BL. */
public final class SpriteTransform {
    private static final int[][] CORNERS = {
        {0, 1, 2, 3}, {3, 2, 1, 0}, {1, 0, 3, 2}, {2, 3, 0, 1},
        {0, 3, 2, 1}, {3, 0, 1, 2}, {1, 2, 3, 0}, {2, 1, 0, 3}
    };
    private SpriteTransform() { }
    public static boolean swapsAxes(int transform) { return (transform & 4) != 0; }
    public static void textureCoordinates(int transform, float left, float top,
            float right, float bottom, float[] result) {
        if (transform < 0 || transform > 7) throw new IllegalArgumentException("Sprite transform");
        for (int i = 0; i < 4; i++) {
            int c = CORNERS[transform][i];
            result[i * 2] = c == 0 || c == 3 ? left : right;
            result[i * 2 + 1] = c < 2 ? top : bottom;
        }
    }
}
