package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 28.07.17.
 */

public class ControlButtonsSharedPreferences {


    public static void saveCurrentlyActiveButton(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        if (PlayButton.initAndGetInstance(activity).isActive())
            editor.putString(getControlButtonActiveKey(activity), ControlButtonsEnum.PLAY.toString());
        else if (PauseButton.initAndGetInstance(activity).isActive())
            editor.putString(getControlButtonActiveKey(activity), ControlButtonsEnum.PAUSE.toString());
        else if (StopButton.initAndGetInstance(activity).isActive())
            editor.putString(getControlButtonActiveKey(activity), ControlButtonsEnum.STOP.toString());
        else
            editor.putString(getControlButtonActiveKey(activity), ControlButtonsEnum.NONE.toString());

        editor.apply();
    }

    public static void restoreLatelyActivatedButton(Activity activity){
        switch(getLastActivatedButton(activity)){
            case PLAY:
                PauseButton.initAndGetInstance(activity).callOnClick();
                break;
            case PAUSE:
                PauseButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
                break;
            case STOP:
                StopButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
                break;
            case NONE:
                StopButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
                break;
            default:
                StopButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
                break;

        }
    }

    private static ControlButtonsEnum getLastActivatedButton(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return ControlButtonsEnum.stringToEnum(getActiveButtonAsString(activity, sharedPref));
    }

    private static String getActiveButtonAsString(Activity activity, SharedPreferences sharedPref) {
        return sharedPref.getString(getControlButtonActiveKey(activity), ControlButtonsEnum.NONE.toString());
    }


    private static String getControlButtonActiveKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.currentControlButtonActiveSP);
    }
}
