package com.feetsdk.android.feetsdk.stepcount;

import android.content.Context;

/**
 * Created by cuieney on 16/12/21.
 */
public class StepController {
    public static IController healthControl;

    private Context context;
    public static IController getInstance(Context context){
        return healthControl == null ? healthControl = new Pedometer(context):healthControl;
    }

    private StepController(Context context) {
        this.context = context;
    }

}
