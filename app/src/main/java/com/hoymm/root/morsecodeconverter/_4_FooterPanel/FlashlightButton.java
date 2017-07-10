package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class FlashlightButton implements FooterButtons{
    private static FlashlightButton instance;
    private Activity activity;
    private ImageButton flashlightButton;

    public static FlashlightButton initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightButton(activity);
        return instance;
    }

    private FlashlightButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        flashlightButton = (ImageButton) getActivity().findViewById(R.id.flashlight_button_id);
    }

    @Override
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return false;
    }

    private void setButtonBehavior() {
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashlightButton.setActivated(!flashlightButton.isActivated());
            }
        });
    }

    private Activity getActivity() {
        return activity;
    }
}
