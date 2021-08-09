package com.foc.libs.shadowLayoutPro;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Test extends FrameLayout {

    public Test(@NonNull Context context) {
        super(context);
    }

    public Test(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Test(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        Canvas tempCanvas = new Canvas(bitmap);
        super.dispatchDraw(tempCanvas);
        Bitmap childesData = bitmap.extractAlpha();

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLUE);
        setLayerType(LAYER_TYPE_SOFTWARE, paint);
        paint.setMaskFilter(new BlurMaskFilter(50, BlurMaskFilter.Blur.NORMAL));


        canvas.drawBitmap(childesData, 0, 0, paint);

        super.dispatchDraw(canvas);
    }
}
