package com.feetsdk.android.feetsdk.player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;

import com.feetsdk.android.feetsdk.ISuperPowerPlayerService;
import com.feetsdk.android.feetsdk.Music;
import com.feetsdk.android.feetsdk.player.callBack.IMusicChange;

import java.lang.ref.WeakReference;

/**
 * Created by cuieney on 16/11/21.
 */
public class PlayerControler implements IMusicControler{
    public static final String MUSIC_CHANGE = "MUSIC_CHANGE";

    private IMusicChange change;

    private WeakReference<Context> context;
    private ISuperPowerPlayerService iSuperPowerPlayerService;
    private int type;

    private BroadcastReceiver playerServiceBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            handleBroadcastReceived(context, intent);
        }
    };

    private void handleBroadcastReceived(Context context, Intent intent) {
        switch (intent.getAction()) {
            case MUSIC_CHANGE:
                if (change != null) {
                    if (getCurrentMusic() != null) {
                        change.currentMusic(getCurrentMusic());
                    }
                }
                break;
        }
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iSuperPowerPlayerService = ISuperPowerPlayerService.Stub.asInterface(service);
            play(type);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            iSuperPowerPlayerService = null;

        }
    };


    public PlayerControler(Context context,int type) {
        this.context = new WeakReference<>(context);
        this.type = type;
        Intent intent = new Intent(context, MusicService.class);
        context.startService(intent);

        context.bindService(intent,mConnection,Context.BIND_AUTO_CREATE);


        IntentFilter filter = new IntentFilter();
        filter.addAction(MUSIC_CHANGE);
        context.registerReceiver(playerServiceBroadcastReceiver, filter);
    }

    @Override
    public void pause() {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.pause();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start() {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.start();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void next() {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.next();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void preview() {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.preview();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
        context.get().unbindService(mConnection);

        if (iSuperPowerPlayerService != null) {
            context.get().stopService(new Intent(context.get(),
                    MusicService.class));
        }

        context.get().unregisterReceiver(playerServiceBroadcastReceiver);

        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.stop();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setTempo(float rate) {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.setTempo(rate);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setBpm(float bpm) {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.setBpm(bpm);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setSeek(int percent) {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.setSeek(percent);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Music getCurrentMusic() {
        Music music = new Music();
        try {
            if (iSuperPowerPlayerService != null) {
                music = iSuperPowerPlayerService.getCurrentMusic();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return music;
    }

    @Override
    public void play(int type) {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.play(type);
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPlaying()  {
        boolean playing = false;
        try {
            if (iSuperPowerPlayerService != null) {
                playing = iSuperPowerPlayerService.isPlaying();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return playing;
    }

    @Override
    public void favMusic() {
        try {
            if (iSuperPowerPlayerService != null) {
                iSuperPowerPlayerService.favMusic();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }


    public void registerMusicChange(IMusicChange change){
        this.change = change;
    }

}
