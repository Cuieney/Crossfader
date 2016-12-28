package com.feetsdk.android.feetsdk.player;

import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import com.feetsdk.android.common.utils.Logger;
import com.feetsdk.android.feetsdk.ISuperPowerPlayerService;
import com.feetsdk.android.feetsdk.Music;

import java.lang.ref.WeakReference;

/**
 * Created by cuieney on 16/11/21.
 */
public class MusicServiceHandler extends ISuperPowerPlayerService.Stub implements IMusicServiceHandler {
    private final WeakReference<MusicService> mService;
    private MusicMgr musicMgr;

    public MusicServiceHandler(WeakReference<MusicService> mService) {
        this.mService = mService;
        musicMgr = new MusicMgr(new WeakReference<Context>(mService.get()));
        Logger.d("server MusicServiceHandler");
    }


    @Override
    public void onStartCommand(Intent intent, int flags, int startId) {
        if (musicMgr == null) {
            musicMgr = new MusicMgr(new WeakReference<Context>(mService.get()));
        }
        Logger.d("server onStartCommand");
    }

    @Override
    public IBinder onBind(Intent intent) {
        return this;
    }

    @Override
    public void onDestroy() {

        Logger.d("server onDestroy");
    }

    @Override
    public void pause() throws RemoteException {
        musicMgr.pause();
    }

    @Override
    public void start() throws RemoteException {
        musicMgr.start();
    }

    @Override
    public void next() throws RemoteException {
        musicMgr.next();
    }

    @Override
    public void preview() throws RemoteException {
        musicMgr.preview();
    }

    @Override
    public void stop() throws RemoteException {
        if (musicMgr != null) {
            musicMgr.pause();
            musicMgr.stop();
            musicMgr = null;
        }
        Logger.d("server stop");
    }

    @Override
    public void setTempo(float rate) throws RemoteException {
        musicMgr.setTempo(rate);
    }

    @Override
    public void setBpm(float bpm) throws RemoteException {
        musicMgr.setBpm(bpm);
    }

    @Override
    public void setSeek(int percent) throws RemoteException {
        musicMgr.setSeek(percent);
    }

    @Override
    public Music getCurrentMusic() throws RemoteException {
        return musicMgr.getCurrentMusic();
    }

    @Override
    public void play(int type) throws RemoteException {
        musicMgr.play(type);
    }

    @Override
    public boolean isPlaying() throws RemoteException {
        return musicMgr.isPlaying();
    }

    @Override
    public void favMusic() throws RemoteException {
        musicMgr.favMusic();
    }

}
