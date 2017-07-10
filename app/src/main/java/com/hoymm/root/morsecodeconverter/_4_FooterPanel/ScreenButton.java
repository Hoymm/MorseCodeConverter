package com.hoymm.root.morsecodeconverter._4_FooterPanel;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class ScreenButton implements FooterButtons{
    private static ScreenButton instance;
    private Activity activity;
    private ImageButton screenButton;

    public static ScreenButton initializateAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ScreenButton(activity);
        return instance;
    }

    public ScreenButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        screenButton = (ImageButton) getActivity().findViewById(R.id.screen_button_id);
    }

    @Override
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return false;
    }

    private void setButtonBehavior() {
        screenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenButton.setActivated(!screenButton.isActivated());
            }
        });
    }

    private Activity getActivity() {
        return activity;
    }
}
