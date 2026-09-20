package com.sega.mobile.framework.device;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import com.sega.mobile.framework.MFMain;
import java.util.List;

public class MFSensor {
    private static SensorEventListener listener = new SensorEventListener() {
        public void onSensorChanged(SensorEvent event) {
            if (event.sensor.getType() == 1) {
                MFSensor.x = event.values[0];
                MFSensor.y = event.values[1];
                MFSensor.z = event.values[2];
            }
        }

        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    private static SensorManager mManager;
    /* access modifiers changed from: private */
    public static float x;
    /* access modifiers changed from: private */
    public static float y;
    /* access modifiers changed from: private */
    public static float z;

    public static float getAccX() {
        return x;
    }

    public static float getAccY() {
        return y;
    }

    public static float getAccZ() {
        return z;
    }

    public static void init() {
        mManager = (SensorManager) MFMain.getInstance().getSystemService("sensor");
        List<Sensor> sensors = mManager.getSensorList(1);
        if (sensors.size() > 0) {
            mManager.registerListener(listener, sensors.get(0), 0);
        }
    }
}
