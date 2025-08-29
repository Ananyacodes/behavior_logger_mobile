package com.example.behaviorauth.sensors;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;


public class TouchLogger {
    private View rootView;
    private View.OnTouchListener touchListener;

    public TouchLogger(Activity activity) {
        rootView = activity.getWindow().getDecorView().getRootView();
        touchListener = (v, event) -> {
            long ts = System.currentTimeMillis();
            String raw = "action=" + event.getAction() +
                    ",x=" + event.getX() +
                    ",y=" + event.getY() +
                    ",p=" + event.getPressure();
            String hashed = HashUtil.hash(raw);
            CSVWriter.append("touch", hashed, ts);
            return false;
        };
    }

    public void startLogging() {
        if (rootView != null && touchListener != null) {
            rootView.setOnTouchListener(touchListener);
        }
    }

    public void stopLogging() {
        if (rootView != null) {
            rootView.setOnTouchListener(null);
        }
    }
}
