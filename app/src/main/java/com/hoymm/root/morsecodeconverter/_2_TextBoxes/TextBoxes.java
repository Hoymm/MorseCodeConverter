package com.hoymm.root.morsecodeconverter._2_TextBoxes;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 19.07.17.
 */

public class TextBoxes {
    private static Toast editOnlyWhenStopActive = null;
    private static EditText upperBox = null, bottomBox = null;
    private static KeyListener defaultKeyListener = null;
    private static Activity activity;

    public static EditText initAndGetUpperBox(Activity activity){
        if (upperBox == null) {
            TextBoxes.activity = activity;
            upperBox = (EditText) getActivity().findViewById(R.id.upper_edit_text_box);
            defaultKeyListener = upperBox.getKeyListener();
            setUpperTextBehaviorWhenOnClicked(getActivity());
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
        if (bottomBox == null) {
            TextBoxes.activity = activity;
            bottomBox = (EditText) activity.findViewById(R.id.bottom_text_view_box);
        }
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
        upperBox = null;
        bottomBox = null;
        defaultKeyListener = null;
    }

    public static void setProperTextColor(Activity activity){
        if (StopButton.initAndGetInstance(activity).isActive())
            setTextNormalColor(activity);
        else
            setTextBroadcastColor(activity);
    }

    public static void setTextNormalColor(Activity activity){
        initAndGetUpperBox(activity).setTextColor(ContextCompat.getColor(activity, R.color.normalTextColor));
        initAndGetBottomBox(activity).setTextColor(ContextCompat.getColor(activity, R.color.normalTextColor));
    }

    public static void setTextBroadcastColor(Activity activity){
        initAndGetUpperBox(activity).setTextColor(ContextCompat.getColor(activity, R.color.broadcastTextColor));
        initAndGetBottomBox(activity).setTextColor(ContextCompat.getColor(activity, R.color.broadcastTextColor));
    }

    public static KeyListener getDefaultKeyListener(){
        return defaultKeyListener;
    }

    private static Activity getActivity(){
        return activity;
    }
}
