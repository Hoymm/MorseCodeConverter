package com.hoymm.root.morsecodeconverter.InputOutputFields;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * Created by root on 06.05.17.
 */

public class ConvertingPaneEditTextBoxes {
    private Context myContext;
    private ImageButton copyToClipboardButton, playButton;
    private CalculateDataAndPerformAnimationForTextEditMiddleBoxes calculateDataAndPerformAnimationForTextEditMiddleBoxes;
    private boolean convertingFromTextToMorseDirection = true;

    public ConvertingPaneEditTextBoxes(Context context) {
        myContext = context;
        calculateDataAndPerformAnimationForTextEditMiddleBoxes = new CalculateDataAndPerformAnimationForTextEditMiddleBoxes(context);
        initializeImageButtons();

    }

    private void initializeImageButtons() {
        playButton = (ImageButton) getActivity().findViewById(R.id.play_button_id);
        copyToClipboardButton = (ImageButton) getActivity().findViewById(R.id.clipboard_button_id);

        // TODO delete that what's below
        copyToClipboardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    public void swapConvertingDirection(){
        convertingFromTextToMorseDirection = !convertingFromTextToMorseDirection;
        if (convertingFromTextToMorseDirection)
            constrictUpperBoxAndExtendLowerBox();
        else
            extendUpperBoxAndConstrictLowerBox();
    }

    private void constrictUpperBoxAndExtendLowerBox() {
        calculateDataAndPerformAnimationForTextEditMiddleBoxes.constrictUpperBox();
        calculateDataAndPerformAnimationForTextEditMiddleBoxes.extendLowerBox();
    }

    private void extendUpperBoxAndConstrictLowerBox() {
        calculateDataAndPerformAnimationForTextEditMiddleBoxes.extendUpperBox();
        calculateDataAndPerformAnimationForTextEditMiddleBoxes.constrictLowerBox();
    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }
}
