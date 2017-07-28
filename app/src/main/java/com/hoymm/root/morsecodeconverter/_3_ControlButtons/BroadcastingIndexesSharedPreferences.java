package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 28.07.17.
 */

public class BroadcastingIndexesSharedPreferences {
    public static void saveIndexesOfCurrentlyBroadcastingTextToSP(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt(getStartTextIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getStartBroadcastingTextIndex());
        editor.putInt(getEndTextIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getEndBroadcastingTextIndex());
        editor.putInt(getStartMorseIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getStartBroadcastingMorseIndex());
        editor.putInt(getEndMorseIndexSPKey(activity), ConvertMorseToSignals.initAndGetInstance(activity).getEndBroadcastingMorseIndex());

        editor.apply();
    }

    private static String getStartTextIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.textStartIndexSP);
    }

    private static String getEndTextIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.textEndIndexSP);
    }

    private static String getStartMorseIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.morseStartIndexSP);
    }

    private static String getEndMorseIndexSPKey(Activity activity) {
        return activity.getBaseContext().getString(R.string.morseEndIndexSP);
    }

    public static void restoreIndexesOfCurBroadcastTextOrSetToDefaultIfNotStoredSP(Activity activity) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        if (ifDataAboutIndexesAreStored(activity, sharedPref))
            restoreIndexesOfCurBroadcastText(activity, sharedPref);
        else
            ConvertMorseToSignals.initAndGetInstance(activity).setBroadcastTextIndexToStart();
    }

    private static boolean ifDataAboutIndexesAreStored(Activity activity, SharedPreferences sharedPref) {
        return sharedPref.getInt(getStartTextIndexSPKey(activity), -1) != -1;
    }

    private static void restoreIndexesOfCurBroadcastText(Activity activity, SharedPreferences sharedPref) {
        ConvertMorseToSignals.initAndGetInstance(activity).textCharStart = (sharedPref.getInt(getStartTextIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).textCharEnd = (sharedPref.getInt(getEndTextIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).morseCharStart = (sharedPref.getInt(getStartMorseIndexSPKey(activity), 0));
        ConvertMorseToSignals.initAndGetInstance(activity).morseCharEnd = (sharedPref.getInt(getEndMorseIndexSPKey(activity), 0));
    }
}
