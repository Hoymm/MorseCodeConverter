package com.hoymm.root.morsecodeconverter._4_MorseKeyboard.BackspaceButton;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 26.06.17.
 */

public class BackspaceButton extends ImageButton {
    private static ImageButton instance = null;
    private static Activity activity;
    private static boolean isPressed = false;

    public static ImageButton initAndGetInstance(Activity activity){
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

    public static void setNewBackspaceButtonBehavior() {
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
        removeSelectedChar();
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
            removeSelectedChar();
            try {
                Thread.sleep(removingCharIntervalSpeedWhenLongPress);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void removeSelectedChar() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextSelectionRemove textSelectionRemove = new TextSelectionRemove(getActivity());
                textSelectionRemove.removeTextAccordingToSelectionAndSetNewTextAndSetSelection();
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
