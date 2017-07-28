package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class VibrationButton extends ButtonsTemplate implements FooterButtonsInterface, Singleton {
    private static VibrationButton instance = null;
    private Vibrator vibrator;

    public static VibrationButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new VibrationButton(activity);
        return instance;
    }

    private VibrationButton(Activity activity) {
        super(activity, R.id.vibration_button_id);
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setActivated(!button.isActivated());
            }
        });
    }

    @Override
    public void start(int time) {
        vibrator.vibrate(time);
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
    }
}
