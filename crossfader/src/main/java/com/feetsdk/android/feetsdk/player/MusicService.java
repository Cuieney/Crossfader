package com.feetsdk.android.feetsdk.player;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by cuieney on 16/11/21.
 */
public class MusicService extends Service {
    IMusicServiceHandler handler;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return handler.onBind(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        handler = new MusicServiceHandler(new WeakReference<MusicService>(this));
    }

    @Override
    public void onDestroy() {
        handler.onDestroy();
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

}
