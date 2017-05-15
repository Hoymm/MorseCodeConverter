package com.hoymm.root.morsecodeconverter;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import static android.R.attr.animationDuration;

/**
 * Created by root on 06.05.17.
 */

public class TranslatingMiddlePanel {
    private Context myContext;
    private RelativeLayout textInput, textOutput;
    private ImageButton copyToClipboardButton, swapTextButton, playButton;

    public TranslatingMiddlePanel(Context context) {
        myContext = context;
        initializeImageButtons();
        resizeInputAndOutputTextFields();
    }

    private void initializeImageButtons() {
        swapTextButton = (ImageButton) getActivity().findViewById(R.id.swap_circle_button_id);
        playButton = (ImageButton) getActivity().findViewById(R.id.play_button_id);
        copyToClipboardButton = (ImageButton) getActivity().findViewById(R.id.clipboard_button_id);

        // TODO delete that what's below
        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void resizeInputAndOutputTextFields() {
        resizeInputField();
        resizeOutputField();
    }

    private void resizeInputField() {
        RelativeLayout textInputField = (RelativeLayout) getActivity().findViewById(R.id.inputTextFieldId);
        textInputField.getLayoutParams().height = 650;

    }

    private BoxResizingAnimation textOutputBox;
    private void resizeOutputField() {


        textOutputBox = new BoxResizingAnimation(getContext(), R.id.outputTextFieldId);


        Toast.makeText(getContext(), "ANIMATIO StaRTED", Toast.LENGTH_SHORT).show();

    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }
}
