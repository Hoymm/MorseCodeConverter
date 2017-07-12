package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._3_ControlButtons.ControlButtonsInterface;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public abstract class ButtonsTemplate implements ControlButtonsInterface {
    private Activity activity;
    protected ImageButton button;

    public ButtonsTemplate(Activity activity, int buttonResourceId) {
        this.activity = activity;
        button = (ImageButton) getActivity().findViewById(buttonResourceId);
    }

    public boolean isActive() {
        return button.isActivated();
    }

    protected Activity getActivity() {
        return activity;
    }

    public void callOnClick() {
        button.callOnClick();
    }

    @Override
    public void deactivateIfNotYetInactive() {
        if (button.isActivated())
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setActivated(false);
                }
            });
    }

    @Override
    public void activateIfNotYetActive() {
        if (!button.isActivated())
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setActivated(true);
                }
            });
    }
}
