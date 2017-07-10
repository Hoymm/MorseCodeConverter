package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PauseButton implements ControlPanelButton{
    private static PauseButton instance = null;
    private ImageButton pauseButton;
    private Activity activity;

    public static PauseButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PauseButton(activity);
        return instance;
    }

    private PauseButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        pauseButton = (ImageButton) getActivity().findViewById(R.id.pauseButtonId);
    }

    private void setButtonBehavior() {
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());

                if(v.isActivated()) {
                    onPauseActivatedAction();
                }
                else {
                    onPauseDeactivatedAction();
                }
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePlayButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makeStopButtonNotClicked();
            }
        });
    }

    private void onPauseDeactivatedAction() {
        pauseButton.setImageResource(R.drawable.pause_purple);
    }

    private void onPauseActivatedAction() {
        pauseButton.setImageResource(R.drawable.pause_white);
    }

    private Activity getActivity() {
        return activity;
    }

    public boolean isActive() {
        return pauseButton.isActivated();
    }

    @Override
    public void callOnClick() {
        pauseButton.callOnClick();
    }
}
