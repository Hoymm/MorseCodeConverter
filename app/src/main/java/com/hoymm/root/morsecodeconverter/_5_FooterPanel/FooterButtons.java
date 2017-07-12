package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;

/**
 * File created by Damian Muca - Kaizen on 12.07.17.
 */

public class FooterButtons {
    public static boolean atLeastOneFooterButtonActive(Activity activity){
        return
                VibrationButton.initializateAndGetInstance(activity).isActive() ||
                SoundButton.initializateAndGetInstance(activity).isActive() ||
                FlashlightButton.initializateAndGetInstance(activity).isActive() ||
                ScreenButton.initializateAndGetInstance(activity).isActive();
    }

}
