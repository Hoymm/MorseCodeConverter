package com.hoymm.root.morsecodeconverter.FooterPanel;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class VibrationFeature implements FooterFeature {
    private Vibrator vibrator;
    private Activity activity;

    public VibrationFeature(Activity activity) {
        this.activity = activity;
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void start(int time) {
        vibrator.vibrate(time);
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    private Activity getActivity(){
        return activity;
    }
}
