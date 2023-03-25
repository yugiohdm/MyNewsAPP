package com.example.mynews.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

public class MyViewPager extends ViewPager {
    private boolean isCanScroll = true;

    public MyViewPager(@NonNull Context context) {
        super(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {

        super(context, attrs);

    }



    public void setNoScroll(boolean noScroll) {

        this.isCanScroll = noScroll;

    }


    @Override

    public void scrollTo(int x, int y) {

        super.scrollTo(x, y);

    }



    @Override

    public boolean onTouchEvent(MotionEvent arg0) {

      if (isCanScroll){

       return false;

      }else{

        return super.onTouchEvent(arg0);

       }

    }

    @Override

    public boolean onInterceptTouchEvent(MotionEvent arg0) {

      if (isCanScroll){

           return false;

      }else{

       return super.onInterceptTouchEvent(arg0);

       }

    }





}
