package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class StopButton implements ControlPanelButton{
    private static StopButton instance = null;
    private ImageButton stopButton;
    private Activity activity;

    public static StopButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new StopButton(activity);
        return instance;
    }

    private StopButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        stopButton = (ImageButton) getActivity().findViewById(R.id.stopButtonId);
    }

    private void setButtonBehavior() {
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    stopButton.setImageResource(R.drawable.stop_white);
                else
                    stopButton.setImageResource(R.drawable.stop_purple);
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePlayButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
            }
        });
    }

    private Activity getActivity() {
        return activity;
    }

    public boolean isActive() {
        return stopButton.isActivated();
    }

    @Override
    public void callOnClick() {
        stopButton.callOnClick();
    }
}
