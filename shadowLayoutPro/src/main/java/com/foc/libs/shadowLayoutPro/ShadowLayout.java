package com.foc.libs.shadowLayoutPro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowLayout extends FrameLayout {
    
    String TAG="ShadowLayout";

    //shadow value
    public int shadowValue = 25;
    //dx
    public float dx = 0;
    //dy
    public float dy = 0;

    //shadow paint
    private Paint shadowPaint;
    //shadow color
    private int shadowColor = Color.parseColor("#212121");

    public ShadowLayout(@NonNull Context context) {
        super(context);
        init(null);
    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ShadowLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * Layout initialization
     */
    private void init(@Nullable AttributeSet attrs) {

        //get attrs
        getAttrs(attrs);

        //create shadow paint
        this.shadowPaint = createShadowPaint();

    }

    /**
     * get All layout attrs
     */
    private void getAttrs(@Nullable AttributeSet attrs) {

        if (attrs == null) return;

        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);

        //get shadow value
        shadowValue = typedArray.getInteger(R.styleable.ShadowLayout_shadow_value, shadowValue);
        //get color
        shadowColor = typedArray.getColor(R.styleable.ShadowLayout_shadow_color, shadowColor);
        //get dx , dy
        dx = typedArray.getDimension(R.styleable.ShadowLayout_shadow_dx, dx);
        dy = typedArray.getDimension(R.styleable.ShadowLayout_shadow_dy, dy);

        //main
        typedArray.recycle();

    }

    /**
     * create shadow paint
     */
    private Paint createShadowPaint() {

        Paint paint = new Paint();
        //layer type
        setLayerType(LAYER_TYPE_SOFTWARE, paint);

        //set shadow value
        paint.setMaskFilter(new BlurMaskFilter(shadowValue, BlurMaskFilter.Blur.NORMAL));

        return paint;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        Log.i(TAG, "dispatchDraw: ");

        try {

            //create  bitmap for canvas
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);

            //create canvas for draw shadow on it
            Canvas shadowCanvas = new Canvas(bitmap);

            //draw shadow on canvas
            super.dispatchDraw(shadowCanvas);

            /*
             * Now we can get all shadow data from bitmap
             */
            Bitmap shadowBitmap = bitmap.extractAlpha();

            //*******************draw bitmap with alpha color
            //set shadow color
            shadowPaint.setColor(getFullAlphaColor(false));
            //draw shadow on main canvas
            canvas.drawBitmap(shadowBitmap, dx, dy, shadowPaint);


            //******************draw bitmap with full alpha color
            //set shadow color
            shadowPaint.setColor(getFullAlphaColor(true));
            //draw childes with full alpha
            canvas.drawBitmap(bitmap, 0, 0, shadowPaint);

        } catch (Exception e) {
            e.printStackTrace();
        }

        //draw all childes
        super.dispatchDraw(canvas);
    }


    private int getFullAlphaColor(boolean isFull) {
        return Color.argb(isFull ? 255 : Color.alpha(shadowColor), Color.red(shadowColor), Color.green(shadowColor), Color.blue(shadowColor));
    }
}