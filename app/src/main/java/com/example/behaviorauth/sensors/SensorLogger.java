package com.example.behaviorauth.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;


public class SensorLogger implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor accel;
    private Sensor gyro;
    private boolean isLogging = false;

    public SensorLogger(Context ctx) {
        sensorManager = (SensorManager) ctx.getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        gyro = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
    }

    public void startLogging() {
        if (!isLogging) {
            if (accel != null) sensorManager.registerListener(this, accel, SensorManager.SENSOR_DELAY_GAME);
            if (gyro != null) sensorManager.registerListener(this, gyro, SensorManager.SENSOR_DELAY_GAME);
            isLogging = true;
        }
    }

    public void stopLogging() {
        if (isLogging) {
            sensorManager.unregisterListener(this);
            isLogging = false;
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        long ts = System.currentTimeMillis();
        String raw = event.sensor.getName() + "," + event.values[0] + "," + event.values[1] + "," + event.values[2];
        String hashed = HashUtil.hash(raw);
        CSVWriter.append("sensor", hashed, ts);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {}
}
