package com.example.behaviorauth.services;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.behaviorauth.sensors.SensorLogger;
import com.example.behaviorauth.sensors.TouchLogger;
import com.example.behaviorauth.sensors.AppUsageLogger;
import com.example.behaviorauth.utils.CSVWriter;

public class BehaviorLoggerService extends Service {

    private SensorLogger sensorLogger;
    private TouchLogger touchLogger;
    private AppUsageLogger appUsageLogger;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BehaviorLogger", "Service started...");

        // init CSV file
        CSVWriter.init("behavior_logs.csv", this);

        // start loggers
        sensorLogger = new SensorLogger(this);
        touchLogger = new TouchLogger(this);
        appUsageLogger = new AppUsageLogger(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BehaviorLogger", "Service stopped...");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
