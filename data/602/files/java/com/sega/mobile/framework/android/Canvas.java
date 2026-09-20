package com.sega.mobile.framework.android;

import android.content.Context;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import com.sega.mobile.framework.MFMain;
import com.sega.mobile.framework.device.MFDevice;
import SonicGBA.GameObject;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import State.TitleState;
import State.State;
import State.GameState;
import State.SpecialStageState;
import Lib.SoundSystem;

public class Canvas extends SurfaceView implements SurfaceHolder.Callback {
    public int PID_BUFFER_SIZE = 10;
    private boolean initialFlag = false;
    protected Graphics mGraphics = new Graphics();
    private SurfaceHolder mHolder;
    public int[] pidBuffer = new int[this.PID_BUFFER_SIZE];
    private boolean useMultiTouch = false;
    public static int screenWidth;
    public static int screenHeight;

    public Canvas(Context context) {
        super(context);
        for (int i = 0; i < this.PID_BUFFER_SIZE; i++) {
            this.pidBuffer[i] = -1;
        }
        this.mHolder = getHolder();
        this.mHolder.addCallback(this);
        setFocusable(true);
        setZOrderOnTop(false);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
        screenHeight = context.getResources().getDisplayMetrics().heightPixels;
    }

    public void setFilterBitmap(boolean b) {
        this.mGraphics.setFilterBitmap(b);
    }

    public void setAntiAlias(boolean b) {
        this.mGraphics.setAntiAlias(b);
    }

    public void setUseMultitouch(boolean b) {
        this.useMultiTouch = b;
    }

    public void surfaceCreated(SurfaceHolder holder) {
        MFDevice.notifyStart(getWidth(), getHeight());
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    public boolean keyDown(int keyCode, KeyEvent msg) {
        keyPressed(keyCode);
        return true;
    }

    public boolean keyUp(int keyCode, KeyEvent msg) {
        keyReleased(keyCode);
        return true;
    }

    private void increaseBuffer() {
        this.PID_BUFFER_SIZE += 10;
        int[] tempBuffer = new int[this.PID_BUFFER_SIZE];
        for (int i = 0; i < this.PID_BUFFER_SIZE - 10; i++) {
            tempBuffer[i] = this.pidBuffer[i];
        }
        this.pidBuffer = tempBuffer;
    }

    public boolean touchEvent(MotionEvent event) {
        if (!this.useMultiTouch) {
            switch (event.getAction()) {
                case 0:
                    pointerPressed(0, (int) event.getX(), (int) event.getY());
                    break;
                case 1:
                    pointerReleased(0, (int) event.getX(), (int) event.getY());
                    break;
                case 2:
                    pointerDragged(0, (int) event.getX(), (int) event.getY());
                    break;
            }
        } else {
            int count = event.getPointerCount();
            if (count >= this.PID_BUFFER_SIZE) {
                count = this.PID_BUFFER_SIZE - 1;
            }
            int action = event.getAction();
            int index = action >> 8;
            int id = event.getPointerId(index);
            if (id >= this.PID_BUFFER_SIZE) {
                increaseBuffer();
            }
            switch (action & 255) {
                case 0:
                case 5:
                    this.pidBuffer[id] = id;
                    pointerPressed(this.pidBuffer[id], (int) event.getX(index), (int) event.getY(index));
                    break;
                case 1:
                case 6:
                    if (this.pidBuffer[id] != -1) {
                        pointerReleased(this.pidBuffer[id], (int) event.getX(index), (int) event.getY(index));
                        this.pidBuffer[id] = -1;
                        break;
                    } else {
                        int i = 0;
                        while (true) {
                            if (i >= this.PID_BUFFER_SIZE) {
                                break;
                            } else if (this.pidBuffer[i] != -1) {
                                pointerReleased(this.pidBuffer[i], (int) event.getX(index), (int) event.getY(index));
                                this.pidBuffer[i] = -1;
                                break;
                            } else {
                                i++;
                            }
                        }
                    }
                case 2:
                    for (int i2 = 0; i2 < count; i2++) {
                        int id2 = event.getPointerId(i2);
                        if (this.pidBuffer[id2] != -1) {
                            pointerDragged(this.pidBuffer[id2], (int) event.getX(i2), (int) event.getY(i2));
                        } else {
                            int j = 0;
                            while (true) {
                                if (j < this.PID_BUFFER_SIZE) {
                                    if (this.pidBuffer[j] != -1) {
                                        this.pidBuffer[id2] = this.pidBuffer[j];
                                        this.pidBuffer[j] = -1;
                                        pointerDragged(this.pidBuffer[id2], (int) event.getX(i2), (int) event.getY(i2));
                                    } else {
                                        j++;
                                    }
                                }
                            }
                        }
                    }
                    break;
            }
        }
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
        if (StageManager.loadStep == 0) State.initTouchkeyBoard();
            float x = event.getX();
            float y = event.getY();
            boolean isTopRightCorner = (x > screenWidth * 0.5) && (y < screenHeight * 0.5);
            boolean isTopLeftCorner = (x < screenWidth * 0.2) && (y < screenHeight * 0.3);
            /*boolean isTopMiddle = (x >= screenWidth * 0.4 && x <= screenWidth * 0.6) && (y < screenHeight * 0.5);
            int fx = 10;
            int fy = 10;
            int fw = MFDevice.fw;
            int fh = MFDevice.fh;
            if (x >= fx && x <= fx + fw && y >= fy && y <= fy + fh) {
                MFDevice.showFPS = !MFDevice.showFPS;
                return true;
            }*/
            if (isTopRightCorner && !MFMain.multiplayer && GameObject.player != null && GameObject.player2 != null) {
                if (MFMain.tapCount != 2) {
                    MFMain.tapCount++;
                } else if (MFMain.tapCount == 2) {
                    MFMain.switchPlayerFocus();
                    MFMain.tapCount = 0;
                }
            } /*else if (isTopLeftCorner && GameState.loadingEnd() && GameObject.player != null && GameObject.player2 == null) {
                if (MFMain.tapCount2 != 5) {
                    MFMain.tapCount2++;
                } else if (MFMain.tapCount2 == 5) {
                    MFMain.superPlayer();
                    MFMain.tapCount2 = 0;
                }
            } else if (isTopLeftCorner && GameObject.player == null && TitleState.state == 2) {
                if (!MFMain.cheat && MFMain.tapCount3 != 4) {
                    MFMain.tapCount3++;
                } else if (MFMain.tapCount3 == 4) {
                    MFMain.cheat = true;
                    SoundSystem.getInstance().playSe(84);
                    MFMain.tapCount3 = 0;
                }
            } else if (isTopRightCorner && GameObject.player == null && TitleState.state == 2) {
                if (MFMain.tails < 7 && MFMain.tapCount4 != 4) {
                    MFMain.tapCount4++;
                } else if (MFMain.tapCount3 == 4) {
                    MFMain.tails = 7;
                    //MainState.DemoMode = MainState.DemoMode ? false : true;
                    SoundSystem.getInstance().playSe(84);
                    MFMain.tapCount4 = 0;
                }
            }*/
        }
        return true;
    }

    public boolean onTrackballEvent(MotionEvent event) {
        if (!MFDevice.enableTrackBall) {
            return true;
        }
        switch (event.getAction()) {
            case 0:
                keyPressed(23);
                return true;
            case 1:
                keyReleased(23);
                return true;
            case 2:
                if (event.getX() > 0.0f) {
                    trackballMoved(22);
                    return true;
                } else if (event.getX() < 0.0f) {
                    trackballMoved(21);
                    return true;
                } else if (event.getY() > 0.0f) {
                    trackballMoved(20);
                    return true;
                } else if (event.getY() >= 0.0f) {
                    return true;
                } else {
                    trackballMoved(19);
                    return true;
                }
            default:
                return true;
        }
    }

    public void onWindowFocusChanged(boolean hasWindowFocus) {
        if (!this.initialFlag) {
            this.initialFlag = true;
        }
        if (hasWindowFocus) {
            setFocusable(true);
            for (int i = 0; i < this.PID_BUFFER_SIZE; i++) {
                this.pidBuffer[i] = -1;
            }
            showNotify();
            return;
        }
        hideNotify();
    }

    public boolean initialized() {
        return this.initialFlag;
    }

    public void hideNotify() {
    }

    public void showNotify() {
    }

    public void keyPressed(int keyCode) {
    }

    public void keyReleased(int keyCode) {
    }

    public void trackballMoved(int keyCode) {
    }

    public void pointerPressed(int id, int x, int y) {
    }

    public void pointerReleased(int id, int x, int y) {
    }

    public void pointerDragged(int id, int x, int y) {
    }

    public void paint(Graphics g) {
    }

    public void repaint() {
        try {
            this.mGraphics.setCanvas(this.mHolder.lockCanvas());
            synchronized (this.mHolder) {
                if (this.mGraphics.getCanvas() != null) {
                    paint(this.mGraphics);
                }
            }
            if (this.mGraphics.getCanvas() != null) {
                this.mHolder.unlockCanvasAndPost(this.mGraphics.getCanvas());
            }
        } catch (Throwable th) {
            try {
            if (this.mGraphics.getCanvas() != null) {
                this.mHolder.unlockCanvasAndPost(this.mGraphics.getCanvas());
            }
            } catch (Exception e) {
            e.printStackTrace();
            }
        }
    }

    public void serviceRepaints() {
    }

    public void setFullScreenMode(boolean b) {
        if (b) {
            MFMain.getInstance().requestWindowFeature(1);
        }
    }
}
