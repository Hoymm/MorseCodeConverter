package com.hoymm.root.morsecodeconverter.MorseKeyboard;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class BackspaceButton extends ImageButton {
    private Activity activity;
    private boolean isPressed = false;

    public BackspaceButton(Context context) {
        super(context);
        activity = (Activity)context;
    }

    public BackspaceButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity)context;
    }

    public BackspaceButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        activity = (Activity)context;
    }

    private void removeCharFromEditText() {
        final EditText upperTextBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                String currentText = upperTextBox.getText().toString();
                int selectionStart = upperTextBox.getSelectionStart();
                int selectionEnd = upperTextBox.getSelectionEnd();
                if (selectionStart != 0) {
                    String textAfterDeletion =
                            currentText.substring(0, selectionStart - 1)
                                    + currentText.substring(selectionEnd);
                    upperTextBox.setText(textAfterDeletion);
                    upperTextBox.setSelection(selectionStart - 1);
                }
            }
        });
    }

    public void startRemoving() {
        isPressed = true;
        startANewThreadToRemoveCharsFromUpperBox();
    }

    private void startANewThreadToRemoveCharsFromUpperBox() {
        new Thread(new Runnable() {
            public void run()
            {
                deletionBeforeLongPress();
                deletionWhenLongPress();
            }}).start();
    }

    private void deletionBeforeLongPress() {
        removeCharFromEditText();
        long sleepTime = 700;
        while(isPressed && sleepTime > 0){
            try {
                Thread.sleep(30);
                sleepTime -= 30;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void deletionWhenLongPress() {
        long removingCharIntervalSpeedWhenLongPress = 30;
        while (isPressed) {
            removeCharFromEditText();
            try {
                Thread.sleep(removingCharIntervalSpeedWhenLongPress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void stopRemoving() {
        isPressed = false;
    }

    private Activity getActivity(){
        return activity;
    }
}
