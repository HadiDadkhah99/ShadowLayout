package com.foc.libs.shadowLayoutPro;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ShadowLayout extends FrameLayout {

    String TAG = "ShadowLayout";
    String LAYOUT_TAG = "";

    //shadow value
    public int shadowValue = 25;
    //dx
    public float dx = 0;
    //dy
    public float dy = 0;
    //color
    public int shadowColor = Color.parseColor("#212121");

    //**************margins
    //margin right
    public float marginRight = 0;
    //margin bottom
    public float marginBottom = 0;
    //margin left
    public float marginLeft = 0;
    //margin top
    public float marginTop = 0;

    //shadow paint
    private Paint shadowPaint;

    //allow draw shadow
    private boolean isAllowDrawShadow = true;

    //just draw shadow
    private boolean isJustShadow = false;

    //shadow behavior
    private ShadowBehavior shadowBehavior;


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
    protected void init(@Nullable AttributeSet attrs) {


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

        //get margins
        marginLeft = typedArray.getDimension(R.styleable.ShadowLayout_shadow_margin_left, marginLeft);
        marginTop = typedArray.getDimension(R.styleable.ShadowLayout_shadow_margin_top, marginTop);
        marginRight = typedArray.getDimension(R.styleable.ShadowLayout_shadow_margin_right, marginRight);
        marginBottom = typedArray.getDimension(R.styleable.ShadowLayout_shadow_margin_bottom, marginBottom);


        //allow shadow
        isAllowDrawShadow = typedArray.getBoolean(R.styleable.ShadowLayout_shadow_allowed, isAllowDrawShadow);

        //just shadow
        isJustShadow = typedArray.getBoolean(R.styleable.ShadowLayout_shadow_just, isJustShadow);

        //get shadow tag
        LAYOUT_TAG = typedArray.getString(R.styleable.ShadowLayout_shadow_tag);
        LAYOUT_TAG=LAYOUT_TAG == null ? "" : LAYOUT_TAG;

        //***get shadow behavior
        String className = typedArray.getString(R.styleable.ShadowLayout_shadow_behavior);
        try {
            Class oClass = Class.forName(className, true, ShadowBehavior.class.getClassLoader());
            shadowBehavior = (ShadowBehavior) oClass.newInstance();
        } catch (Exception ignored) {
            Log.i(TAG, "err-->" + ignored.toString());
        }

        //main
        typedArray.recycle();

    }

    /**
     * create shadow paint
     */
    private Paint createShadowPaint() {

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        //layer type
        setLayerType(LAYER_TYPE_SOFTWARE, paint);


        //set shadow value
        paint.setMaskFilter(new BlurMaskFilter(shadowValue, BlurMaskFilter.Blur.NORMAL));

        return paint;
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {

        long startDrawTime = System.currentTimeMillis();

        try {

            //if allowed
            if (isAllowDrawShadow) {

                //shadow behavior
                if (shadowBehavior != null)
                    shadowPaint.setShader(new LinearGradient(0, 0, (int) (Math.cos(shadowBehavior.getAngle()) * getWidth()), (int) (Math.sin(shadowBehavior.getAngle()) * getWidth()), shadowBehavior.getStartColor(), shadowBehavior.getEndColor(), Shader.TileMode.CLAMP));


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

                shadowBitmap = ShadowUtil.resizeBitmap(shadowBitmap, (int) (shadowBitmap.getWidth() - marginRight - marginLeft), (int) (shadowBitmap.getHeight() - marginBottom - marginTop));

                //*******************draw bitmap with alpha color
                //set shadow color
                shadowPaint.setColor(getFullAlphaColor(false));
                //draw shadow on main canvas
                canvas.drawBitmap(shadowBitmap, dx + marginLeft, dy + marginTop, shadowPaint);


                //******************draw bitmap with full alpha color
                if (!isJustShadow) {
                    //set shadow color
                    shadowPaint.setColor(getFullAlphaColor(true));
                    //draw childes with full alpha
                    canvas.drawBitmap(bitmap, 0, 0, shadowPaint);
                }


                bitmap.recycle();
                shadowBitmap.recycle();
                System.gc();

            } else {
                //draw all childes
                super.dispatchDraw(canvas);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        long endDrawTime = System.currentTimeMillis() - startDrawTime;
        //log draw time
        Log.i(TAG, "Draw_Time" + (LAYOUT_TAG.isEmpty() ? "" : ("(" + LAYOUT_TAG + ")")) + "=" + endDrawTime);

    }


    private int getFullAlphaColor(boolean isFull) {
        return Color.argb(isFull ? 255 : Color.alpha(shadowColor), Color.red(shadowColor), Color.green(shadowColor), Color.blue(shadowColor));
    }

    /**
     * Enable shadow
     */
    public void enableShadow() {
        isAllowDrawShadow = true;
        postInvalidate();
    }

    /**
     * Disable shadow
     */
    public void disableShadow() {
        isAllowDrawShadow = false;
        postInvalidate();
    }


    public boolean isAllowDrawShadow() {
        return isAllowDrawShadow;
    }

    public boolean isJustShadow() {
        return isJustShadow;
    }

    public void setJustShadow(boolean justShadow) {
        isJustShadow = justShadow;
    }

    public void setAllowDrawShadow(boolean allowDrawShadow) {
        isAllowDrawShadow = allowDrawShadow;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void setMarginBottom(float marginBottom) {
        this.marginBottom = marginBottom;
    }

    public void setMarginLeft(float marginLeft) {
        this.marginLeft = marginLeft;
    }

    public void setMarginRight(float marginRight) {
        this.marginRight = marginRight;
    }

    public void setMarginTop(float marginTop) {
        this.marginTop = marginTop;
    }

    public void setShadowColor(int shadowColor) {
        this.shadowColor = shadowColor;
    }

    public void setShadowValue(int shadowValue) {
        this.shadowValue = shadowValue;
    }

    public void setLAYOUT_TAG(String LAYOUT_TAG) {
        this.LAYOUT_TAG = LAYOUT_TAG;
    }
}