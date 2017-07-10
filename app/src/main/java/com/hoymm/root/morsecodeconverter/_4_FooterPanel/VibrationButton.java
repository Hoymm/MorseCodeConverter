package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.content.Context;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class VibrationButton implements FooterButtons {
    private static VibrationButton instance = null;
    private Vibrator vibrator;
    private Activity activity;
    private ImageButton vibrateButton;

    public static VibrationButton initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new VibrationButton(activity);
        return instance;
    }

    private VibrationButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        vibrateButton = (ImageButton) getActivity().findViewById(R.id.vibration_button_id);
        vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public void start(int time) {
        vibrator.vibrate(time);
    }

    private void setButtonBehavior() {
        vibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrateButton.setActivated(!vibrateButton.isActivated());
                if (vibrateButton.isActivated())
                    Toast.makeText(getActivity(), "Vibration Activated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Vibration Deactivated", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean isPermissionGranted() {
        // vibration is a permission always granting statically
        return true;
    }

    private Activity getActivity() {
        return activity;
    }
}
