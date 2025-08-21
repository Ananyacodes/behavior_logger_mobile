package com.example.behaviorauth.sensors;

import android.inputmethodservice.InputMethodService;
import android.view.KeyEvent;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;

public class KeystrokeIME extends InputMethodService {

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        long ts = System.currentTimeMillis();
        String raw = "down_" + keyCode + "_" + ts;
        String hashed = HashUtil.hash(raw);
        CSVWriter.append("keystroke", hashed, ts);
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        long ts = System.currentTimeMillis();
        String raw = "up_" + keyCode + "_" + ts;
        String hashed = HashUtil.hash(raw);
        CSVWriter.append("keystroke", hashed, ts);
        return super.onKeyUp(keyCode, event);
    }
}
