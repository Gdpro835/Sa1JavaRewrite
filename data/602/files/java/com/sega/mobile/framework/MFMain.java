package com.sega.mobile.framework;

import SonicGBA.MapManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.sega.mobile.framework.android.Canvas;
import com.sega.mobile.framework.device.MFDevice;
import com.sega.mobile.framework.device.MFSound;
import com.sega.mobile.framework.device.MFGamePad;
import com.sega.mobile.framework.device.MFGraphics;
import java.util.Locale;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient.FileChooserParams;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.webkit.WebView;
import android.webkit.WebSettings;
import android.webkit.WebViewClient;
import android.widget.Toast;
import android.os.Environment;
import android.content.ActivityNotFoundException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.ContentValues;
import android.content.ContentResolver;
import android.provider.MediaStore;
import android.net.Uri;
import android.os.Environment;
import android.app.NotificationManager;
import android.content.Context;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.IOException;
import android.content.Context;
import SonicGBA.GameObject;
import SonicGBA.PlayerObject;
import SonicGBA.StageManager;
import State.TitleState;
import State.State;
import State.GameState;
import State.SpecialStageState;
import android.content.pm.ActivityInfo;
import android.view.InputDevice;
import Lib.SoundSystem;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.util.List;
import java.util.Enumeration;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.ServerSocket;
import java.io.IOException;
import java.net.Socket;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import android.widget.ArrayAdapter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import android.widget.EditText;
import android.widget.FrameLayout;
import com.sega.MFLib.Main;
import java.net.SocketException;
import java.io.EOFException;
import MFLib.MainState;
import Lib.CopyFields;
import android.os.SystemClock;

public abstract class MFMain extends Activity {
    private static MFMain instance;
    public static Canvas mCanvas;
    private static boolean mCreated = false;
    private Handler mHandler = new Handler();
    public static boolean browser = false;
    public static int tails = 0;
    //public static boolean fatest = false;
    public static boolean notification = false;
    private static final String CHANNEL_ID = "download_channel";
    public abstract MFGameState getEntryGameState();
    public ValueCallback<Uri> mUploadMessage;         // Android < 5.0
    public ValueCallback<Uri[]> mFilePathCallback;    // Android >= 5.0
    public static boolean SUPERINNORMALSTAGE = false;
    private static final long TAP_TIMEOUT = 600;
    public static int tapCount = 0;
    public static int tapCount2 = 0;
    public static int tapCount3 = 0;
    public static int tapCount4 = 0;
    private long lastTapTime = 0;
    public static boolean multiplayer = false;
    public FrameLayout layout;
    public static PlayerObject playermulti;
    public static boolean cheat;

    public static void switchPlayerFocus() {
        if (GameObject.player != null && GameObject.player2 != null) {
        if (MapManager.focusObj == GameObject.player) {
            PlayerObject copy = GameObject.player2;
            GameObject.player2 = GameObject.player;
            GameObject.player = copy;
            GameObject.player.setPlayer(GameObject.player);
            GameObject.player2.setPlayer(GameObject.player2);
            GameObject.player.Player2Hurt = false;
            GameObject.player2.Player2Hurt = true;
            int chrid = PlayerObject.getCharacterID();
            PlayerObject.characterID = PlayerObject.getCharacterID2();
            PlayerObject.characterID2 = chrid;
            MapManager.setFocusObj(GameObject.player);
            GameObject.player.headInit(PlayerObject.getCharacterID());
            }
        }
    }
    public static void superPlayer() {
        boolean z = true;
        if (z && MapManager.focusObj == GameObject.player) {
            if (PlayerObject.getCharacterID() == 0) {
                //GameObject.player.characterID = 4;
                GameObject.player = GameObject.player.getPlayer(4);
                GameObject.player.setPlayer(GameObject.player);
                GameObject.player.invincibleCount = -1;
                GameObject.player.isAntiGravity = true;
                GameObject.player.getRing(999);
                MapManager.setFocusObj(GameObject.player);
                GameObject.player.headInit();
                SUPERINNORMALSTAGE = true;
            }
        }
    }
    
    /*public void fastPlayer() {
    if (GameObject.player != null) {
        if (MapManager.getFocusObj() == GameObject.player) {
            fatest = true;
            GameObject.player.velX *= 2;
            }
        }
    }*/
    
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mFilePathCallback == null) return;
                Uri[] results = null;
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        String dataString = data.getDataString();
                        if (dataString != null) {
                            results = new Uri[]{ Uri.parse(dataString) };
                        }
                    }
                }
                mFilePathCallback.onReceiveValue(results);
                mFilePathCallback = null;
            } else {
                if (mUploadMessage == null) return;
                Uri result = null;
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        result = data.getData();
                    }
                }
                mUploadMessage.onReceiveValue(result);
                mUploadMessage = null;
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }

        if (resultCode != RESULT_OK || data == null || data.getData() == null) {
            Toast.makeText(this, "Operation canceled or file retrieval failed", Toast.LENGTH_SHORT).show();
            return;
        }
    }

    public static final MFMain getInstance() {
        return instance;
    }

    public String getAppProperty(String key) {
        return "Android";
    }

    public Handler getHandler() {
        return this.mHandler;
    }

    public void drawDeviceRotated(MFGraphics g, int width, int height) {
    }

    public boolean logicDeviceSuspend() {
        if (MFGamePad.isKeyPress(2113)) {
            return true;
        }
        return false;
    }

    public void drawDeviceSuspend(MFGraphics g) {
        g.enableExceedBoundary();
        g.clearScreen(0);
        g.setFont(-1);
        g.setColor(MapManager.END_COLOR);
        g.drawString("Press the confirm key to continue", MFDevice.getScreenWidth() >> 1, MFDevice.getScreenHeight() >> 1, 3);
        g.drawString("confirm", 0, MFDevice.getScreenHeight(), 36);
        g.disableExceedBoundary();
    }

    public void dialogExit() {
    }

    public void notifyDestroyed() {
        finish();
        MFDevice.openUrl();
        System.exit(0);
    }

    public void platformRequest(final String url) {
        //startActivity(new Intent("android.intent.action.VIEW", Uri.parse(url)));
        if (!notification) createNotificationChannel();
    notification = true;
    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            final WebView webView = new WebView(MFMain.this);

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setDomStorageEnabled(true);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return false;
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                }

                @Override
                public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                    super.onReceivedError(view, errorCode, description, failingUrl);
                }
            });
            
            webView.setWebChromeClient(new WebChromeClient() {

    // Android 5.0+
    @Override
    public boolean onShowFileChooser(WebView webView,
                                     ValueCallback<Uri[]> filePathCallback,
                                     FileChooserParams fileChooserParams) {
        if (mFilePathCallback != null) {
            mFilePathCallback.onReceiveValue(null);
        }
        mFilePathCallback = filePathCallback;
        Intent intent = fileChooserParams.createIntent();
        try {
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            mFilePathCallback = null;
            Toast.makeText(MFMain.this, "Unable to open file picker", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Android < 3.0
    public void openFileChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooser(uploadMsg, "*/*");
    }

    // Android 3.0+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooser(uploadMsg, acceptType, null);
    }

    // Android 4.1+
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
        mUploadMessage = uploadMsg;
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("*/*");
        try {
            startActivityForResult(Intent.createChooser(intent, "Select a file"), 1);
        } catch (ActivityNotFoundException e) {
            mUploadMessage = null;
            Toast.makeText(MFMain.this, "Unable to open file picker", Toast.LENGTH_SHORT).show();
        }
    }
});

            if (url != null || !url.isEmpty()) {
                webView.loadUrl(url);
            }

webView.setDownloadListener(new DownloadListener() {
    @Override
    public void onDownloadStart(final String url, final String userAgent, String contentDisposition, String mimeType, long contentLength) {
            final int notifId = 1001;

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showNotification("Download started", "Downloading file: " + url, notifId);
                }
            });
            new Thread(new Runnable() {
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    InputStream is = null;
                    ByteArrayOutputStream baos = null;
                    try {
                        URL downloadUrl = new URL(url);
                        connection = (HttpURLConnection) downloadUrl.openConnection();
                        connection.setRequestProperty("User-Agent", userAgent);
                        connection.setInstanceFollowRedirects(true);
                        connection.connect();
                        final int s = connection.getResponseCode();
                        final String s2 = connection.getHeaderField("Location");
                        runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MFMain.this, "Response code: " + s, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(this, "Location: " + s2, Toast.LENGTH_SHORT).show();
                }
            });
                        if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                            is = connection.getInputStream();
                            baos = new ByteArrayOutputStream();

                            byte[] buffer = new byte[8192];
                            int bytesRead;
                            while ((bytesRead = is.read(buffer)) != -1) {
                                baos.write(buffer, 0, bytesRead);
                            }

                            byte[] data = baos.toByteArray();
                            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    showNotification("Download finished", "File downloaded successfully: " + url, notifId);
                    Toast.makeText(MFMain.this, "Download finished", Toast.LENGTH_SHORT).show();
                }
            });
                        String path = downloadUrl.getPath();
                        String fileName = path.substring(path.lastIndexOf('/') + 1);
                        if (Build.VERSION.SDK_INT > 28) {                        
                        ContentValues values = new ContentValues();
                        values.put(MediaStore.Downloads.DISPLAY_NAME, fileName);
                        values.put(MediaStore.Downloads.MIME_TYPE, "application/octet-stream");
                        values.put(MediaStore.Downloads.IS_PENDING, 1);
                        ContentResolver resolver = getContentResolver();
                        Uri collection = MediaStore.Downloads.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
                        Uri fileUri = resolver.insert(collection, values);
                        try (OutputStream out = resolver.openOutputStream(fileUri)) {
                             out.write(data);
                        }
                        values.clear();
                        values.put(MediaStore.Downloads.IS_PENDING, 0);
                        resolver.update(fileUri, values, null, null);
                        } else {
                        File downloadsDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                        File outputFile = new File(downloadsDir, fileName);
                        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                        fos.write(data);
                        fos.flush();
                        } catch (IOException e) {
                          e.printStackTrace();
                        }
                        }
                        } else {
                        runOnUiThread(new Runnable() {
                        @Override
                public void run() {
                    showNotification("Download failed", "File not downloaded successfully: " + url, notifId);
                }
            });
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        runOnUiThread(new Runnable() {
                        @Override
                public void run() {
                    showNotification("Download failed", "File not downloaded successfully: " + url, notifId);
                }
            });
                    } finally {
                        if (baos != null) {
                            try { baos.close(); } catch (IOException ignored) {}
                        }
                        if (is != null) {
                            try { is.close(); } catch (IOException ignored) {}
                        }
                        if (connection != null) {
                            connection.disconnect();
                        }
                    }
                }
            }).start();
    }
});
setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); 
getInstance().setContentView(webView);
}
});
    }
    
    private void createNotificationChannel() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        NotificationChannel channel = new NotificationChannel(
            CHANNEL_ID,
            "Download Notifications",
            NotificationManager.IMPORTANCE_LOW);
        channel.setDescription("Notifications for file downloads");

        NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }
}

private void showNotification(String title, String text, int notificationId) {
    NotificationManager manager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

    Notification.Builder builder;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        builder = new Notification.Builder(this, CHANNEL_ID);
    } else {
        builder = new Notification.Builder(this);
        builder.setPriority(Notification.PRIORITY_LOW);
    }

    builder.setContentTitle(title)
           .setContentText(text)
           .setSmallIcon(title.contains("finished") ? this.getResources().getIdentifier("icon", "mipmap", this.getPackageName()) : android.R.drawable.stat_sys_download)
           .setAutoCancel(true);

    manager.notify(notificationId, builder.build());
}

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(1);      
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= 28) {
            getWindow().setFlags(512, 512);
            getWindow().getAttributes().layoutInDisplayCutoutMode = 1;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            getWindow().setFlags(512, 512);
            getWindow().getDecorView().setSystemUiVisibility(4098);
        }
        if (!mCreated) {
            mCreated = true;
            getWindow().setFlags(1024, 1024);
            setVolumeControlStream(3);
            if (instance == null) {
                instance = this;
                mCanvas = (Canvas) MFDevice.getSystemDisplayable();
                //getInstance().setContentView(mCanvas);
                layout = new FrameLayout(this);
                layout.addView(mCanvas);
                setContentView(layout);
            }
        }
    }
    
    boolean isGamepadKey(int keyCode) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BUTTON_A:
            case KeyEvent.KEYCODE_BUTTON_B:
            case KeyEvent.KEYCODE_BUTTON_X:
            case KeyEvent.KEYCODE_BUTTON_Y:
            case KeyEvent.KEYCODE_BUTTON_L1:
            case KeyEvent.KEYCODE_BUTTON_R1:
            case KeyEvent.KEYCODE_BUTTON_L2:
            case KeyEvent.KEYCODE_BUTTON_R2:
            case KeyEvent.KEYCODE_BUTTON_SELECT:
            case KeyEvent.KEYCODE_BUTTON_START:
            case KeyEvent.KEYCODE_BUTTON_MODE:
            case KeyEvent.KEYCODE_BUTTON_THUMBL:
            case KeyEvent.KEYCODE_BUTTON_THUMBR:
            case KeyEvent.KEYCODE_DPAD_UP:
            case KeyEvent.KEYCODE_DPAD_DOWN:
            case KeyEvent.KEYCODE_DPAD_LEFT:
            case KeyEvent.KEYCODE_DPAD_RIGHT:
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ALT_LEFT:
            case KeyEvent.KEYCODE_SPACE:
            case KeyEvent.KEYCODE_ENTER:
            case KeyEvent.KEYCODE_DEL:
                return true;
            default:
                return false;
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        MFGamePad.pressVisualKey(decodeGamepadKey(keyCode));
        int source = event.getSource();
        if (StageManager.loadStep == 0 && (isGamepadKey(keyCode) || (source & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK || (source & InputDevice.SOURCE_GAMEPAD) == InputDevice.SOURCE_GAMEPAD)) {
            State.releaseTouchkeyBoard();
            return true;
        } else if (StageManager.loadStep == 0) {
            State.initTouchkeyBoard();
        }
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (browser) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                browser = false;
                TitleState.state = 1;
                getInstance().setContentView(layout);
            }
        }
        if (keyCode == 24) {
            MFDevice.notifyKeyPressed(keyCode);
            if (MFDevice.getEnableVolumeKey()) {
                return false;
            }
            return true;
        } else if (keyCode == 25) {
            MFDevice.notifyKeyPressed(keyCode);
            if (MFDevice.getEnableVolumeKey()) {
                return false;
            }
            return true;
        } else if (keyCode == 4) {
            if (!MFDevice.getEnableCustomBack()) {
                showExitConfirm();
            } else if (event.getAction() == 0) {
                mCanvas.keyPressed(keyCode);
            }
            return true;
        } else {
            mCanvas.keyDown(keyCode, event);
            if (keyCode != 84 && keyCode != 82) {
                return false;
            }
            MFDevice.notifyKeyPressed(keyCode);
            return true;
        }
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        mCanvas.keyUp(keyCode, event);
        MFGamePad.releaseVisualKey(decodeGamepadKey(keyCode));
        if (keyCode == 84 || keyCode == 82) {
            return true;
        }
        return false;
    }

    public boolean onTouchEvent(MotionEvent event) {
        mCanvas.touchEvent(event);
        return true;
    }

    public void showExitConfirm() {
        if (Locale.getDefault().equals(Locale.CHINA)) {
            setExitConfirmStr("退出游戏提示", "是否退出游戏？", "确定", "取消");
        } else if (Locale.getDefault().equals(Locale.JAPAN)) {
            setExitConfirmStr("ソニックアドバンス", "ゲームを終了しますか？", "はい", "キャンセル");
        } else {
            setExitConfirmStr("Exit", "Exit Game?", "Yes", "No");
        }
    }

    public void setExitConfirmStr(String title, String message, String positive, String negative) {
        new AlertDialog.Builder(getInstance()).setTitle(title).setMessage(message).setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == 84 || keyCode == 82) {
                    return true;
                }
                return false;
            }
        }).setPositiveButton(positive, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                MFMain.this.dialogExit();
                MFMain.this.notifyDestroyed();
            }
        }).setNegativeButton(negative, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            }
        }).show();
    }
    
    private int decodeGamepadKey(int keyCode) {
    switch (keyCode) {

        case KeyEvent.KEYCODE_DPAD_UP:
            return MFGamePad.KEY_PAD_UP;
        case KeyEvent.KEYCODE_DPAD_DOWN:
            return MFGamePad.KEY_PAD_DOWN;
        case KeyEvent.KEYCODE_DPAD_LEFT:
            return MFGamePad.KEY_PAD_LEFT;
        case KeyEvent.KEYCODE_DPAD_RIGHT:
            return MFGamePad.KEY_PAD_RIGHT;

        case KeyEvent.KEYCODE_BUTTON_A:
            return MFGamePad.KEY_JOYSTICK_X;
        case KeyEvent.KEYCODE_BUTTON_B:
            return MFGamePad.KEY_JOYSTICK_O;
        case KeyEvent.KEYCODE_BUTTON_X:
            return MFGamePad.KEY_JOYSTICK_O;
        case KeyEvent.KEYCODE_BUTTON_Y:
            return MFGamePad.KEY_JOYSTICK_TRIANGLE;

        case KeyEvent.KEYCODE_BUTTON_L1:
            return MFGamePad.KEY_JOYSTICK_L1;
        case KeyEvent.KEYCODE_BUTTON_R1:
            return MFGamePad.KEY_JOYSTICK_R1;

        case KeyEvent.KEYCODE_BUTTON_START:
            return MFGamePad.KEY_JOYSTICK_START;
        case KeyEvent.KEYCODE_BUTTON_SELECT:
            return MFGamePad.KEY_BACK;

        case KeyEvent.KEYCODE_MENU:
            return MFGamePad.KEY_MENU; 

        case KeyEvent.KEYCODE_VOLUME_UP:
            return MFGamePad.KEY_VOLUME_UP;
        case KeyEvent.KEYCODE_VOLUME_DOWN:
            return MFGamePad.KEY_VOLUME_DOWN; 
        case KeyEvent.KEYCODE_SEARCH:
            return MFGamePad.KEY_SEARCH; 

        case KeyEvent.KEYCODE_ALT_LEFT:
            return MFGamePad.KEY_JOYSTICK_O;
        case KeyEvent.KEYCODE_SPACE:
            return MFGamePad.KEY_JOYSTICK_X;
        case KeyEvent.KEYCODE_ENTER:
            return MFGamePad.KEY_JOYSTICK_START;
        case KeyEvent.KEYCODE_DEL:
            return MFGamePad.KEY_BACK;

        default:
            return 0;
    }
}

// @Override
// public boolean onGenericMotionEvent(MotionEvent event) {
//     if ((event.getSource() & InputDevice.SOURCE_JOYSTICK) == InputDevice.SOURCE_JOYSTICK
//         && event.getAction() == MotionEvent.ACTION_MOVE) {

//         float x = getCenteredAxis(event, MotionEvent.AXIS_X);
//         float y = getCenteredAxis(event, MotionEvent.AXIS_Y);

//         handleJoystickInput(x, y);
//         return true;
//     }
//     return super.onGenericMotionEvent(event);
// }

private float getCenteredAxis(MotionEvent event, int axis) {
    InputDevice device = event.getDevice();
    if (device == null) {
        return 0;
    }
    InputDevice.MotionRange range = device.getMotionRange(axis, event.getSource());
    if (range != null) {
        float value = event.getAxisValue(axis);
        float flat = range.getFlat();
        if (Math.abs(value) > flat) {
            return value;
        }
    }
    return 0;
}

// private void handleJoystickInput(float x, float y) {
//     int keys = 0;
//     float deadzone = 0.15f;

//     // Horizontal
//     if (x < -deadzone) keys |= MFGamePad.KEY_PAD_LEFT;
//     else if (x > deadzone) keys |= MFGamePad.KEY_PAD_RIGHT;

//     // Vertical (invert Y if your game treats negative as down)
//     if (y < -deadzone) keys |= MFGamePad.KEY_PAD_UP;
//     else if (y > deadzone) keys |= MFGamePad.KEY_PAD_DOWN;

//     // Only update keys if something changed
//     if (keys != 0) {
//         if (GameObject.player != null) State.releaseTouchkeyBoard();
//         MFGamePad.pressVisualKey(keys);
//     } else if (GameObject.player != null) {
//         State.initTouchkeyBoard();
//     }
// }

    public void onDestroy() {
        //System.exit(0);
        super.onDestroy();
    }
    public void onPause() {
        super.onPause();
        MFDevice.isPaused = true;
        mCanvas.hideNotify();
    }
    public void onStop() {
        super.onStop();
        //MFDevice.Pause();
    }
    public void onResume() {
        super.onResume();
        mCanvas.setFocusable(true);
        mCanvas.showNotify();
        MFDevice.isPaused = false;
    }
    
    public void initMultiplayer() {
    /*Key.touchMainMenuReset2();
    Key.touchkeyboardClose();
    Key.touchsoftkeyInit();*/
    SoundSystem.getInstance().playSe(84);
    //mCanvas.setFocusable(false);
    //mCanvas.setFocusableInTouchMode(false);
    multiplayer = true;
            getLocalIpAddress(new OnIPSelectedListener() {
        @Override
        public void onIPSelected(InetAddress selectedIP) {
            if (selectedIP != null) {
            //Toast.makeText(MFMain.this, "selectedIP isn't empty", Toast.LENGTH_SHORT).show();
            InetAddress localAddress = selectedIP;
            searchNetwork("Your device IP: " + localAddress.getHostAddress());
            } else {
            Toast.makeText(MFMain.this, "selectedIP is empty", Toast.LENGTH_SHORT).show();
            multiplayer = false;
            }
        }
    });
    }
    
    public void searchNetwork(String ip) {
    AlertDialog.Builder builder = new AlertDialog.Builder(MFMain.this);
        builder.setTitle("Choose Mode. \n" + ip)
               .setMessage("Do you want to be the receiver or the client?")
               .setPositiveButton("Server", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       iniciarReceptor();
                   }
               })
               .setNegativeButton("Client", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       iniciarCliente();
                   }
               });
AlertDialog dialog = builder.create();
dialog.setCanceledOnTouchOutside(true);
dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
    @Override
    public void onCancel(DialogInterface dialog) {
    mCanvas.setFocusable(true);
    mCanvas.setFocusableInTouchMode(true);
    multiplayer = false;
    }
});
dialog.show();
    }
    
    public interface OnIPSelectedListener {
        void onIPSelected(InetAddress selectedIP);
    }

    public boolean isPrivateIP(InetAddress inetAddress) {
    String ip = inetAddress.getHostAddress();
    return ip.startsWith("10.") || ip.startsWith("172.") || ip.startsWith("192.168");
}

    // Método para obter os IPs e exibir o AlertDialog
    public void getLocalIpAddress(final OnIPSelectedListener listener) {
        final List<InetAddress> validIPs = new ArrayList<>();

        try {
            // Varrendo todas as interfaces de rede
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                    for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                        InetAddress inetAddress = enumIpAddr.nextElement();
                            if (isPrivateIP(inetAddress)) {
    validIPs.add(inetAddress);
}
                    }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
            Toast.makeText(MFMain.this, "getLocalIpAddress error", Toast.LENGTH_SHORT).show();
        }

        // Se não houver IPs válidos, invoca o callback com null
        if (validIPs.isEmpty()) {
            listener.onIPSelected(null);
            return;
        }

        // Converte lista de InetAddress para uma lista de String para exibição
        List<String> ipStrings = new ArrayList<>();
        for (InetAddress addr : validIPs) {
            ipStrings.add(addr.getHostAddress());
        }

        // Adapter para o AlertDialog
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MFMain.this, android.R.layout.simple_list_item_1, ipStrings);

        // Cria o AlertDialog com a lista de IPs e exibe
        new AlertDialog.Builder(MFMain.this)
            .setTitle("Select IP")
            .setAdapter(adapter, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    // Retorna o IP selecionado ao callback
                    listener.onIPSelected(validIPs.get(which));
                }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) { 
        listener.onIPSelected(null);
        mCanvas.setFocusable(true);
        mCanvas.setFocusableInTouchMode(true);
        multiplayer = false;
        }
        })
            .show();
    }

    private void iniciarReceptor() {
    ExecutorService executor = Executors.newSingleThreadExecutor();
    executor.execute(new Runnable() {
        @Override
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket(12345);
                Socket socket = serverSocket.accept();
                        GameTask gameTask = new GameTask(MFMain.this, socket, true);
                        State.setState(9);
                        mCanvas.setFocusable(true);
                        mCanvas.setFocusableInTouchMode(true);
                serverSocket.close();
            } catch (IOException e) {
                mCanvas.setFocusable(true);
                mCanvas.setFocusableInTouchMode(true);
                multiplayer = false;
                e.printStackTrace();
            }
        }
    });
}

    private void iniciarCliente() {
            // Cria o AlertDialog com um EditText para o IP
            AlertDialog.Builder builder = new AlertDialog.Builder(MFMain.this);
            builder.setTitle("Enter the Receiver IP");

            // Cria o EditText para o IP
            final EditText input = new EditText(MFMain.this);
            input.setHint("Ex: 192.168.1.100");
            builder.setView(input);
            
            builder.setCancelable(false);

            // Define os botões do AlertDialog
            builder.setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    final String ipReceptor = input.getText().toString().trim();
                    if (ipReceptor.isEmpty()) {
                        Toast.makeText(MFMain.this, "IP cannot be empty", Toast.LENGTH_SHORT).show();
                        multiplayer = false;
                        return;
                    }

                    // Conecta ao receptor em uma thread separada
                    ExecutorService executor = Executors.newSingleThreadExecutor();
                    executor.execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Socket socket = new Socket(ipReceptor, 12345); // Conecta ao receptor
                                        GameTask gameTask = new GameTask(MFMain.this, socket, false); // Cliente
                                        State.setState(9);
                                        mCanvas.setFocusable(true);
                                        mCanvas.setFocusableInTouchMode(true);
                            } catch (IOException e) {
                                e.printStackTrace();
                                        Toast.makeText(MFMain.this, "Error connecting", Toast.LENGTH_SHORT).show();
                                        mCanvas.setFocusable(true);
                                        mCanvas.setFocusableInTouchMode(true);
                                        multiplayer = false;
                            }
                        }
                    });
                }
            });

            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int which) {
             mCanvas.setFocusable(true);
             mCanvas.setFocusableInTouchMode(true);
             multiplayer = false;
    }
});
            builder.show();
}
    
    public static PlayerObject getPlayerMulti() {
    return playermulti;
    }
    
    public class GameTask {

    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private boolean isReceptor;
    private volatile boolean running = true;

    public GameTask(Context context, Socket socket, boolean isReceptor) {
        this.socket = socket;
        this.isReceptor = isReceptor;

        try {
            if (isReceptor) {
                input = new ObjectInputStream(socket.getInputStream());
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
            } else {
                output = new ObjectOutputStream(socket.getOutputStream());
                output.flush();
                input = new ObjectInputStream(socket.getInputStream());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        final boolean z = isReceptor;
        new Thread(new Runnable() {
     @Override
     public void run() {
      while (!z && running) {
try {
    output.writeObject(StageManager.getStageID());
    output.flush();

    Object obj = input.readObject();
    if (obj instanceof Integer && (Integer) obj == StageManager.getStageID()) {

    output.writeObject(GameObject.player);
    output.flush();

    Object obj2 = input.readObject();
    if (obj2 instanceof PlayerObject) {
        playermulti = (PlayerObject) obj2;
    }
    }
} catch (Exception e) {
    e.printStackTrace();
    stopTask();
}
                }
      while (z && running) {
try {
    Object obj = input.readObject();
    if (obj instanceof Integer && (Integer) obj == StageManager.getStageID()) {

        output.writeObject(StageManager.getStageID());
        output.flush();

        Object obj2 = input.readObject();
        if (obj2 instanceof PlayerObject) {
            playermulti = (PlayerObject) obj2;
        }

        output.writeObject(GameObject.player);
        output.flush();
    }
} catch (Exception e) {
    e.printStackTrace();
    stopTask();
}
                }
      }
      }).start();
    }

    public void stopTask() {
        running = false;
        TitleState.state = 1;
        MFMain.multiplayer = false;
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
}
