package com.example.aram.rotationobject;

import android.content.Context;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    private GestureDetectorCompat mDetector;
    private ImageView imageObject;
    private  float maxVelocityX ;
    private final float maxFastDuration = 200;
    private final float maxRotationDegree = 360;
    private  float oldDegree = 0;
    private int widthDisplay;
    private int heightDisplay;
    private TextView tvDegree;
    private char degreeSymbol;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageObject = findViewById(R.id.im_object);
        tvDegree = findViewById(R.id.tv_degree);
        maxVelocityX    = ViewConfiguration.get(this).getScaledMaximumFlingVelocity();
        degreeSymbol = 0x00B0;
        WindowManager wm = (WindowManager) this.getSystemService(Context.WINDOW_SERVICE);
        if(wm.getDefaultDisplay()!= null){
             widthDisplay = wm.getDefaultDisplay().getWidth();
             heightDisplay = wm.getDefaultDisplay().getHeight();
        }

        mDetector = new GestureDetectorCompat(this, new GestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }



    class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            if(Math.abs(event1.getY()- event2.getY())<= 200){
                long duration = (long) Math.abs(maxVelocityX/velocityX*maxFastDuration);
                float degree = maxRotationDegree*(event2.getX()-event1.getX())/widthDisplay;
                RotetionObject(imageObject,degree,duration);
            }
            return true;
        }

    }

    private void RotetionObject(ImageView imageView, final float degree, long duration) {
        float degreeTemp = oldDegree+degree;
        RotateAnimation localRotateAnimation = new RotateAnimation(oldDegree, degreeTemp, 1, 0.5F, 1, 0.5F);
        localRotateAnimation.setDuration(duration);
        localRotateAnimation.setFillAfter(true);
        localRotateAnimation.setInterpolator(new LinearInterpolator());
        localRotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                tvDegree.setText("");

            }

            @Override
            public void onAnimationEnd(Animation animation) {
               tvDegree.setText(""+Math.abs(degree)+degreeSymbol);

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        imageView.startAnimation(localRotateAnimation);
        oldDegree = degreeTemp;
    }



}
