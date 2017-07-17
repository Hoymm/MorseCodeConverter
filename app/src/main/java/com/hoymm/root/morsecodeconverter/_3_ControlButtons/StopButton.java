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
                if(v.isActivated()) {
                    makeButtonActiveIfNotYet();
                    PlayButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                    PauseButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                }
                else
                    deactivateIfNotYetInactive();
            }
        });
    }

    @Override
    public void makeButtonActiveIfNotYet() {
        super.makeButtonActiveIfNotYet();
        setButtonImageToDeactivated();
    }

    private void setButtonImageToDeactivated() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setImageResource(R.drawable.stop_white);
            }
        });
    }

    @Override
    public void deactivateIfNotYetInactive() {
        super.deactivateIfNotYetInactive();
        setButtonImageActivated();
    }

    private void setButtonImageActivated() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setImageResource(R.drawable.stop_purple);
            }
        });
    }

    public static void setNull() {
        instance = null;
    }
}
