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
    private BiometricLogger biometricLogger;
    private LoginLogger loginLogger;
    private BrowserLogger browserLogger;

    private android.os.Handler handler;

    // Intervals and durations in ms
    private static final long BIOMETRIC_INTERVAL = 10 * 60 * 1000; // 10 min
    private static final long BIOMETRIC_WINDOW = 180 * 60 * 1000; // 180 min
    private static final long TOUCH_INTERVAL = 30 * 60 * 1000; // 30 min
    private static final long TOUCH_DURATION = 5 * 60 * 1000; // 5 min
    private static final long LOGIN_INTERVAL = 60 * 60 * 1000; // 60 min
    private static final long LOGIN_DURATION = 3 * 60 * 1000; // 3 min
    private static final long KEYSTROKE_INTERVAL = 30 * 60 * 1000; // 30 min
    private static final long KEYSTROKE_DURATION = 5 * 60 * 1000; // 5 min
    private static final long APP_BROWSER_INTERVAL = 180 * 60 * 1000; // 180 min
    private static final long APP_BROWSER_DURATION = 15 * 60 * 1000; // 15 min

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("BehaviorLogger", "Service started...");

        CSVWriter.init("behavior_logs.csv", this);

        sensorLogger = new SensorLogger(this);
        touchLogger = new TouchLogger(this);
        appUsageLogger = new AppUsageLogger(this);
        biometricLogger = new BiometricLogger(this);
        loginLogger = new LoginLogger(this);
        browserLogger = new BrowserLogger(this);

        handler = new android.os.Handler();

        // Biometric logging every 10 min in 180 min window
        Runnable biometricCycle = new Runnable() {
            long elapsed = 0;
            @Override
            public void run() {
                if (elapsed < BIOMETRIC_WINDOW) {
                    biometricLogger.startLogging();
                    // Simulate event (replace with real biometric event)
                    biometricLogger.logEvent("face_or_fingerprint");
                    handler.postDelayed(() -> biometricLogger.stopLogging(), 1000); // stop after 1s
                    elapsed += BIOMETRIC_INTERVAL;
                    handler.postDelayed(this, BIOMETRIC_INTERVAL);
                }
            }
        };
        handler.post(biometricCycle);

        // Touch pattern logging 5 min every 30 min
        Runnable touchCycle = new Runnable() {
            @Override
            public void run() {
                touchLogger.startLogging();
                handler.postDelayed(() -> touchLogger.stopLogging(), TOUCH_DURATION);
                handler.postDelayed(this, TOUCH_INTERVAL);
            }
        };
        handler.post(touchCycle);

        // Login behavior logging 3 min every 60 min
        Runnable loginCycle = new Runnable() {
            @Override
            public void run() {
                loginLogger.startLogging();
                // Simulate event (replace with real login event)
                loginLogger.logEvent("login");
                handler.postDelayed(() -> loginLogger.stopLogging(), LOGIN_DURATION);
                handler.postDelayed(this, LOGIN_INTERVAL);
            }
        };
        handler.post(loginCycle);

        // Keystroke logging 5 min every 30 min
        Runnable keystrokeCycle = new Runnable() {
            @Override
            public void run() {
                KeystrokeIME.startLogging();
                handler.postDelayed(() -> KeystrokeIME.stopLogging(), KEYSTROKE_DURATION);
                handler.postDelayed(this, KEYSTROKE_INTERVAL);
            }
        };
        handler.post(keystrokeCycle);

        // App and browser usage logging 15 min every 180 min
        Runnable appBrowserCycle = new Runnable() {
            @Override
            public void run() {
                appUsageLogger.logUsage();
                browserLogger.startLogging();
                // Simulate event (replace with real browser event)
                browserLogger.logEvent("browser_usage");
                handler.postDelayed(() -> browserLogger.stopLogging(), APP_BROWSER_DURATION);
                handler.postDelayed(this, APP_BROWSER_INTERVAL);
            }
        };
        handler.post(appBrowserCycle);

        // Sensor logging (unchanged, always on)
        sensorLogger.startLogging();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("BehaviorLogger", "Service stopped...");
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }
        if (sensorLogger != null) sensorLogger.stopLogging();
        if (touchLogger != null) touchLogger.stopLogging();
        if (loginLogger != null) loginLogger.stopLogging();
        if (biometricLogger != null) biometricLogger.stopLogging();
        if (browserLogger != null) browserLogger.stopLogging();
        KeystrokeIME.stopLogging();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
