package com.sega.mobile.framework.device;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.MediaPlayer;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.utility.MFUtility;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import com.sega.mobile.framework.device.MFSound;

public final class MFPlayer implements MediaPlayer.OnCompletionListener {
    public static final int CLOSED = 4;
    public static final int PREFETCHED = 2;
    public static final int REALIZED = 1;
    public static final int STARTED = 3;
    private static final String[] STATE_NAME = {"UNREALIZED", "REALIZED", "PREFETCHED", "STARTED", "CLOSED"};
    public static final int UNREALIZED = 0;
    private long duration;
    private MediaPlayer mPlayer;
    private int mediaTimeForSet = 0;
    private String soundIdentifier;
    private boolean soundLoop;
    protected int soundPriority;
    private int soundState;
    private int soundType;
    protected String soundUrl;
    private int soundVolume;
    public static boolean se = false;
    public boolean isSe = false;

    public static MFPlayer createMFPlayer(String url, boolean z) {
        se = z;
        return new MFPlayer(url, 0);
    }

    public static MFPlayer createMFPlayer(InputStream stream, String type) throws IOException {
        if (type.indexOf("mid") != -1) {
            type = ".mid";
        } else if (type.indexOf("wav") != -1) {
            type = ".wav";
        } else if (type.indexOf("mpeg") != -1) {
            type = ".mp3";
        }
        String file = "tempsound" + type;
        FileOutputStream fos = MFMain.getInstance().openFileOutput(file, 0);
        byte[] buf = new byte[1024];
        while (true) {
            int len = stream.read(buf);
            if (len <= 0) {
                fos.flush();
                fos.close();
                return new MFPlayer(file, 1);
            }
            fos.write(buf, 0, len);
        }
    }

    private MFPlayer(String url, int type) {
        this.soundIdentifier = url;
        MFDebug.loadSound(this.soundIdentifier);
        this.soundUrl = url;
        this.soundType = type;
        this.soundLoop = false;
        this.soundState = 0;
        this.soundVolume = 100;
        this.duration = 0;
    }

    public void setLoop(boolean loop) {
        this.soundLoop = loop;
    }

    public boolean isLoop() {
        return this.soundLoop;
    }

    public void setMediaTime(int msec) {
        if (this.mPlayer == null || this.soundState != 3) {
            this.mediaTimeForSet = msec;
        } else {
            this.mPlayer.seekTo(msec);
        }
    }

    public void setVolume(int volume) {
        try {
        this.soundVolume = MFUtility.getValueInRange(volume, 0, 100);
        if (this.mPlayer != null) {
            this.mPlayer.setVolume(((float) this.soundVolume) / 100.0f, ((float) this.soundVolume) / 100.0f);
        }
        } catch (Exception e) {
        }
    }

    public int getVolume() {
        return this.soundVolume;
    }

    public int getState() {
        return this.soundState;
    }

    public void realize() {
        switch (soundState) {
            case 0:
            case 4:
                try {
                    String str;
                    if (soundType == 0) {
                        AssetManager assets = MFMain.getInstance().getAssets();
                        if (soundUrl.startsWith("/")) {
                            str = soundUrl.substring(1);
                        } else {
                            str = soundUrl;
                        }
                        AssetFileDescriptor afd = assets.openFd(str);
                        mPlayer = new MediaPlayer();
                        mPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
                        afd.close();
                    } else {
                        FileInputStream fis = MFMain.getInstance().openFileInput(soundUrl);
                        mPlayer = new MediaPlayer();
                        mPlayer.setDataSource(fis.getFD());
                        fis.close();
                    }
                    mPlayer.setAudioStreamType(3);
                    mPlayer.prepare();
                    duration = (long) mPlayer.getDuration();
                    mPlayer.setOnCompletionListener(MFPlayer.this);
                    soundState = 1;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            case 1:
            case 2:
            case 3:
                mediaError(soundUrl, "realize", STATE_NAME[soundState]);
                return;
            default:
                return;
        }
    }

    public void prefetch() {
        switch (this.soundState) {
            case 0:
            case 2:
            case 3:
            case 4:
                mediaError(this.soundUrl, "prefetch", STATE_NAME[this.soundState]);
                return;
            case 1:
                try {
                    this.soundState = 2;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    public void start() {
        switch (soundState) {
            case 0:
            case 1:
            case 3:
            case 4:
                mediaError(soundUrl, "start", STATE_NAME[soundState]);
                return;
            case 2:
                try {
                    mPlayer.seekTo(mediaTimeForSet);
                    mediaTimeForSet = 0;
                    MediaPlayer mediaPlayer = mPlayer;
                    mediaPlayer.setLooping(soundLoop);
                    mPlayer.start();
                    soundState = 3;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }

    public void stop() {
        switch (this.soundState) {
            case 0:
            case 1:
            case 4:
                mediaError(this.soundUrl, "stop", STATE_NAME[this.soundState]);
                return;
            case 3:
                try {
                    this.mPlayer.stop();
                    this.soundState = 2;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            default:
                return;
        }
    }
    
    public void pause() {
                try {
                    this.mPlayer.pause();
                    this.soundState = 2;
                    return;
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
    }

    public void deallocate() {
        switch (this.soundState) {
            case 0:
            case 1:
            case 4:
                mediaError(this.soundUrl, "deallocate", STATE_NAME[this.soundState]);
                return;
            case 2:
                break;
            case 3:
                stop();
                break;
            default:
                return;
        }
        try {
            this.mPlayer.release();
            this.soundState = 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void close() {
        switch (this.soundState) {
            case 0:
            case 1:
                break;
            case 2:
                break;
            case 3:
                stop();
                break;
            default:
                return;
        }
        deallocate();
        if (this.mPlayer != null) {
            this.mPlayer.setOnCompletionListener((MediaPlayer.OnCompletionListener) null);
            this.mPlayer.release();
        }
        this.mPlayer = null;
        this.soundState = 4;
    }

    public long getMediaTime() {
        if (this.mPlayer == null) {
            return 0;
        }
        return (long) this.mPlayer.getCurrentPosition();
    }

    public long getDuration() {
        return this.duration;
    }

    /* access modifiers changed from: protected */
    public void tick() {
    }

    private static void mediaError(String url, String function, String state) {
    }

    public void onCompletion(MediaPlayer mp) {
        if (!this.soundLoop) {
            this.soundState = 2;
        }
    }

    /* access modifiers changed from: protected */
    public void finalize() throws Throwable {
        MFDebug.releaseSound(this.soundIdentifier);
        super.finalize();
    }
}
