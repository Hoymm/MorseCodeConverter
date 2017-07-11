package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class FlashlightButtons extends ButtonsTemplate implements FooterButtonsInterface {
    private static FlashlightButtons instance;

    public static FlashlightButtons initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightButtons(activity);
        return instance;
    }

    private FlashlightButtons(Activity activity) {
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
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return false;
    }
}
