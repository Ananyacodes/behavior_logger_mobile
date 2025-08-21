package com.example.behaviorauth.models;

public class BehaviorEvent {
    public String source;
    public String hashedValue;
    public long timestamp;

    public BehaviorEvent(String source, String hashedValue, long timestamp) {
        this.source = source;
        this.hashedValue = hashedValue;
        this.timestamp = timestamp;
    }
}
