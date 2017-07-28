package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class SoundButton extends ButtonsTemplate implements FooterButtonsInterface, Singleton {
    private static SoundButton instance;

    public static SoundButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SoundButton(activity);
        return instance;
    }

    private SoundButton(Activity activity) {
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
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
    }
}
