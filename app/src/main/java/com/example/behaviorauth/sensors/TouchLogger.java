package com.example.behaviorauth.sensors;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;

public class TouchLogger {

    public TouchLogger(Activity activity) {
        View rootView = activity.getWindow().getDecorView().getRootView();

        rootView.setOnTouchListener((v, event) -> {
            long ts = System.currentTimeMillis();
            String raw = "action=" + event.getAction() +
                    ",x=" + event.getX() +
                    ",y=" + event.getY() +
                    ",p=" + event.getPressure();
            String hashed = HashUtil.hash(raw);
            CSVWriter.append("touch", hashed, ts);
            return false;
        });
    }
}
