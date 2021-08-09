package com.foc.libs.shadowlayout;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class Test extends View {

    public Test(Context context) {
        super(context);
    }

    public Test(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Test(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setMaskFilter(new BlurMaskFilter(30, BlurMaskFilter.Blur.NORMAL));
        setLayerType(LAYER_TYPE_SOFTWARE, paint);

        //canvas.drawOval(new RectF(100, 100, 400, 400), paint);



        Matrix matrix = new Matrix();
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        RectF dst = new RectF(0, 0, 150, 150);
        matrix.setRectToRect(src,dst, Matrix.ScaleToFit.FILL);
        Bitmap bitmap1=Bitmap.createBitmap(bitmap,0,0,(int)src.width(),(int)src.height(),matrix,true);
        canvas.drawBitmap(bitmap1.extractAlpha(), 100, 120, paint);

        matrix = new Matrix();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.app_icon);
        src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        dst = new RectF(0, 0, 200, 200);
        matrix.setRectToRect(src,dst, Matrix.ScaleToFit.FILL);
        bitmap1=Bitmap.createBitmap(bitmap,0,0,(int)src.width(),(int)src.height(),matrix,true);
        canvas.drawBitmap(bitmap1,100,100, null);


    }
}
