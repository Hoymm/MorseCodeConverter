package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class StopButton extends ControlButtonsBehavior {
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
                if(v.isActivated())
                    button.setImageResource(R.drawable.stop_white);
                else
                    button.setImageResource(R.drawable.stop_purple);
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePlayButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
            }
        });
    }
}
