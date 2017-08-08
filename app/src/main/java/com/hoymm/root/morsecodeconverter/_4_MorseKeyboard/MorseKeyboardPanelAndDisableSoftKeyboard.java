package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.hoymm.root.morsecodeconverter._2_TextBoxes.ResizingTextBoxesAnimation;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton.BackspaceButton;

/**
 * File created by Damian Muca - Kaizen on 20.06.17.
 */

public class MorseKeyboardPanelAndDisableSoftKeyboard {
    private Context context;
    private LinearLayout morseKeyboardPanel;
    private ValueAnimator hidePanelAnimation, showPanelAnimation;

    public MorseKeyboardPanelAndDisableSoftKeyboard(Context context) {
        this.context = context;
        initObjects();
        initAnimation();
        changeButtonsBehavior();
    }

    private void initObjects() {
        initializateButtons();
        initXMLObjects();
    }

    private void initializateButtons() {
        Log.i("WriteBUTTON", "initializate buttons.");
        SpaceButton.initAndGetInstance(getActivity());
        DotButton.initAndGetInstance(getActivity());
        LineButton.initAndGetInstance(getActivity());
        BackspaceButton.initAndGetInstance(getActivity());

    }

    private void initXMLObjects() {
        morseKeyboardPanel = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
    }

    private void initAnimation() {
        initHideAnimation();
        initShowAnimation();
    }

    private void initHideAnimation() {
        final ViewGroup.LayoutParams params = morseKeyboardPanel.getLayoutParams();
        hidePanelAnimation = ValueAnimator.ofFloat(
                getActivity().getResources().getDimension(R.dimen.morse_keyboard_height), 1);
        hidePanelAnimation.setDuration(ResizingTextBoxesAnimation.animationTime);
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
        showPanelAnimation.setDuration(ResizingTextBoxesAnimation.animationTime);
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

    public void hideOrShowMorsePanelAnimation(){
        if(MorseToTextArrowsSwap.isConvertingTextToMorse)
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

    public void disableOrEnableSystemKeyboard() {
        if (MorseToTextArrowsSwap.isConvertingTextToMorse)
            enableSystemKeyboard();
        else
            disableSystemKeyboard();
    }

    private void enableSystemKeyboard() {
        TextBoxes.initAndGetUpperBox(getActivity()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mImm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mImm.showSoftInput(TextBoxes.initAndGetUpperBox(getActivity()), InputMethodManager.SHOW_IMPLICIT);
            }
        });
        Log.i("SystemKeyboard", "enabled (when converting text-> morse).");
    }

    private void disableSystemKeyboard() {
        TextBoxes.initAndGetUpperBox(getActivity()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Log.i("SystemKeyboard", "disabled (when converting morse-> text)..");
    }

    private boolean isAnyAnimationRunning() {
        return showPanelAnimation.isRunning() || hidePanelAnimation.isRunning();
    }


    private void changeButtonsBehavior() {
        BackspaceButton.setNewBackspaceButtonBehavior();
    }

    private Activity getActivity() {
        return (Activity)context;
    }
}
