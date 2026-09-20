package Lib;

/** Test-only audio sink; no Android audio threads/devices. */
public final class SoundSystem {
    private static final SoundSystem INSTANCE = new SoundSystem();
    public int bgmStarts;
    private boolean playing;
    public static SoundSystem getInstance() { return INSTANCE; }
    public void playBgm(int id) { playBgm(id, true); }
    public void playBgm(int id, boolean loop) { bgmStarts++; playing = true; }
    public void stopBgm(boolean dispose) { playing = false; }
    public void resumeBgm() { playing = true; }
    public boolean bgmPlaying() { return playing; }
    public void playSe(int id) { }
    public void playSe(int id, boolean loop) { }
    public void playSequenceSe(int id) { }
    public void preLoadSequenceSe(int id) { }
    public void setSoundState(int value) { }
    public void setSeState(int value) { }
    public void setSoundSpeed(float value) { }
}
