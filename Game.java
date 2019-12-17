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
    boolean visited;

    public Game(Context context) {
        super(context);
        activity = (MainActivity) context;
        this.width = getWidth();
        this.height = getHeight();
        visited = false;
    }

    public void onDraw(Canvas canvas) {
        for (int i = 0; i < num_circ; i++) {
            circles.get(i).draw(canvas);
        }
        rect = new Rect(0, height - 300, width, height, circles.get(0).color);
        rect.draw(canvas);
    }

    public void start(){
        float x, y;
//        Random rand = new Random(System.currentTimeMillis());
//        num_circ = 5 + rand.nextInt(6);
        num_circ = 1;
        for (int i = 0; i < num_circ; i++) {
            x = (float) 100;
            y = (float) 100;
            int red = 255;
            int green = 16;
            int blue = 125;
            float radius = 36;
            Circle new_circ = new Circle(x, y, radius, i, Color.rgb(red, green, blue));
            circles.add(new_circ);
        }
    }

    public boolean onTouchEvent(MotionEvent event) {
        int current_circ = -1;
        int done_circ = 0;
        if (event.getAction() == MotionEvent.ACTION_DOWN){
            for (int i = 0; i < num_circ; i++) {
                if (circles.get(i).inCircle(event.getX(), event.getY())) {
                    current_circ = i;
                    visited = true;
                    break;
                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            circles.get(current_circ).x = event.getX();
            circles.get(current_circ).y = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
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
            return true;
        }
        return false;
    }
}
