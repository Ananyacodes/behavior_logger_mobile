package com.example.behaviorauth.sensors;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;

import com.example.behaviorauth.utils.CSVWriter;
import com.example.behaviorauth.utils.HashUtil;

import java.util.List;

public class AppUsageLogger {

    public AppUsageLogger(Context ctx) {
        UsageStatsManager usm = (UsageStatsManager) ctx.getSystemService(Context.USAGE_STATS_SERVICE);
        long end = System.currentTimeMillis();
        long start = end - 60000; // last 1 min
        List<UsageStats> stats = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, start, end);

        for (UsageStats usage : stats) {
            String raw = usage.getPackageName() + "_" + usage.getTotalTimeInForeground();
            String hashed = HashUtil.hash(raw);
            CSVWriter.append("appusage", hashed, end);
        }
    }
}
