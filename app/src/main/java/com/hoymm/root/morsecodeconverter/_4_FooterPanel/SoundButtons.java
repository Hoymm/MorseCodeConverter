package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class SoundButtons extends ButtonsTemplate implements FooterButtonsInterface {
    private static SoundButtons instance;

    public static SoundButtons initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SoundButtons(activity);
        return instance;
    }

    private SoundButtons(Activity activity) {
        super(activity, R.id.sound_button_id);
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
