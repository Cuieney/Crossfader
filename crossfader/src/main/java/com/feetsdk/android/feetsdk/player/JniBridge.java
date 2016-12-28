package com.feetsdk.android.feetsdk.player;

/**
 * Created by cuieney on 16/8/25.
 */
public class JniBridge {

    public  native void FeetPower(String apkPath, long[] offsetAndLength);

    public  native void onPlayPause(boolean play);

    public  native void onCrossfader(int value);

    public  native void onFxSelect(int value);

    public  native void onFxOff();

    public  native void onFxValue(int value);

    public  native void onChangeBpm(float value);

    public  native void setTempo(float tempo, boolean masterTempo);

    public  native void setBpm(float bpm);

    public  native void openPathB(String apkPath, long[] offsetAndLength);

    public  native void openPathA(String apkPath, long[] offsetAndLength);

    public  native void openPath(String url);

    public  native float getPositionPercent();

    public  native void Seek(float percent);

    public  native boolean isPlaying();


    public JniBridge() {
    }

    static {
        System.loadLibrary("FeetPower");
    }

}
