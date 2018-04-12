package com.krohajah.shwabramvpparser.utils;

import android.os.Handler;

/**
 * @author Maxim Berezin
 */
public class UiThread {

    private final Handler handler;

    public UiThread(Handler handler) {
        this.handler = handler;
    }

    public final boolean post(Runnable runnable) {
        return handler.post(runnable);
    }
}
