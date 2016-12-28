package crossfader.cuieney.www.crossfader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.cover_img_one)
    ImageView coverImgOne;
    @BindView(R.id.cover_img_two)
    ImageView coverImgTwo;
    @BindView(R.id.value1)
    TextView value1;
    @BindView(R.id.value2)
    TextView value2;
    @BindView(R.id.value3)
    TextView value3;

    public SensorManager sensormanager;

    private Bitmap bmp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        value1 = (TextView) findViewById(R.id.value1);
        value2 = (TextView) findViewById(R.id.value2);
        value3 = (TextView) findViewById(R.id.value3);
        coverImgOne = (ImageView) findViewById(R.id.cover_img_one);
        coverImgTwo = (ImageView) findViewById(R.id.cover_img_two);

        sensormanager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

        RxSensor.registerSensor(sensormanager)
                .throttleFirst(30, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<float[]>() {
                    @Override
                    public void call(final float[] values) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                value1.setText(values[0] + "");
                                value2.setText(values[1] + "");
                                value3.setText(values[2] + "");
                            }
                        });

                        float value = values[1];

                        float rate = (Math.abs(value) / 25);
                        if (Math.abs(rate-lastBigValue) >0.07) {
                            if (value > 0) {
                                scalePicture(1 + rate, 1 - rate);
                            } else
                                scalePicture(1 - rate, 1 + rate);

                            }
                    }
                });
    }


    private float lastBigValue = 1;
    private float lastSmallValue = 1;
    AnimatorSet set = new AnimatorSet();

    private void scalePicture(float bigValue, float smallValue) {
        Logger.d(lastBigValue+",,"+bigValue);
        if (bigValue<1.5 && bigValue > 0.5 && smallValue<1.5 && smallValue> 0.5) {

            if (lastBigValue != bigValue && lastSmallValue != smallValue) {

                set.playTogether(
                        ObjectAnimator.ofFloat(coverImgOne, "scaleX", lastBigValue, bigValue),
                        ObjectAnimator.ofFloat(coverImgOne, "scaleY", lastBigValue, bigValue),
                        ObjectAnimator.ofFloat(coverImgTwo, "scaleX", lastSmallValue, smallValue),
                        ObjectAnimator.ofFloat(coverImgTwo, "scaleY", lastSmallValue, smallValue)

                );
                set.setInterpolator(new AccelerateInterpolator());
                set.setDuration(10).start();
                lastBigValue = bigValue;
                lastSmallValue = smallValue;
            }
        }

    }


    private void BigPicture(float bigValue, float smallValue) {


        Matrix matrix = new Matrix();
        matrix.postScale(bigValue, bigValue, bmp.getWidth() / 2, bmp.getHeight() / 2);
        Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas = new Canvas(createBmp);
        Paint paint = new Paint();
        canvas.drawBitmap(bmp, matrix, paint);

        Matrix matrix1 = new Matrix();
        matrix1.postScale(smallValue, smallValue, bmp.getWidth() / 2, bmp.getHeight() / 2);
        Bitmap createBmp1 = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
        Canvas canvas1 = new Canvas(createBmp1);
        Paint paint1 = new Paint();
        canvas1.drawBitmap(bmp, matrix1, paint1);


        coverImgOne.setBackgroundColor(Color.RED);
        coverImgTwo.setBackgroundColor(Color.RED);
        coverImgOne.setImageBitmap(createBmp);
        coverImgTwo.setImageBitmap(createBmp1);
    }


    public void ShowPhotoByImageView() {
//        Uri imageFileUri = data.getData();
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;    //手机屏幕水平分辨率
        int height = dm.heightPixels;  //手机屏幕垂直分辨率
        Log.v("height", "" + height);
        Log.v("width", "" + width);
//        try {
        // Load up the image's dimensions not the image itself
        BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
        bmpFactoryOptions.inJustDecodeBounds = true;
//            bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
        int heightRatio = (int) Math.ceil(bmpFactoryOptions.outHeight / (float) height);
        int widthRatio = (int) Math.ceil(bmpFactoryOptions.outWidth / (float) width);
        Log.v("bmpheight", "" + bmpFactoryOptions.outHeight);
        Log.v("bmpheight", "" + bmpFactoryOptions.outWidth);
        if (heightRatio > 1 && widthRatio > 1) {
            if (heightRatio > widthRatio) {
                bmpFactoryOptions.inSampleSize = heightRatio * 2;
            } else {
                bmpFactoryOptions.inSampleSize = widthRatio * 2;
            }
        }
        //图像真正解码
        bmpFactoryOptions.inJustDecodeBounds = false;
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.fade);
//            bmp = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageFileUri), null, bmpFactoryOptions);
//            imageShow.setImageBitmap(bmp); //将剪裁后照片显示出来
//            textview1.setVisibility(View.VISIBLE);
        CreatePhotoByImageView();
//        } catch(FileNotFoundException e) {
//            e.printStackTrace();
//        }
    }

    //创建第二张图片并显示
    public void CreatePhotoByImageView() {
        try {
            Bitmap createBmp = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());
            Canvas canvas = new Canvas(createBmp); //画布 传入位图用于绘制
            Paint paint = new Paint(); //画刷 改变颜色 对比度等属性
            canvas.drawBitmap(bmp, 0, 0, paint);    //错误:没有图片 因为参数bmp写成createBmp
            coverImgOne.setImageBitmap(createBmp);
//            textview2.setVisibility(View.VISIBLE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
