package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

abstract class WriteButton extends ImageButton {
    private Activity activity;

    WriteButton(Context context) {
        super(context);
        activity = (Activity)context;
    }

    protected void setWriteButtonBehavior(ImageButton imageButton, final char insertChar) {
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextBoxes.initAndGetUpperBox(getActivity()).isEnabled())
                    setOnClickAction(insertChar);
            }
        });
    }

    private void setOnClickAction(char insertChar) {
        String currentText =
                TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        int selectionStart =
                TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        int selectionEnd =
                TextBoxes.initAndGetUpperBox(getActivity()).getSelectionEnd();
        currentText =
                currentText.substring(0, selectionStart)
                        + insertChar
                        + currentText.substring(selectionEnd);
        TextBoxes.initAndGetUpperBox(getActivity()).setText(currentText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selectionStart + 1);
    }

    protected Activity getActivity() {
        return activity;
    }
}
