package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.widget.EditText;
import android.widget.TextView;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

public class TextBoxes {
    private static EditText upperTextBox = null;
    private static TextView bottomBox = null;

    public static EditText initAndGetUpperBox(Activity activity){
        if (upperTextBox == null)
            upperTextBox = (EditText) activity.findViewById(R.id.upper_edit_text_box);
        return upperTextBox;
    }

    public static TextView initAndGetBottomBox(Activity activity){
        if (bottomBox == null)
            bottomBox = (TextView) activity.findViewById(R.id.bottom_text_view_box);
        return bottomBox;
    }

    public static void setNull(){
        upperTextBox = null;
        bottomBox = null;
    }
}
