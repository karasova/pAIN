package com.example.rainbowgame;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Rect {
    float left, top, right, bottom;
    int color;

    public Rect(float left, float top, float right, float bottom, int color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
    }

    public void draw(Canvas canvas) {
        Paint p = new Paint();
        p.setColor(color);
        canvas.drawRect(left, top, right, bottom, p);
    }

    public boolean inRect(float point_x, float point_y) {
        if (point_x >= left && point_x <= right && point_y >= top && point_y <= bottom) {
            return true;
        }
        return false;
    }
}
