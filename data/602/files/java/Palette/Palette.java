package Palette;

public class Palette {
    int[] colors;

    public Palette(int count) {
        this.colors = new int[count];
    }

    public Palette(int[] color) {
        this.colors = new int[color.length];
        for (int i = 0; i < color.length; i++) {
            this.colors[i] = color[i];
        }
    }

    public int getEntryCount() {
        return this.colors.length;
    }

    public int getEntry(int num) {
        return this.colors[num];
    }

    public void setEntry(int num, int color) {
        this.colors[num] = color;
    }
}
