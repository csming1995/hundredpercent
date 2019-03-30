package com.csming.percent;

import android.view.MotionEvent;
import android.widget.Toast;

/**
 * @author Created by csming on 2019/3/30.
 */
public abstract class SlideTouchEventListener {

    private float x1 = 0;
    private float y1 = 0;
    private float x2 = 0;
    private float y2 = 0;

    private float distance = 100;

    public void setDistance(float distance) {
        this.distance = distance;
    }

    public void onTouchEvent(MotionEvent event) {
        if(event.getAction() == MotionEvent.ACTION_DOWN) {
            x1 = event.getX();
            y1 = event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_UP) {
            x2 = event.getX();
            y2 = event.getY();
            if(y1 - y2 > distance) {
                onTouchUp();
            } else if(y2 - y1 > distance) {
                onTouchDown();
            } else if(x1 - x2 > distance) {
                onTouchLeft();
            } else if(x2 - x1 > distance) {
                onTouchRight();
            }
        }
    }

    public abstract void onTouchUp();

    public abstract void onTouchDown();

    public abstract void onTouchLeft();

    public abstract void onTouchRight();

}
