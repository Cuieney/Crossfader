package crossfader.cuieney.www.crossfader.rx;

import android.hardware.SensorManager;

import rx.Observable;

/**
 * Created by cuieney on 16/12/12.
 */
public final class RxSensor {


    public static Observable<float[]> registerSensor(SensorManager sensorManager){

        return Observable.create(new SensorChangeOnSubscribe(sensorManager));
    }

}
