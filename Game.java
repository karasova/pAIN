package com.example.rainbowgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
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
    int current_circ, last_i;
    int done_circs;

    public Game(Context context) {
        super(context);
        activity = (MainActivity) context;
        active = false;
        last_i = 0;
    }

    public void start(){
        float x, y;
        Random rand = new Random(System.currentTimeMillis());
        num_circ = 5 + rand.nextInt(6);
        for (int i = 0; i < num_circ; i++) {
            x = (float) (10 + rand.nextInt(width));
            y = (float) (10 + rand.nextInt(2 * height / 3));
            int red = 1 + rand.nextInt(256);
            int green = 1 + rand.nextInt(256);
            int blue = 1 + rand.nextInt(256);
            float radius = 50 + rand.nextInt(101);
            Circle new_circ = new Circle(x, y, radius, i, Color.rgb(red, green, blue));
            circles.add(new_circ);
        }
    }

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged (w, h, oldw, oldh);
        width = w;
        height = h;
        start();
    }

    public void onDraw(Canvas canvas) {
        width =  getWidth();
        height =  getHeight();
        for (int i = last_i; i < num_circ; i++) {
            circles.get(i).draw(canvas);
        }
        rect = new Rect(0, height - 300, width, height, circles.get(last_i).color);
        rect.draw(canvas);
    }


    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:

                for (int i = 0; i < num_circ; i++) {
                    if (circles.get(i).inCircle(event.getX(), event.getY())) {
                        current_circ = i;
                        active = true;
                        break;
                    }
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (active == true) {
                    circles.get(current_circ).x = event.getX();
                    circles.get(current_circ).y = event.getY();
                }
                break;

            case MotionEvent.ACTION_UP:

                if (rect.inRect(event.getX(), event.getY()) && circles.get(current_circ).color == rect.color && active == true) {
                    done_circs++;
                    last_i = current_circ+1;
                    if (done_circs == num_circ) {
                        Toast end = Toast.makeText(activity, "Вы собрали все шары!", Toast.LENGTH_LONG);
                        end.show();
                        done_circs = 0;
                        circles.clear();
                        start();
                        last_i = 0;
                    }
                    current_circ = -1;
                    active = false;

                }
                break;

            default:
                return true;
        }
        invalidate();
        return true;
    }
}
