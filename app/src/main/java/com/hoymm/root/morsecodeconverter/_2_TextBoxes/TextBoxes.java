package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

public class TextBoxes {
    private static Toast editOnlyWhenStopActive = null;

    public static EditText initAndGetUpperBox(Activity activity){
        return (EditText) activity.findViewById(R.id.upper_edit_text_box);
    }

    public static EditText initAndGetBottomBox(Activity activity){
        return (EditText) activity.findViewById(R.id.bottom_text_view_box);
    }

    public static void setUpperBoxScrollable(Activity activity){
        initAndGetUpperBox(activity).setVerticalScrollBarEnabled(true);
        initAndGetUpperBox(activity).setMovementMethod(new ScrollingMovementMethod());
    }

    public static void setBottomBoxScrollable(Activity activity){
        initAndGetBottomBox(activity).setVerticalScrollBarEnabled(true);
        initAndGetBottomBox(activity).setMovementMethod(new ScrollingMovementMethod());
    }

    public static void showToastEditTextAllowedOnlyWhenStopButtonActive(Activity activity) {
        if (editOnlyWhenStopActive != null)
            editOnlyWhenStopActive.cancel();
        editOnlyWhenStopActive = Toast.makeText(activity, R.string.edit_only_when_stop_active, Toast.LENGTH_LONG);
        editOnlyWhenStopActive.show();
    }
}
