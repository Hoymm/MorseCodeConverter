package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.text.method.ScrollingMovementMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

public class TextBoxes {
    private static Toast editOnlyWhenStopActive = null;
    private static EditText upperBox = null, bottomBox = null;

    public static EditText initAndGetUpperBox(Activity activity){
        if (upperBox == null) {
            upperBox = (EditText) activity.findViewById(R.id.upper_edit_text_box);
            setUpperTextBehaviorWhenOnClicked(activity);
        }
        return upperBox;
    }

    private static void setUpperTextBehaviorWhenOnClicked(final Activity activity) {
        upperBox.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP
                        && !StopButton.initAndGetInstance(activity).isActive())
                    TextBoxes.showToastEditTextAllowedOnlyWhenStopButtonActive(activity);
                return false;
            }
        });
    }

    public static EditText initAndGetBottomBox(Activity activity){
        if (bottomBox == null)
            bottomBox = (EditText) activity.findViewById(R.id.bottom_text_view_box);
        return bottomBox;
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
        editOnlyWhenStopActive = Toast.makeText(activity, R.string.edit_only_when_stop_active, Toast.LENGTH_SHORT);
        editOnlyWhenStopActive.show();
    }

    public static void saveTextBoxesContentDataToSP(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString(getUpperBoxSPKey(activity), TextBoxes.initAndGetUpperBox(activity).getText().toString());
        editor.putString(getBottomBoxSPKey(activity), TextBoxes.initAndGetBottomBox(activity).getText().toString());
        editor.apply();
    }

    private static String getUpperBoxSPKey(Activity activity){
        return activity.getBaseContext().getString(R.string.upperTextBoxTextContentSP);
    }

    private static String getBottomBoxSPKey(Activity activity){
        return activity.getBaseContext().getString(R.string.bottomTextBoxTextContentSP);
    }

    public static void restoreTextBoxesContentFromSharedPreferences(Activity activity) {
        restoreUpperTextBox(activity);
        restoreBottomTextBox(activity);
    }

    private static void restoreUpperTextBox(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        TextBoxes.initAndGetUpperBox(activity).setText(sharedPref.getString(getUpperBoxSPKey(activity), ""));
    }

    private static void restoreBottomTextBox(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        TextBoxes.initAndGetBottomBox(activity).setText(sharedPref.getString(getBottomBoxSPKey(activity), ""));
    }

    public static void setNull() {
        upperBox = bottomBox = null;
    }
}
