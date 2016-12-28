package crossfader.cuieney.www.crossfader;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by cuieney on 16/12/12.
 */
public class ScaleView extends View {
    private Paint mPaint;
    private Context context;

    private int width;
    private int height;


    private int defaultScale;

    /**
     * 在那边缩放
     */
    private boolean scaleLeft;

    public ScaleView(Context context) {
        super(context);
        initData(context);
    }

    public ScaleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initData(context);
    }

    public ScaleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData(context);
    }


    private void initData(Context context){
        this.context = context;
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#f00"));
        width = getWidth();
        height = getHeight();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Rect rect = new Rect(width-(width*(defaultScale/10)),width-(width*(defaultScale/10)),0,width-(width*(defaultScale/10)));
        canvas.drawRect(rect,mPaint);

    }

    public synchronized void setScale(int scale){
        defaultScale = scale;
        postInvalidate();
    }
}
