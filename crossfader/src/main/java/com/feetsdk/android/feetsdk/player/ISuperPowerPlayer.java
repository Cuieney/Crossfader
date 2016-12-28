package com.feetsdk.android.feetsdk.player;

import java.io.File;

/**
 * Created by cuieney on 16/8/25.
 */
public interface ISuperPowerPlayer {
    void onPlayPause(boolean play);
    void play(File file);
    void play(String url);
    void setTempo(float rate);
    void setBpm(float bpm);
    void setSeek(int percent);
    boolean isPlaying();
}
