    package com.example.view;

    import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

    /**
     * Created by puppey on 4/13/14.
     */
    public class PuSlidingLinearLayout extends LinearLayout {

        private float xFraction = 0;

        public PuSlidingLinearLayout(Context context) {
            super(context);
        }

        public PuSlidingLinearLayout(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        public PuSlidingLinearLayout(Context context, AttributeSet attrs, int defStyle) {
            super(context, attrs, defStyle);
        }

        private ViewTreeObserver.OnPreDrawListener preDrawListener = null;

        public void setXFraction(float fraction) {

            this.xFraction = fraction;

            if (getHeight() == 0) {
                if (preDrawListener == null) {
                    preDrawListener = new ViewTreeObserver.OnPreDrawListener() {
                        @Override
                        public boolean onPreDraw() {
                            getViewTreeObserver().removeOnPreDrawListener(preDrawListener);
                            setXFraction(xFraction);
                            return true;
                        }
                    };
                    getViewTreeObserver().addOnPreDrawListener(preDrawListener);
                }
                return;
            }

            float translationX = getWidth() * fraction;
            setTranslationX(translationX);
//            Log.i("translationX", "translationX->"+getHeight()+"*"+fraction+"="+translationX);
        }

        public float getXFraction() {
            return this.xFraction;
        }

        @Override
        public void setAlpha(float alpha) {
            super.setAlpha(alpha);
        }
    }
