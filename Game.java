package com.example.rain;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class Game extends View {
    MainActivity activity;
    ArrayList<Circle> circles = new ArrayList<>();
    Rect rect;
    int num_circ, width, height;
    boolean active;

    public Game(Context context) {
        super(context);
        activity = (MainActivity) context;
        active = false;
        start();
    }

    public void start(){
        Log.d("SIZES", "w = " + String.valueOf(width) + "h = " + String.valueOf(height));
        float x, y;
        Random rand = new Random(System.currentTimeMillis());
        num_circ = 5 + rand.nextInt(6);
        for (int i = 0; i < num_circ; i++) {
            x = 0 + (float) Math.random() * 1000;
            y = 0 + (float) Math.random() * (1000/2);
            int red = 1 + rand.nextInt(256);
            int green = 1 + rand.nextInt(256);
            int blue = 1 + rand.nextInt(256);
            float radius = 30 + rand.nextInt(101);
            Circle new_circ = new Circle(x, y, radius, i, Color.rgb(red, green, blue));
            circles.add(new_circ);
        }
    }

    public void onDraw(Canvas canvas) {
        width =  getWidth();
        height =  getHeight();
        for (int i = 0; i < num_circ; i++) {
            circles.get(i).draw(canvas);
        }
        rect = new Rect(0, height - 300, width, height, Color.YELLOW);
        rect.draw(canvas);
    }


    public boolean onTouchEvent(MotionEvent event) {
        int current_circ = -1;
        int done_circ = 0;
        float dX = 0, dY = 0;
        int lastAction;

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                for (int i = 0; i < num_circ; i++) {
                    if (circles.get(i).inCircle(event.getX(), event.getY())) {
                        dX = circles.get(i).x - event.getRawX();
                        dY = circles.get(i).y - event.getRawY();
                        current_circ = i;
                        active = true;
                        lastAction = MotionEvent.ACTION_DOWN;
                        break;
                    }
                }

                break;

            case MotionEvent.ACTION_MOVE:
                circles.get(current_circ).x = event.getRawX() + dX;
                circles.get(current_circ).y = event.getRawY() + dY;
                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (rect.inRect(event.getX(), event.getY()) && circles.get(current_circ).color == rect.color) {
                    done_circ++;
                    circles.remove(circles.get(current_circ));
                    if (done_circ == num_circ) {
                        Toast end = Toast.makeText(activity, "Вы собрали все шары!", Toast.LENGTH_LONG);
                        end.show();
                        done_circ = 0;
                        circles.clear();
                        start();
                    }
                    current_circ = -1;

                }
                invalidate();
                break;

            default:
                return false;
        }

        return false;
    }
}
