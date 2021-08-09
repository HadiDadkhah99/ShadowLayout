package com.foc.libs.shadowLayoutPro;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;

public class ShadowUtil {


    public static Bitmap resizeBitmap(Bitmap bitmap, int width, int height) {

        //src rect
        RectF src = new RectF(0, 0, bitmap.getWidth(), bitmap.getHeight());
        //destination rect
        RectF dst = new RectF(0, 0, width, height);

        //resize with scale (scale type fit xy)
        Matrix matrix = new Matrix();
        matrix.setRectToRect(src, dst, Matrix.ScaleToFit.FILL);

        //result bitmap
        Bitmap resBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);



        return resBitmap;
    }
}
