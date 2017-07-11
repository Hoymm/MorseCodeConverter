package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

abstract class WriteButton extends ImageButton {
    private EditText upperTextBox;
    private Activity activity;

    WriteButton(Context context) {
        super(context);
        upperTextBox = (EditText) ((Activity)context).findViewById(R.id.upper_edit_text_box);
        activity = (Activity)context;
    }

    protected void setWriteButtonBehavior(ImageButton imageButton, final char insertChar) {
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

    protected Activity getActivity() {
        return activity;
    }
}
