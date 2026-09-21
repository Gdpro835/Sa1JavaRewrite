package Lib;

import java.util.ArrayList;
import java.util.List;

/** Test-only audio sink; records requested cues, never claims to play Android audio. */
public final class SoundSystem {
    private static final SoundSystem INSTANCE = new SoundSystem();
    public int bgmStarts;
    public final List<Integer> bgmCues = new ArrayList<Integer>();
    public final List<Integer> effects = new ArrayList<Integer>();
    private boolean playing, bgmFlag = true, seFlag = true;
    private int bgm = -1, loopEffect = -1;
    public static SoundSystem getInstance() { return INSTANCE; }
    public static void open() { }
    public void exec() { }
    public void playBgm(int id) { playBgm(id, true); }
    public void playBgm(int id, boolean loop) { bgmStarts++; bgm = id; bgmCues.add(id); playing = true; }
    public void playNextBgm(int id) { playBgm(id); }
    public void playBgmInSpeed(int id, boolean loop, float speed) { playBgm(id, loop); }
    public void playBgmFromTime(long start, int id) { playBgm(id); }
    public void playBgmSequence(int first, int second) { playBgm(first); }
    public void playBgmSequenceNoLoop(int first, int second) { playBgm(first, false); }
    public void stopBgm(boolean dispose) { playing = false; }
    public void restartBgm() { playing = true; }
    public void resumeBgm() { playing = true; }
    public boolean bgmPlaying() { return playing; }
    public boolean bgmPlaying2() { return playing; }
    public int getPlayingBGMIndex() { return bgm; }
    public String getBgmName(int id) { return String.valueOf(id); }
    public void playSe(int id) { effects.add(id); }
    public void playSe(int id, boolean loop) { effects.add(id); }
    public void playLongSe(int id) { effects.add(id); }
    public void playLoopSe(int id) { loopEffect = id; }
    public int getPlayingLoopSeIndex() { return loopEffect; }
    public boolean isLoopSePlaying() { return loopEffect >= 0; }
    public void stopLoopSe() { loopEffect = -1; }
    public void stopLongSe() { }
    public void resumeLoopSe() { }
    public void playSequenceSe(int id) { effects.add(id); }
    public void preLoadSequenceSe(int id) { }
    public void playSequenceSeSingle() { }
    public void preLoadAllSe() { }
    public void setSoundState(int value) { }
    public void setSeState(int value) { }
    public void setSoundSpeed(float value) { }
    public void setVolume(int value) { }
    public void setVolumnState(int value) { }
    public void updateVolumeState() { }
    public void setBgmFlag(boolean value) { bgmFlag = value; }
    public boolean getBgmFlag() { return bgmFlag; }
    public void setSeFlag(boolean value) { seFlag = value; }
    public boolean getSeFlag() { return seFlag; }
}
