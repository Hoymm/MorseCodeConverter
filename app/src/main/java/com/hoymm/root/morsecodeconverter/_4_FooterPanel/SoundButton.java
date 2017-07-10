package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class SoundButton implements FooterButtons{
    private static SoundButton instance;
    private Activity activity;
    private ImageButton soundButton;

    public static SoundButton initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SoundButton(activity);
        return instance;
    }

    private SoundButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        soundButton = (ImageButton) getActivity().findViewById(R.id.sound_button_id);
    }

    @Override
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return false;
    }

    private void setButtonBehavior() {
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundButton.setActivated(!soundButton.isActivated());
            }
        });
    }

    private Activity getActivity() {
        return activity;
    }
}
