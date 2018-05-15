package com.example.manish.wassup;

/**
 * Created by Manish on 03-Dec-17.
 */

public class Conv {

    public boolean seen;
    public long timestamp;

    public  Conv(){

    }

    public Conv(boolean seen, long timestamp) {
        this.seen = seen;
        this.timestamp = timestamp;
    }

    public boolean isSeen() {
        return seen;
    }

    public void setseen(boolean seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
