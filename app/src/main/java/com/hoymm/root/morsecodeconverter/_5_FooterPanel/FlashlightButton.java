package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class FlashlightButton extends ButtonsTemplate implements FooterButtonsInterface {
    private static FlashlightButton instance;

    public static FlashlightButton initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightButton(activity);
        return instance;
    }

    private FlashlightButton(Activity activity) {
        super(activity, R.id.flashlight_button_id);
        setButtonBehavior();
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
    public void startIfActiveAndPermissionsGranted(int time) {

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
