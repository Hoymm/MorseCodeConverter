package com.hoymm.root.morsecodeconverter._4_MorseKeyboardAndDisableSoftKeyboard;

import android.app.Activity;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public class DotButton extends WriteButton {
    private static ImageButton instance = null;

    public static ImageButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new DotButton(activity);
        return instance;
    }

    public DotButton(Activity activity) {
        super(activity);
        instance = (ImageButton) getActivity().findViewById(R.id.dot_button_id);
        setWriteButtonBehavior(instance, '·');// this is not a dot
    }

    public static void setNull() {
        instance = null;
    }
}
