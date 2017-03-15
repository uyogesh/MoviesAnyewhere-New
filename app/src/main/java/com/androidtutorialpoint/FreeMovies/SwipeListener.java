package com.androidtutorialpoint.FreeMovies;

/**
 * Created by flowing on 3/5/17.
 */

import android.content.Context;
import android.gesture.Gesture;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class SwipeListener implements View.OnTouchListener {

    final GestureDetector gestureDetector ;

    public SwipeListener (Context ctx) {
        gestureDetector = new GestureDetector(ctx,new GestureListener());
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }


    final class GestureListener implements GestureDetector.OnGestureListener{

        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }
}
