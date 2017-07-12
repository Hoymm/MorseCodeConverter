package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class StopButton extends ButtonsTemplate {
    private static StopButton instance = null;

    public static StopButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new StopButton(activity);
        return instance;
    }

    private StopButton(Activity activity) {
        super(activity, R.id.stopButtonId);
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated()) {
                    button.setImageResource(R.drawable.stop_white);
                    PlayPauseStopButtons.initAndGetInstance(getActivity()).makePlayButtonNotClicked();
                    PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
                }
                else
                    button.setImageResource(R.drawable.stop_purple);
            }
        });
    }
}
