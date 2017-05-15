package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by root on 06.05.17.
 */

public class MiddleTextBoxes {
    private Context myContext;
    private RelativeLayout textInput, textOutput;
    private ImageButton copyToClipboardButton, swapTextButton, playButton;

    public MiddleTextBoxes(Context context) {
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

    private MiddleTextBoxesResizingAnimation textOutputBox;
    private void resizeOutputField() {


        textOutputBox = new MiddleTextBoxesResizingAnimation(getContext(), R.id.outputTextFieldId);


        Toast.makeText(getContext(), "ANIMATIO StaRTED", Toast.LENGTH_SHORT).show();

    }

    private Context getContext(){
        return myContext;
    }

    private Activity getActivity(){
        return ((Activity)getContext());
    }
}
