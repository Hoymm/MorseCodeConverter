package com.hoymm.root.morsecodeconverter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

/**
 * Created by root on 15.05.17.
 */

public class MiddleTextBoxesResizingAnimation {
    private static final int animationTime = 1800;

    private Context myContext;
    private RelativeLayout box;
    private int bigBoxHeight, smallBoxHeight;

    private ValueAnimator animateToSmall, animateToBig;

    public MiddleTextBoxesResizingAnimation(Context context, int layoutID){

        myContext = context;
        box = (RelativeLayout) getActivity().findViewById(layoutID);
        calculateBoxHeights();
        initializeAnimations();
    }

    private void initializeAnimations(){
        smallToBigAnimationInit(params.height, params.height+300);
        bigToSmallAnimInitialize();
    }

    private void resizeAnimation(int fromHeight, int toHeight) {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) box.getLayoutParams();
        animateToBig = ValueAnimator.ofFloat(fromHeight, toHeight);
        animateToBig.setDuration(animationTime);
        animateToBig.setInterpolator(new AccelerateDecelerateInterpolator());
        animateToBig.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                params.height = (int) animatedValue;
                box.setLayoutParams(params);
            }
        });
        animateToBig.start();
    }

    private void bigToSmallAnimInitialize() {

    }

    private void calculateBoxHeights() {
        int dpHeightOfRestAppElements = calculateDPHeightOfRestAppElements();
        smallBoxHeight = 0;
    }

    private int calculateDPHeightOfRestAppElements() {
        int height = 0;
        LinearLayout topSpinnerBar = (LinearLayout) getActivity().findViewById(R.id.headerId);
        height += topSpinnerBar.getLayoutParams().height;

        LinearLayout swappingTranslatingModePanel = (LinearLayout) getActivity().findViewById(R.id.swappingPanelId);
        height += swappingTranslatingModePanel.getLayoutParams().height;

        LinearLayout footerWithButtons = (LinearLayout) getActivity().findViewById(R.id.applicationFooterId);
        height += footerWithButtons.getLayoutParams().height;

        LinearLayout morseKeyboard = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
        height += morseKeyboard.getLayoutParams().height;
        return height;
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }


}
