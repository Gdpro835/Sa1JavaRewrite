package com.sega.mobile.framework.device;

import android.media.AudioManager;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.utility.MFUtility;
import java.util.Vector;
import State.TitleState;

public final class MFSound {
    public static int seLimit = 15;
    public static final int LEVEL_MAX = 15;
    public static final int VOLUME_MAX = 100;

    private static MFPlayer bgm;
    private static MFPlayer lastBgm;
    private static MFPlayer nextBgm;

    private static boolean bgmFlag;
    private static boolean bgmPlaying;
    private static boolean bgmStarted;

    private static boolean seFlag;
    private static boolean resumeFlag;
    private static boolean suspendFlag;
    private static boolean deviceInterrupted;

    private static int soundVolume;

    private static AudioManager mManager;

    private static Vector<MFPlayer> soundVector;
    private static Vector<MFPlayer> seVector;
    private static Vector<MFPlayer> prefetchedSeVector;

    private static MFPlayer createMFPlayer(String url) {
        MFPlayer player = MFPlayer.createMFPlayer(url, false);
        soundVector.addElement(player);
        return player;
    }

    private static boolean updateBgm(MFPlayer player) {
        switch (player.getState()) {
            case 0: // UNREALIZED
            case 4: // CLOSED
                player.realize();
                return false;
            case 1: // REALIZED
                player.prefetch();
                return false;
            case 2: // PREFETCHED
                player.start();
                return true;
            case 3:
            default:
                return false;
        }
    }
    
    private static boolean updateSound(MFPlayer player) {
        switch (player.getState()) {
            case 0:
            case 4:
                player.realize();
                return false;
            case 1:
                player.prefetch();
                return false;
            case 2:
                return true;
            case 3:
            default:
                return false;
        }
    }

    protected static void init() {
        bgmFlag = true;
        seFlag = true;
        deviceInterrupted = false;
        resumeFlag = false;
        suspendFlag = false;
        bgmPlaying = false;
        bgmStarted = false;
        soundVolume = 100;

        soundVector = new Vector<>();
        seVector = new Vector<>();
        prefetchedSeVector = new Vector<>();

        mManager = (AudioManager) MFMain.getInstance().getSystemService("audio");
    }

    protected static void tick() {
        for (int i = 0; i < soundVector.size(); i++) {
            soundVector.elementAt(i).tick();
        }

        for (int i = 0; i < seVector.size(); i++) {
            MFPlayer se = seVector.elementAt(i);
            if (se != null && updateSound(se)) {
                if (se.getState() != 2) {
                    seVector.removeElementAt(i);
                    i--;
                }
            }
        }

        if (bgmFlag) {
            if (suspendFlag) {
                if (!deviceInterrupted) {
                    deviceInterrupted = true;
                    stopBgm();
                }
                suspendFlag = false;
            }

            if (resumeFlag) {
                if (deviceInterrupted) {
                    deviceInterrupted = false;
                    resumeBgm();
                }
                resumeFlag = false;
            }

            if (nextBgm != null) {
                if (bgm != null && !nextBgm.soundUrl.equals(bgm.soundUrl)) {
                    bgm.stop();
                    bgm.deallocate();
                    bgm.close();
                }
                bgm = nextBgm;
                nextBgm = null;
                bgmStarted = false;
            }

            if (bgm != null && (bgm.isLoop() || !bgmStarted)) {
                bgmStarted = updateBgm(bgm);
            }

            if (bgm != null && !bgm.isLoop() && bgmStarted && bgm.getState() == 2) {
                bgmPlaying = false;
                bgmStarted = false;
                bgm.deallocate();
                bgm.close();
                bgm = null;
            }
        }

        for (int i = 0; i < prefetchedSeVector.size(); ) {
            MFPlayer se = prefetchedSeVector.elementAt(i);
            if (se.getState() == 2) {
                se.deallocate();
                se.close();
                prefetchedSeVector.removeElementAt(i);
            } else {
                i++;
            }
        }

        if (prefetchedSeVector.size() < seLimit) {
            if (seFlag) {
                while (!seVector.isEmpty()) {
                    MFPlayer player = seVector.elementAt(0);
                    if (player != null && updateSound(player)) {
                        seVector.removeElementAt(0);
                        prefetchedSeVector.addElement(player);
                    } else {
                        break;
                    }
                }
            } else {
                seVector.removeAllElements();
            }
        }
    }

    protected static void deviceInterrupt() {
        suspendFlag = true;
    }

    protected static void deviceResume() {
        resumeFlag = true;
    }

    public static void setVolume(int volume) {
        soundVolume = MFUtility.getValueInRange(volume, 0, 100);
        int maxVolume = mManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mManager.setStreamVolume(AudioManager.STREAM_MUSIC, (soundVolume * maxVolume) / 100, 0);
    }

    public static int getVolume() {
        int max = mManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        int current = mManager.getStreamVolume(AudioManager.STREAM_MUSIC);
        soundVolume = MFUtility.getValueInRange((current * 100) / max, 0, 100);
        return soundVolume;
    }

    public static void setLevel(int level) {
        mManager.setStreamVolume(AudioManager.STREAM_MUSIC, level, 0);
    }

    public static int getLevel() {
        return mManager.getStreamVolume(AudioManager.STREAM_MUSIC);
    }

    public static void setBgmFlag(boolean enabled) {
        bgmFlag = enabled;
        if (!enabled) stopBgm();
    }

    public static boolean getBgmFlag() {
        return bgmFlag;
    }

    public static void setSeFlag(boolean enabled) {
        seFlag = enabled;
        if (!enabled) seVector.removeAllElements();
    }

    public static boolean getSeFlag() {
        return seFlag;
    }

    public static void preloadSound(String url) {
        for (MFPlayer player : soundVector) {
            if (player.soundUrl.equals(url)) return;
        }
        MFPlayer player = createMFPlayer(url);
        player.realize();
        if (player != null) player.prefetch();
    }

    public static void playBgm(String url, boolean loop) {
        nextBgm = null;
        for (MFPlayer player : soundVector) {
            if (player.soundUrl.equals(url)) {
                nextBgm = player;
                break;
            }
        }
        if (nextBgm == null) {
            nextBgm = createMFPlayer(url);
        }
        nextBgm.setLoop(loop);
        bgmPlaying = true;
        lastBgm = nextBgm;
    }

    public static void stopBgm() {
        try {
            if (bgm != null) {
                bgm.stop();
                bgm.deallocate();
                bgm.close();
            }
        } catch (Exception ignored) {
        } finally {
            bgm = null;
            nextBgm = null;
            bgmPlaying = false;
            bgmStarted = false;
        }
    }

    public static void stopBgmNoResume() {
        stopBgm();
        lastBgm = null;
    }

    public static long getBgmMediaTime() {
        return (bgm != null) ? bgm.getMediaTime() : 0L;
    }

    public static void resumeBgm() {
        if (lastBgm != null && lastBgm.isLoop()) {
            bgmPlaying = true;
            nextBgm = lastBgm;
        }
    }

    public static boolean isBgmPlaying() {
        return bgmPlaying;
    }

    public static void playSe(String url) {
        playSe(url, 0);
    }

    public static void playSe(String url, int priority) {
    if (!seFlag) return;

    if (url.contains("se_103") && TitleState.characterslots == 2 && MFMain.tails == 7) {
        url = "/se/shc.ogg";
        MFMain.tails++;
        priority = 1;
    }

    MFPlayer player = null;
    synchronized (soundVector) {
    for (MFPlayer p : soundVector) {
        if (p.soundUrl.equals(url) && p.getState() == 2) {
            player = p;
            break;
        }
    }
    }

    if (player == null) {
        player = createMFPlayer(url);
        player.realize();
        if (player != null) player.prefetch();
    }

    if (player != null) {
    player.soundPriority = priority;
    player.setMediaTime(0);

    if (player.getState() == 2) {
        if (prefetchedSeVector.size() >= seLimit) {
            MFPlayer old = prefetchedSeVector.remove(0);
            old.deallocate();
            old.close();
        }
        player.start();
        prefetchedSeVector.addElement(player);
    } else {
        seVector.addElement(player);
    }
    }
}

    public static void releaseAllSound() {
        Vector<MFPlayer> temp = new Vector<>(soundVector);
        if (temp != null) {
        for (MFPlayer p : temp) {
            p.close();
        }
        }
        if (soundVector != null) soundVector.removeAllElements();
        if (seVector != null) seVector.removeAllElements();
        bgm = null;
        nextBgm = null;
    }

    public static void setBgm(MFPlayer player, boolean loop) {
        nextBgm = player;
        nextBgm.setLoop(loop);
        bgmPlaying = true;
        lastBgm = nextBgm;
    }

    public static MFPlayer getCurrentBgm() {
        return (nextBgm != null) ? nextBgm : bgm;
    }
}