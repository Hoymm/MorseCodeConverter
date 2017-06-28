package com.hoymm.root.morsecodeconverter.MorseKeyboard;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.text.InputType;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.InputOutputFields.ResizingAnimationForTextBoxes;
import com.hoymm.root.morsecodeconverter.MainActivity;
import com.hoymm.root.morsecodeconverter.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter.MorseToTextSwappingPanel;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 20.06.17.
 */

public class MorseKeyboardPanel {
    private Context context;
    private LinearLayout morseKeyboardPanel;
    private ImageButton lineButton, dotButton;
    private SpaceButton spaceButton;
    private BackspaceButton backspaceButton;
    private EditText upperTextBox;
    private ValueAnimator hidePanelAnimation, showPanelAnimation;

    public MorseKeyboardPanel(Context context) {
        this.context = context;
        initObjects();
        initAnimation();
        setButtonsBehavior();
    }

    private void initObjects() {
        initXMLObjects();
    }

    private void initXMLObjects() {
        morseKeyboardPanel = (LinearLayout) getActivity().findViewById(R.id.morseKeyboardId);
        lineButton = (ImageButton) getActivity().findViewById(R.id.line_button_id);
        dotButton = (ImageButton) getActivity().findViewById(R.id.dot_button_id);
        spaceButton = (SpaceButton) getActivity().findViewById(R.id.space_button_id);
        backspaceButton = (BackspaceButton) getActivity().findViewById(R.id.backspace_button_id);
        upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
    }

    private void initAnimation() {
        initHideAnimation();
        initShowAnimation();
    }

    private void initHideAnimation() {
        final ViewGroup.LayoutParams params = morseKeyboardPanel.getLayoutParams();
        hidePanelAnimation = ValueAnimator.ofFloat(
                getActivity().getResources().getDimension(R.dimen.morse_keyboard_height), 1);
        hidePanelAnimation.setDuration(ResizingAnimationForTextBoxes.animationTime);
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
        showPanelAnimation.setDuration(ResizingAnimationForTextBoxes.animationTime);
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
        if(MorseToTextSwappingPanel.isConvertingTextToMorse)
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
        if(MorseToTextSwappingPanel.isConvertingTextToMorse)
            enableSystemKeyboard();
        else
            disableSystemKeyboard();
    }

    private void enableSystemKeyboard() {
        upperTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager mImm = (InputMethodManager)
                        getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                mImm.showSoftInput(upperTextBox, InputMethodManager.SHOW_IMPLICIT);
            }
        });
        Log.e("System keyboard", "enabled.");
    }

    private void disableSystemKeyboard() {
        upperTextBox.setTextIsSelectable(true);
        upperTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        Log.e("System keyboard", "disabled.");
    }

    private boolean isAnyAnimationRunning() {
        return showPanelAnimation.isRunning() || hidePanelAnimation.isRunning();
    }


    private void setButtonsBehavior() {
        setWriteButtonBehavior(lineButton, '−');// this is not a minus
        setWriteButtonBehavior(dotButton, '·');// and this is not a dot
        setSpaceButtonBehavior();
        setBackspaceButtonBehavior();
    }

    private void setWriteButtonBehavior(ImageButton imageButton, final char insertChar) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentText = upperTextBox.getText().toString();
                int selectionStart = upperTextBox.getSelectionStart();
                int selectionEnd = upperTextBox.getSelectionEnd();
                currentText =
                        currentText.substring(0, selectionStart)
                        + insertChar
                        + currentText.substring(selectionEnd);
                upperTextBox.setText(currentText);
                upperTextBox.setSelection(selectionStart+1);
            }
        });
    }

    private void setSpaceButtonBehavior() {

    }

    private void setBackspaceButtonBehavior() {
        backspaceButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        backspaceButton.startRemoving();
                        break;
                    case MotionEvent.ACTION_UP:
                        backspaceButton.stopRemoving();
                        break;

                }
                return false;
            }
        });
    }


    private Activity getActivity() {
        return (Activity)context;
    }

}
