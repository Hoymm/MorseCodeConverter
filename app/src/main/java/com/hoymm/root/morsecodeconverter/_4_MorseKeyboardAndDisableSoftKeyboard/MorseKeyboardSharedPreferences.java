package com.hoymm.root.morsecodeconverter._4_MorseKeyboardAndDisableSoftKeyboard;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;

/**
 * File created by Damian Muca - Kaizen on 28.07.17.
 */

public class MorseKeyboardSharedPreferences {

    public static void saveCurrentlyActiveButton(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean(getVibrationButtonActiveKey(activity), VibrationButton.initAndGetInstance(activity).isActive());
        editor.putBoolean(getSoundButtonActiveKey(activity), SoundButton.initAndGetInstance(activity).isActive());
        editor.putBoolean(getFlashlightButtonActiveKey(activity), FlashlightButton.initAndGetInstance(activity).isActive());
        editor.putBoolean(getScreenButtonActiveKey(activity), ScreenButton.initAndGetInstance(activity).isActive());

        editor.apply();
    }

    private static String getVibrationButtonActiveKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.vibrationButtonActiveSP);
    }

    private static String getSoundButtonActiveKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.soundButtonActiveSP);
    }

    private static String getFlashlightButtonActiveKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.flashlightButtonActiveSP);
    }

    private static String getScreenButtonActiveKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.screenButtonActiveSP);
    }

    public static void restoreLatelyActiveButtons(Activity activity){
        restoreVibrationButtonState(activity);
        restoreSoundButtonState(activity);
        restoreFlashlightButtonState(activity);
        restoreScreenButtonState(activity);
    }

    private static void restoreVibrationButtonState(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getVibrationButtonActiveKey(activity), false))
            VibrationButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
        else
            VibrationButton.initAndGetInstance(activity).deactivateIfNotYetInactive();
    }

    private static void restoreSoundButtonState(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getSoundButtonActiveKey(activity), false))
            SoundButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
        else
            SoundButton.initAndGetInstance(activity).deactivateIfNotYetInactive();

    }

    private static void restoreFlashlightButtonState(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getFlashlightButtonActiveKey(activity), false))
            FlashlightButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
        else
            FlashlightButton.initAndGetInstance(activity).deactivateIfNotYetInactive();

    }

    private static void restoreScreenButtonState(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if(sharedPref.getBoolean(getScreenButtonActiveKey(activity), false))
            ScreenButton.initAndGetInstance(activity).makeButtonActiveIfNotYet();
        else
            ScreenButton.initAndGetInstance(activity).deactivateIfNotYetInactive();

    }
}
