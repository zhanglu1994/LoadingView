package com.example.zhangl.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.RelativeLayout;

public class LoadingView extends RelativeLayout {
    private CircleView mLeftView,mMiddleView,mRightView;





    private int mTranslationDistance = 20;

    public LoadingView(Context context) {
        this(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mTranslationDistance = dip2px(50);
        mLeftView = getCircleView(context);
        mLeftView.setColor(Color.BLACK);
        mMiddleView = getCircleView(context);
        mMiddleView.setColor(Color.RED);
        mRightView = getCircleView(context);
        mRightView.setColor(Color.GREEN);

        addView(mLeftView);
        addView(mRightView);
        addView(mMiddleView);

        post(new Runnable() {
            @Override
            public void run() {
                //布局实例化好之后  再去开启动画
                outAnimation();

            }
        });
    }

    private void outAnimation() {
        //往外面跑

        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView,"translationX",0,-mTranslationDistance);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView,"translationX",0,mTranslationDistance);


        //弹性效果



        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new DecelerateInterpolator(1f));
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                inAnimation();
            }
        });

        set.start();
    }

    private void inAnimation() {
        //里面边跑

        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView,"translationX",-mTranslationDistance,0);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView,"translationX",mTranslationDistance,0);


        AnimatorSet set = new AnimatorSet();
        set.setDuration(500);
        set.setInterpolator(new AccelerateInterpolator(1f));
        set.playTogether(leftTranslationAnimator,rightTranslationAnimator);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                int leftColor = mLeftView.getColor();
                int middleColor = mMiddleView.getColor();
                int rightColor = mRightView.getColor();

                mMiddleView.setColor(leftColor);
                mRightView.setColor(middleColor);
                mLeftView.setColor(rightColor);


                outAnimation();
            }
        });

        set.start();
    }

    public CircleView getCircleView(Context context){
        CircleView circleView = new CircleView(context);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(dip2px(15),dip2px(15));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    private int dip2px(int dip) {

        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dip,getResources().getDisplayMetrics());

    }

}
