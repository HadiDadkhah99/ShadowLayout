package com.foc.libs.shadowLayoutPro;

import android.graphics.Color;

public  class ShadowBehavior {

    private int angle=45;
    private int startColor = Color.RED;
    private int endColor = Color.BLUE;

    public ShadowBehavior() {
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }

    public int getAngle() {
        return angle;
    }

    public int getEndColor() {
        return endColor;
    }

    public int getStartColor() {
        return startColor;
    }
}
