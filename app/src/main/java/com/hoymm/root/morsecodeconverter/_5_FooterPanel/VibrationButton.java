package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class VibrationButton extends ButtonsTemplate implements FooterButtonsInterface {
    private static VibrationButton instance = null;
    private Vibrator vibrator;

    public static VibrationButton initializateAndGetInstance(Activity activity){
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
                if (button.isActivated())
                    Toast.makeText(getActivity(), "Vibration Activated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Vibration Deactivated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void startIfActiveAndPermissionsGranted(int time) {
        if (button.isActivated()) {
            if (isPermissionGranted())
                vibrator.vibrate(time);

        }
    }

    @Override
    public boolean isButtonActive() {
        return button.isActivated();
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }
}
