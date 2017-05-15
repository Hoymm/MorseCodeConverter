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
    private static final int animationTime = 800;

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
        smallToBigAnimationInit();
        bigToSmallAnimInitialize();
    }

    private void smallToBigAnimationInit() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) box.getLayoutParams();
        animateToBig = ValueAnimator.ofFloat(params.height, params.height+300);
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
    }

    private void bigToSmallAnimInitialize() {

    }

    private void calculateBoxHeights() {
        int dpHeightOfRestAppElements = calculateDPHeightOfRestAppElements();
        smallBoxHeight = 0;
    }

    private int calculateDPHeightOfRestAppElements() {
        int overallHeight = 0;

        LinearLayout topSpinnerBar;
        topSpinnerBar = (LinearLayout) getActivity().findViewById(R.id.headerId);
        overallHeight += topSpinnerBar.getLayoutParams().height;

        LinearLayout swappingTranslatingModePanel;
        swappingTranslatingModePanel = (LinearLayout) getActivity().findViewById(R.id.swappingPanelId);
        overallHeight += swappingTranslatingModePanel.getLayoutParams().height;

        LinearLayout footerWithButtons;
        footerWithButtons = (LinearLayout) getActivity().findViewById(R.id.applicationFooterId);
        overallHeight += footerWithButtons.getLayoutParams().height;


        // TODO initialize it.
        /*RelativeLayout morseCodingPanel
        morseCodingPanel = (RelativeLayout) getActivity().findViewById(R.id.headerId);
        overallHeight += morseCodingPanel.getHeight();
        */
        Log.e("Overal HEIGHT", " amounts " + overallHeight + " !!!!!!!!!!!!!!!!!!!!!!!!");
        return overallHeight;
    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }


}
