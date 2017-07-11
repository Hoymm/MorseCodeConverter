package com.hoymm.root.morsecodeconverter._3_MorseKeyboard;

import android.app.Activity;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public class LineButton extends WriteButton {
    private static ImageButton instance = null;

    public static ImageButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new LineButton(activity);
        return instance;
    }

    public LineButton(Activity activity) {
        super(activity);
        instance = (ImageButton) getActivity().findViewById(R.id.line_button_id);
        setWriteButtonBehavior(instance, '−');// this is not a minus
    }
}
