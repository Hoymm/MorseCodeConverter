package com.hoymm.root.morsecodeconverter._4_MorseKeyboard;

import android.app.Activity;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public class DotButton extends WriteButton implements Singleton {
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

    @Override
    public void setNull() {
        instance = null;
    }
}
