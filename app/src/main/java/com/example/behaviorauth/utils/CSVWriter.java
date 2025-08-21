package com.example.behaviorauth.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVWriter {

    private static File logFile;

    public static void init(String filename, Context ctx) {
        File dir = new File(ctx.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "BehaviorLogs");
        if (!dir.exists()) dir.mkdirs();
        logFile = new File(dir, filename);

        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
                FileWriter writer = new FileWriter(logFile, true);
                writer.append("timestamp,source,hash\n");
                writer.close();
            } catch (IOException e) {
                Log.e("CSVWriter", "Init error", e);
            }
        }
    }

    public static void append(String source, String hash, long timestamp) {
        if (logFile == null) return;
        try {
            FileWriter writer = new FileWriter(logFile, true);
            writer.append(timestamp + "," + source + "," + hash + "\n");
            writer.close();
        } catch (IOException e) {
            Log.e("CSVWriter", "Append error", e);
        }
    }
}
