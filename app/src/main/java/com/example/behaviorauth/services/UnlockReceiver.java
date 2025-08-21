package com.example.behaviorauth.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;

public class UnlockReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        long ts = System.currentTimeMillis();
        String hashed = HashUtil.hash("unlock_event_" + ts);
        CSVWriter.append("unlock", hashed, ts);
    }
}
