package com.hoymm.root.morsecodeconverter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.hoymm.root.morsecodeconverter.InputOutputFields.AnimationForEditTextBoxes;

/**
 * File created by Damian Muca - Kaizen on 20.06.17.
 */

public class MorseKeyboardPanel {
    private Context context;
    private LinearLayout morseKeyboardPanel;
    private ImageButton spaceButton, lineButton, dotButton, backspaceButton;
    private ValueAnimator hidePanelAnimation, showPanelAnimation;

    public MorseKeyboardPanel(Context context) {
        this.context = context;
        initObjects();
    }

    private void initObjects() {
        initXMLObjects();
        initAnimation();
    }

    private void initXMLObjects() {
        morseKeyboardPanel = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
        spaceButton = (ImageButton) getActivity().findViewById(R.id.space_button_id);
        lineButton = (ImageButton) getActivity().findViewById(R.id.line_button_id);
        dotButton = (ImageButton) getActivity().findViewById(R.id.dot_button_id);
        backspaceButton = (ImageButton) getActivity().findViewById(R.id.backspace_button_id);
    }

    private void initAnimation() {
        initHideAnimation();
        initShowAnimation();
    }

    private void initHideAnimation() {
        final ViewGroup.LayoutParams params = morseKeyboardPanel.getLayoutParams();
        hidePanelAnimation = ValueAnimator.ofFloat(
                getActivity().getResources().getDimension(R.dimen.morse_keyboard_height), 1);
        hidePanelAnimation.setDuration(AnimationForEditTextBoxes.animationTime);
        hidePanelAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        hidePanelAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                params.height = (int) animatedValue;
                morseKeyboardPanel.setLayoutParams(params);
            }

        });

        hidePanelAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationEnd(Animator animation)
            {
                morseKeyboardPanel.setVisibility(View.GONE);
            }
        });
    }

    private void initShowAnimation() {
        final ViewGroup.LayoutParams params = morseKeyboardPanel.getLayoutParams();
        showPanelAnimation = ValueAnimator.ofFloat(1,
                getActivity().getResources().getDimension(R.dimen.morse_keyboard_height));
        showPanelAnimation.setDuration(AnimationForEditTextBoxes.animationTime);
        showPanelAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        showPanelAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float animatedValue = (float) valueAnimator.getAnimatedValue();
                params.height = (int) animatedValue;
                morseKeyboardPanel.setLayoutParams(params);
            }
        });

        showPanelAnimation.addListener(new AnimatorListenerAdapter()
        {
            @Override
            public void onAnimationStart(Animator animation) {
                morseKeyboardPanel.setVisibility(View.VISIBLE);
            }
        });
    }

    public void hideOrShowMorsePanel(){
        if(MorseToTextSwappingPanel.convertTextToMorse)
            hidePanel();
        else
            showPanel();
    }

    private void showPanel(){
        if(!isAnyAnimationRunning())
            showPanelAnimation.start();
    }

    private void hidePanel(){
        if(!isAnyAnimationRunning())
            hidePanelAnimation.start();
    }

    private boolean isAnyAnimationRunning() {
        return showPanelAnimation.isRunning() || hidePanelAnimation.isRunning();
    }

    private Activity getActivity() {
        return (Activity)context;
    }

}
