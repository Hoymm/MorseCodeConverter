package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 15.06.17.
 */

public class AnimationForTextEditBoxes extends EditText {
    private static final int animationTime = 1800;
    private RelativeLayout upperBox, lowerBox;
    private Context myContext;
    public ValueAnimator upperBoxToBig, upperBoxToSmall, lowerBoxToBig, lowerBoxToSmall;
    private CalculateHeightsForInputOutput heights;

    public AnimationForTextEditBoxes(Context context) {
        super(context);
        this.myContext = context;
        heights = new CalculateHeightsForInputOutput(myContext);
        connectEditTextFieldWithXML();
        initializateAnimations();
    }

    private void connectEditTextFieldWithXML() {
        upperBox = (RelativeLayout) getActivity().findViewById(R.id.inputEditTextFieldId);
        lowerBox = (RelativeLayout) getActivity().findViewById(R.id.outputEditTextFieldId);
    }

    private void initializateAnimations() {
        upperBoxToBigAnimationInit();
        upperBoxToSmallAnimationInit();
        lowerBoxToBigAnimationInit();
        lowerBoxToSmallAnimationInit();
    }

    // TODO FINISH THESE ANIMATIONS
    private void upperBoxToBigAnimationInit() {
        final RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) upperBox.getLayoutParams();
        upperBoxToBig = ValueAnimator.ofFloat(heights.upperBoxSmallHeight, heights.upperBoxBigHeight);
        upperBoxToBig.setDuration(animationTime);
        upperBoxToBig.setInterpolator(new AccelerateDecelerateInterpolator());
        upperBoxToBig.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                params.height = (int) animatedValue;
                upperBox.setLayoutParams(params);
            }
        });
        upperBoxToBig.start(
    }

    private void upperBoxToSmallAnimationInit() {

    }

    private void lowerBoxToBigAnimationInit() {

    }

    private void lowerBoxToSmallAnimationInit() {

    }

    private Activity getActivity(){
        return ((Activity)myContext);
    }

    public void startConstrictingAnimation() {

    }
}
