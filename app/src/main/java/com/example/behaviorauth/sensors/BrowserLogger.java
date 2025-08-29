package com.example.behaviorauth.sensors;

import android.content.Context;
import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;

public class BrowserLogger {
    private Context ctx;
    private boolean isLogging = false;

    public BrowserLogger(Context ctx) {
        this.ctx = ctx;
    }

    public void startLogging() {
        isLogging = true;
        // TODO: Integrate with browser usage API and log events
    }

    public void stopLogging() {
        isLogging = false;
    }

    // Call this when a browser event occurs
    public void logEvent(String eventType) {
        if (isLogging) {
            long ts = System.currentTimeMillis();
            String raw = eventType + "_" + ts;
            String hashed = HashUtil.hash(raw);
            CSVWriter.append("browser", hashed, ts);
        }
    }
}
