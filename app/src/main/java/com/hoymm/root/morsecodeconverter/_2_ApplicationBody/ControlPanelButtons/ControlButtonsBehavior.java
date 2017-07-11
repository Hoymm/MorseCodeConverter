package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.widget.ImageButton;

/**
 * File created by Damian Muca - Kaizen on 11.07.17.
 */

public abstract class ControlButtonsBehavior {
    private Activity activity;
    protected ImageButton button;

    ControlButtonsBehavior(Activity activity, int buttonResourceId) {
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
}
