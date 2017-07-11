package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class BackspaceButton extends ImageButton {
    private static ImageButton instance = null;
    private static Activity activity;
    private static boolean isPressed = false;

    static ImageButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new BackspaceButton(activity);
        return instance;
    }

    private BackspaceButton(Activity activity) {
        super(activity);
        BackspaceButton.activity = activity;
        initObjects();
        setNewBackspaceButtonBehavior();
    }

    private void initObjects() {
        instance = (ImageButton) getActivity().findViewById(R.id.backspace_button_id);
    }

    static void setNewBackspaceButtonBehavior() {
        instance.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()){
                    case MotionEvent.ACTION_DOWN:
                        startRemoving();
                        break;
                    case MotionEvent.ACTION_UP:
                        stopRemoving();
                        break;

                }
                return false;
            }
        });
    }

    static void startRemoving() {
        isPressed = true;
        startANewThreadToRemoveCharsFromUpperBox();
    }

    private static void startANewThreadToRemoveCharsFromUpperBox() {
        new Thread(new Runnable() {
            public void run()
            {
                deletionBeforeLongPress();
                deletionWhenLongPress();
            }}).start();
    }

    private static void deletionBeforeLongPress() {
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

    private static void deletionWhenLongPress() {
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

    private static void removeCharFromEditText() {
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

    static void stopRemoving() {
        isPressed = false;
    }

    private static Activity getActivity(){
        return activity;
    }
}
