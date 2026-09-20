package Lib;

import PlatformStandard.Standard2;
import SonicGBA.GlobalResource;
import SonicGBA.PlayerObject;
import com.sega.mobile.framework.device.MFPlayer;
import com.sega.mobile.framework.device.MFSound;
import com.sega.mobile.framework.MFMain;

public class SoundSystem {
    public static final byte BGM_1UP = 43;
    public static final byte BGM_ASPHYXY = 21;
    public static final byte BGM_BOSS_01 = 22;
    public static final byte BGM_BOSS_02 = 23;
    public static final byte BGM_BOSS_03 = 24;
    public static final byte BGM_BOSS_04 = 25;
    public static final byte BGM_BOSS_F1 = 46;
    public static final byte BGM_BOSS_F2 = 47;
    public static final byte BGM_BOSS_FINAL3 = 19;
    public static final byte BGM_CLEAR_ACT1 = 26;
    public static final byte BGM_CLEAR_ACT2 = 27;
    public static final byte BGM_CLEAR_EX = 29;
    public static final byte BGM_CLEAR_FINAL = 28;
    public static final byte BGM_CONTINUE = 5;
    public static final byte BGM_CREDIT = 33;
    public static final byte BGM_ENDING_EX_01 = 32;
    public static final byte BGM_ENDING_EX_02 = 45;
    public static final byte BGM_ENDING_FINAL = 31;
    public static final byte BGM_GAMEOVER = 30;
    public static final byte BGM_INVINCIBILITY = 44;
    public static final byte BGM_NEWRECORD = 41;
    public static final byte BGM_OPENING = 0;
    public static final byte BGM_PLAYER_SELECT = 2;
    public static final byte BGM_RANKING = 4;
    public static final byte BGM_SP = 35;
    public static final byte BGM_SP_CLEAR = 37;
    public static final byte BGM_SP_EMERALD = 38;
    public static final byte BGM_SP_INTRO = 34;
    public static final byte BGM_SP_TOTAL_CLEAR = 40;
    public static final byte BGM_SP_TOTAL_MISS = 39;
    public static final byte BGM_SP_TRICK = 36;
    public static final byte BGM_ST1_1 = 6;
    public static final byte BGM_ST1_2 = 7;
    public static final byte BGM_ST2_1 = 8;
    public static final byte BGM_ST2_2 = 9;
    public static final byte BGM_ST3_1 = 10;
    public static final byte BGM_ST3_2 = 11;
    public static final byte BGM_ST4_1 = 12;
    public static final byte BGM_ST4_2 = 13;
    public static final byte BGM_ST5_1 = 14;
    public static final byte BGM_ST5_2 = 15;
    public static final byte BGM_ST6_1 = 16;
    public static final byte BGM_ST6_2 = 17;
    public static final byte BGM_ST_EX = 20;
    public static final byte BGM_ST_FINAL = 18;
    public static final byte BGM_TIMEATTACKGOAL = 42;
    public static final byte BGM_TITLE = 1;
    public static final byte BGM_ZONESELECT = 3;
    public static final boolean HAS_SE = false;
    public static final byte OP_PATCH = 80;
    public static final boolean PRE_LOAD_SE = true;
    public static final byte SE_103 = 0;
    public static final byte SE_106 = 1;
    public static final byte SE_107 = 2;
    public static final byte SE_108 = 3;
    public static final byte SE_109 = 4;
    public static final byte SE_110 = 5;
    public static final byte SE_111 = 6;
    public static final byte SE_112 = 7;
    public static final byte SE_113 = 79;
    public static final byte SE_114_01 = 8;
    public static final byte SE_114_02 = 9;
    public static final byte SE_115 = 10;
    public static final byte SE_116 = 11;
    public static final byte SE_117 = 12;
    public static final byte SE_118 = 13;
    public static final byte SE_119 = 14;
    public static final byte SE_120 = 15;
    public static final byte SE_121 = 16;
    public static final byte SE_123 = 17;
    public static final byte SE_125 = 18;
    public static final byte SE_126 = 19;
    public static final byte SE_127 = 20;
    public static final byte SE_128 = 21;
    public static final byte SE_130 = 22;
    public static final byte SE_131 = 23;
    public static final byte SE_132 = 24;
    public static final byte SE_133 = 25;
    public static final byte SE_135 = 26;
    public static final byte SE_136 = 27;
    public static final byte SE_137 = 28;
    public static final byte SE_138 = 29;
    public static final byte SE_139 = 30;
    public static final byte SE_140 = 31;
    public static final byte SE_141 = 32;
    public static final byte SE_142 = 33;
    public static final byte SE_143 = 34;
    public static final byte SE_144 = 35;
    public static final byte SE_145 = 36;
    public static final byte SE_148 = 37;
    public static final byte SE_149 = 38;
    public static final byte SE_150 = 39;
    public static final byte SE_162 = 81;
    public static final byte SE_166 = 40;
    public static final byte SE_168 = 41;
    public static final byte SE_169 = 42;
    public static final byte SE_171 = 43;
    public static final byte SE_172 = 44;
    public static final byte SE_173 = 45;
    public static final byte SE_174 = 46;
    public static final byte SE_175 = 47;
    public static final byte SE_176 = 48;
    public static final byte SE_177 = 82;
    public static final byte SE_178 = 49;
    public static final byte SE_179 = 83;
    public static final byte SE_180 = 50;
    public static final byte SE_181_01 = 51;
    public static final byte SE_181_02 = 52;
    public static final byte SE_182 = 53;
    public static final byte SE_183 = 54;
    public static final byte SE_184 = 55;
    public static final byte SE_185 = 56;
    public static final byte SE_189 = 57;
    public static final byte SE_190 = 58;
    public static final byte SE_191 = 59;
    public static final byte SE_192 = 60;
    public static final byte SE_193 = 61;
    public static final byte SE_194 = 62;
    public static final byte SE_195 = 63;
    public static final byte SE_198_01 = 64;
    public static final byte SE_198_02 = 65;
    public static final byte SE_199_01 = 66;
    public static final byte SE_199_02 = 67;
    public static final byte SE_200_01 = 68;
    public static final byte SE_200_02 = 69;
    public static final byte SE_201_01 = 70;
    public static final byte SE_201_02 = 71;
    public static final byte SE_206 = 72;
    public static final byte SE_209 = 73;
    public static final byte SE_210 = 74;
    public static final byte SE_211 = 75;
    public static final byte SE_212 = 76;
    public static final byte SE_213 = 77;
    public static final byte SE_214 = 78;
    private static final String SE_PATH = "/se/";
    public static final byte SHC = 84;
    private static SoundSystem instance;
    private static int volume = 46;
    private final String[] BgmName = {"bgm_01_opening", "bgm_02_title", "bgm_03_player_select", "bgm_04_zoneselect", "bgm_10_ranking", "bgm_155_continue", "bgm_12_st1_1", "bgm_13_st1_2", "bgm_14_st2_1", "bgm_15_st2_2", "bgm_16_st3_1", "bgm_17_st3_2", "bgm_18_st4_1", "bgm_19_st4_2", "bgm_20_st5_1", "bgm_21_st5_2", "bgm_22_st6_1", "bgm_23_st6_2", "bgm_24_st_final", "bgm_25_boss_final3", "bgm_26_st_ex", "bgm_27_asphyxy", "bgm_29_boss_01", "bgm_30_boss_02", "bgm_31_boss_03", "bgm_32_boss_04", "bgm_33_clear_act1", "bgm_34_clear_act2", "bgm_35_clear_final", "bgm_36_clear_ex", "bgm_37_gameover", "bgm_38_ending_final", "bgm_39_ending_ex_01", "bgm_40_credit", "bgm_41_sp_intro", "bgm_42_sp", "bgm_43_sp_trick", "bgm_44_sp_clear", "bgm_45_sp_emerald", "bgm_46_sp_total_miss", "bgm_47_sp_total_clear", "bgm_305_NewRecord", "bgm_306_TimeAttackGoal", "bgm_307_1up", "bgm_28_invincibility", "bgm_39_ending_ex_02", "bgm_49_boss_f1", "bgm_50_boss_f2"};
    private final String[] BgmNameSpeeding = {"bgm_12_st1_1_speed", "bgm_13_st1_2_speed", "bgm_14_st2_1_speed", "bgm_15_st2_2_speed", "bgm_16_st3_1_speed", "bgm_17_st3_2_speed", "bgm_18_st4_1_speed", "bgm_19_st4_2_speed", "bgm_20_st5_1_speed", "bgm_21_st5_2_speed", "bgm_22_st6_1_speed", "bgm_23_st6_2_speed", "bgm_28_invincibility_speed"};
    private String BgmPrefix;
    private final String BgmType;
    private final int[][] INTRO_MSEC;
    public String Path;
    private final String[] SE_NAME;
    private final int[] VOLUME_OPTION;
    private int bgmIndex;
    private int currentIntroMsec;
    private int currentLoopMsec;
    private MFPlayer longSeplayer;
    private long mediaTime;
    private int nextBgmIndex;
    private boolean nextBgmLoop;
    private boolean nextBgmWaiting;
    private float preSpeed;
    private int seIndex;
    private MFPlayer seplayer;
    private float speed;

    public SoundSystem() {
        int[] iArr = new int[2];
        iArr[1] = 32000;
        int[] iArr2 = new int[2];
        iArr2[1] = 78800;
        int[] iArr3 = new int[2];
        iArr3[1] = 72000;
        int[] iArr4 = new int[2];
        iArr4[1] = 64066;
        int[] iArr5 = new int[2];
        iArr5[1] = 71032;
        int[] iArr6 = new int[2];
        iArr6[1] = 71032;
        int[] iArr7 = new int[2];
        iArr7[1] = 88432;
        int[] iArr8 = new int[2];
        iArr8[1] = 67200;
        int[] iArr9 = new int[2];
        iArr9[1] = 78732;
        int[] iArr10 = new int[2];
        iArr10[1] = 69833;
        this.INTRO_MSEC = new int[][]{new int[2], new int[2], iArr, new int[]{9433, 25433}, new int[]{1432, 18132}, new int[2], new int[]{1800, 73800}, iArr2, new int[]{7066, 91566}, new int[]{7500, 70666}, new int[]{4432, 90832}, iArr3, new int[]{17000, 68432}, iArr4, iArr5, iArr6, new int[]{12600, 70200}, new int[]{5566, 78966}, iArr7, iArr8, iArr9, new int[2], new int[]{3800, 48366}, new int[]{3132, 57500}, new int[]{6200, 56266}, new int[]{31866, 123066}, new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[]{6167, 68133}, new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], new int[2], iArr10, new int[]{17067, 51333}};
        this.BgmType = ".ogg";
        this.SE_NAME = new String[]{"se_103.ogg", "se_106.wav", "se_107.wav", "se_108.wav", "se_109.ogg", "se_110.ogg", "se_111.ogg", "se_112.ogg", "se_114_01.ogg", "se_114_02.ogg", "se_115.ogg", "se_116.ogg", "se_117.ogg", "se_118.ogg", "se_119.ogg", "se_120.ogg", "se_121.ogg", "se_123.ogg", "se_125.ogg", "se_126.ogg", "se_127.ogg", "se_128.ogg", "se_130.ogg", "se_131.ogg", "se_132.ogg", "se_133.ogg", "se_135.ogg", "se_136.ogg", "se_137.ogg", "se_138.ogg", "se_139.ogg", "se_140.ogg", "se_141.wav", "se_142.ogg", "se_143.ogg", "se_144.ogg", "se_145.ogg", "se_148.ogg", "se_149.ogg", "se_150.ogg", "se_166.ogg", "se_168.ogg", "se_169.ogg", "se_171.ogg", "se_172.ogg", "se_173.ogg", "se_174.ogg", "se_175.ogg", "se_176.ogg", "se_178.ogg", "se_180.ogg", "se_181_01.ogg", "se_181_02.ogg", "se_182.ogg", "se_183.ogg", "se_184.ogg", "se_185.ogg", "se_189.ogg", "se_190.ogg", "se_191.ogg", "se_192.ogg", "se_193.ogg", "se_194.ogg", "se_195.ogg", "se_198_01.ogg", "se_198_02.ogg", "se_199_01.ogg", "se_199_02.ogg", "se_200_01.ogg", "se_200_02.ogg", "se_201_01.ogg", "se_201_02.ogg", "se_206.ogg", "se_209.ogg", "se_210.ogg", "se_211.ogg", "se_212.ogg", "se_213.ogg", "se_214.ogg", "se_113.ogg", "op_patch.ogg", "se_162.ogg", "se_177.ogg", "se_179.ogg", "shc.ogg"};
        this.BgmPrefix = "";
        this.nextBgmWaiting = false;
        this.nextBgmIndex = 0;
        this.speed = 1.0f;
        this.preSpeed = 1.0f;
        this.mediaTime = 0;
        int[] iArr11 = new int[4];
        iArr11[1] = 25;
        iArr11[2] = 60;
        iArr11[3] = 95;
        this.VOLUME_OPTION = iArr11;
    }

    private int getSpeedyVersionBgm(int bgmID) {
        if (bgmID >= 6 && bgmID < 18) {
            return bgmID - 6;
        }
        if (bgmID == 44) {
            return 12;
        }
        return -1;
    }

    public void exec() {
        MFPlayer currentBGM;
        if (Standard2.soundtrack != 1) {
            this.Path = "/mid0/";
        } else {
            this.Path = "/mid1/";
        }
        if (this.nextBgmWaiting && !bgmPlaying()) {
            this.nextBgmWaiting = false;
            if (!PlayerObject.isTerminal) {
                playBgm(this.nextBgmIndex, this.nextBgmLoop);
            }
        }
        if (!this.nextBgmWaiting && this.currentLoopMsec > 0 && (currentBGM = MFSound.getCurrentBgm()) != null && currentBGM.getState() == 3) {
            if (PlayerObject.IsSpeedUp()) {
                if (currentBGM.getMediaTime() >= ((long) (this.currentLoopMsec >> 1))) {
                    currentBGM.setMediaTime((int) (((long) (this.currentIntroMsec >> 1)) + (currentBGM.getMediaTime() - ((long) (this.currentLoopMsec >> 1)))));
                }
            } else if (currentBGM.getMediaTime() >= ((long) this.currentLoopMsec)) {
                currentBGM.setMediaTime((int) (((long) this.currentIntroMsec) + (currentBGM.getMediaTime() - ((long) this.currentLoopMsec))));
            }
        }
    }

    public static void open() {
        instance = new SoundSystem();
        instance.openImpl();
    }

    public static SoundSystem getInstance() {
        if (instance == null) {
            open();
        }
        return instance;
    }

    private void openImpl() {
        this.nextBgmWaiting = false;
    }

    public void playBgmSequence(int index0, int index1) {
        playBgm(index0, false);
        this.nextBgmWaiting = true;
        this.nextBgmIndex = index1;
        this.nextBgmLoop = true;
    }

    public void playBgmFromTime(long startTime, int index0) {
        this.bgmIndex = index0;
        String fileName = this.Path.concat(this.BgmPrefix).concat(String.valueOf(this.BgmName[index0]) + ".mid");
        System.out.println(fileName);
        MFSound.playBgm(fileName, false);
        MFSound.getCurrentBgm().setMediaTime((int) startTime);
    }

    public void playBgmSequenceNoLoop(int index0, int index1) {
        playBgm(index0, false);
        this.nextBgmWaiting = true;
        this.nextBgmIndex = index1;
        this.nextBgmLoop = false;
    }

    public void playBgm(int index) {
        playBgm(index, true);
    }

    public void playNextBgm(int index) {
        this.nextBgmWaiting = true;
        this.nextBgmIndex = index;
        this.nextBgmLoop = true;
    }

    public int getPlayingBGMIndex() {
        return this.bgmIndex;
    }

    public String getBgmName(int index) {
        return this.BgmName[index];
    }

    public void setSoundSpeed(float speed2) {
        this.preSpeed = this.speed;
        this.speed = speed2;
    }

    public void playBgm(int index, boolean loop) {
        playBgmInSpeed(index, loop, this.speed);
    }

    public void restartBgm() {
        this.mediaTime = MFSound.getBgmMediaTime();
        System.out.println("mediaTime get:" + this.mediaTime);
        this.mediaTime = (long) ((((float) this.mediaTime) * this.preSpeed) / this.speed);
        MFSound.stopBgm();
        playBgm(this.bgmIndex, true);
    }

    public void playBgmInSpeed(int index, boolean loop, float speed2) {
        this.currentIntroMsec = this.INTRO_MSEC[index][0];
        this.currentLoopMsec = this.INTRO_MSEC[index][1];
        if (this.currentLoopMsec > 0) {
            loop = false;
        }
        if (speed2 == 1.0f || getSpeedyVersionBgm(index) < 0) {
            playBgmInNormalSpeed(index, loop);
        } else {
            playBgmInInnormalSpeed(index, loop, speed2);
        }
    }

    private void playBgmInNormalSpeed(int index, boolean loop) {
        this.bgmIndex = index;
        String fileName = this.Path.concat(this.BgmPrefix).concat(String.valueOf(this.BgmName[index]) + ".mid");
        System.out.println(fileName);
        MFSound.playBgm(fileName, loop);
        System.out.println("play bgm");
        if (this.mediaTime > 0) {
            MFSound.getCurrentBgm().setMediaTime((int) this.mediaTime);
            System.out.println("mediaTime:" + this.mediaTime);
            this.mediaTime = 0;
        }
    }

    private void playBgmInInnormalSpeed(int index, boolean loop, float speed2) {
        this.bgmIndex = index;
        String concat = this.Path.concat(this.BgmPrefix).concat(String.valueOf(this.BgmName[index]) + ".mid");
        if (getSpeedyVersionBgm(this.bgmIndex) >= 0) {
            MFPlayer a = MFPlayer.createMFPlayer(this.Path.concat(this.BgmPrefix).concat(String.valueOf(this.BgmNameSpeeding[getSpeedyVersionBgm(this.bgmIndex)]) + ".mid"), false);
            MFSound.setBgm(a, loop);
            if (this.mediaTime > 0) {
                a.setMediaTime((int) this.mediaTime);
                System.out.println("~~mediaTime:" + this.mediaTime);
                this.mediaTime = 0;
            }
        }
    }

    public void stopBgm(boolean isDel) {
        MFSound.stopBgm();
        this.nextBgmWaiting = false;
    }
    
    public void resumeBgm() {
        MFSound.resumeBgm();
    }

    public void playSe(int index, boolean loop) {
        stopLoopSe();
        final int ind = index;
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        MFSound.playSe(SE_PATH + SE_NAME[ind], 1);
        }
        });
        thread.start();
    }

    public void playSe(int index) {
    playSe(index, false);
    }

    public void playLongSe(int index) {
    final int ind = index;
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag()) {
            if (longSeplayer != null) {
                longSeplayer.stop();
                longSeplayer.close();
            }
            longSeplayer = MFPlayer.createMFPlayer(SE_PATH + SE_NAME[ind], true);
            longSeplayer.realize();
            longSeplayer.prefetch();
            longSeplayer.setLoop(false);
            if (volume == 0) {
                longSeplayer.setVolume(0);
            } else {
                longSeplayer.setVolume(100);
            }
            longSeplayer.start();
        }
        }
        });
        thread.start();
    }

    public void stopLongSe() {
        if (this.longSeplayer != null) {
            this.longSeplayer.stop();
            this.longSeplayer.deallocate();
            this.longSeplayer.close();
            this.longSeplayer = null;
        }
    }

    public void playLoopSe(int index) {
    final int ind = index;
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag() && !isLoopSePlaying()) {
        try {
            if (seplayer != null) {
                seplayer.stop();
                seplayer.close();
            }
            seplayer = MFPlayer.createMFPlayer(SE_PATH + SE_NAME[ind], true);
            seIndex = ind;
            seplayer.realize();
            seplayer.prefetch();
            seplayer.setLoop(true);
            if (volume == 0) {
                seplayer.setVolume(0);
            } else {
                seplayer.setVolume(100);
            }
            seplayer.start();
        } catch (Exception e) {
        }
        }
        }
        });
        thread.start();
    }

    public void playSequenceSe(int index) {
    final int ind = index;
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag() && !isLoopSePlaying()) {
            if (seplayer != null) {
                seplayer.stop();
                seplayer.close();
            }
            seplayer = null;
            seplayer = MFPlayer.createMFPlayer(SE_PATH + SE_NAME[ind], true);
            seIndex = ind;
            seplayer.realize();
            if (seplayer != null) {
            seplayer.prefetch();
            seplayer.setLoop(false);
            if (volume == 0) {
                seplayer.setVolume(0);
            } else {
                seplayer.setVolume(100);
            }
            seplayer.start();
            }
        }
        }
        });
        thread.start();
    }

    public void preLoadSequenceSe(int index) {
    final int ind = index;
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag() && !isLoopSePlaying()) {
            if (seplayer != null) {
                seplayer.stop();
                seplayer.close();
            }
            seplayer = MFPlayer.createMFPlayer(SE_PATH + SE_NAME[ind], true);
            seIndex = ind;
            seplayer.realize();
            seplayer.prefetch();
            seplayer.setLoop(false);
            if (volume == 0) {
                seplayer.setVolume(0);
            } else {
                seplayer.setVolume(100);
            }
        }
        }
        });
        thread.start();
    }

    public void playSequenceSeSingle() {
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag() && !isLoopSePlaying()) {
            if (seplayer != null) {
                seplayer.stop();
                seplayer.close();
            }
            seplayer.start();
        }
        }
        });
        thread.start();
    }

    public void stopLoopSe() {
    try {
        if (MFSound.getSeFlag() && isLoopSePlaying()) {
            this.seplayer.stop();
            this.seplayer.deallocate();
            this.seplayer.close();
            this.seplayer = null;
            }
        } catch (Exception e) {
        }
    }

    public void resumeLoopSe() {
    Thread thread = new Thread(new Runnable() {
    @Override
    public void run() {
        if (MFSound.getSeFlag()) {
            if (seplayer != null) {
                seplayer.stop();
                seplayer.close();
            }
            seplayer = MFPlayer.createMFPlayer(SE_PATH + SE_NAME[seIndex], true);
            seplayer.realize();
            seplayer.prefetch();
            seplayer.setLoop(true);
            if (volume == 0) {
                seplayer.setVolume(0);
            } else {
                seplayer.setVolume(100);
            }
            seplayer.start();
        }
        }
        });
        thread.start();
    }

    public int getPlayingLoopSeIndex() {
        return this.seIndex;
    }

    public boolean isLoopSePlaying() {
        if (!MFSound.getSeFlag()) {
            return false;
        }
        if (this.seplayer == null) {
            return false;
        }
        if (this.seplayer.getState() == 3) {
            return true;
        }
        return false;
    }

    public void setVolume(int vol) {
        MFSound.setLevel(vol);
    }

    public boolean getBgmFlag() {
        return MFSound.getBgmFlag();
    }

    public void setBgmFlag(boolean flag) {
        MFSound.setBgmFlag(flag);
    }

    public boolean getSeFlag() {
        return MFSound.getSeFlag();
    }

    public void setSeFlag(boolean flag) {
        MFSound.setSeFlag(flag);
    }

    public boolean bgmPlaying() {
        return MFSound.isBgmPlaying();
    }

    public boolean bgmPlaying2() {
        return MFSound.isBgmPlaying();
    }

    public void setVolumnState(int state) {
        if (state == 0) {
            setSeFlag(false);
        } else {
            setSeFlag(true);
        }
        if (state >= 0 && state <= 15) {
            setBgmFlag(true);
        }
        volume = state;
    }

    public void updateVolumeState() {
        GlobalResource.soundConfig = MFSound.getLevel();
        if (GlobalResource.soundConfig == 0) {
            GlobalResource.seConfig = 0;
        }
        getInstance().setSoundState(GlobalResource.soundConfig);
        getInstance().setSeState(GlobalResource.seConfig);
    }

    public void setSoundState(int state) {
        if (state == 0) {
            setSeFlag(false);
        } else {
            setSeFlag(true);
        }
        if (state >= 0 && state <= 15) {
            setBgmFlag(true);
        }
    }

    public void setSeState(int state) {
        switch (state) {
            case 0:
                setSeFlag(false);
                return;
            case 1:
                setSeFlag(true);
                return;
            default:
                return;
        }
    }

    public void preLoadAllSe() {
        if (PRE_LOAD_SE) {
        MFSound.releaseAllSound();
        for (int i = 0; i < this.SE_NAME.length; i++) {
            MFSound.preloadSound(SE_PATH + this.SE_NAME[i]);
        }
        }
    }
}
