package crossfader.cuieney.www.crossfader.rx;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import rx.Observable;
import rx.Subscriber;
import rx.android.MainThreadSubscription;

/**
 * Created by cuieney on 16/12/12.
 */
public class SensorChangeOnSubscribe implements Observable.OnSubscribe<float[]> {

    final SensorManager sensormanager;

    public SensorChangeOnSubscribe(SensorManager sensormanager) {
        this.sensormanager = sensormanager;
    }

    @Override
    public void call(final Subscriber<? super float[]> subscriber) {
        final SensorEventListener listener = new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                float[] values = event.values;
                subscriber.onNext(values);
            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        };
        sensormanager.registerListener(listener, sensormanager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        subscriber.add(new MainThreadSubscription() {
            @Override
            protected void onUnsubscribe() {
                sensormanager.unregisterListener(listener);
            }
        });
        subscriber.onNext(new float[]{0,0,0});
    }




}
