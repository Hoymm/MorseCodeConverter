package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PauseButton extends ControlButtonsBehavior{
    private static PauseButton instance = null;

    public static PauseButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PauseButton(activity);
        return instance;
    }

    private PauseButton(Activity activity) {
        super(activity, R.id.pauseButtonId);
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
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
        button.setImageResource(R.drawable.pause_purple);
    }

    private void onPauseActivatedAction() {
        button.setImageResource(R.drawable.pause_white);
    }
}
