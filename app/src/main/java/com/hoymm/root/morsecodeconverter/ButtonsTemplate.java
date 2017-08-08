package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.ControlButtonsInterface;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PauseButton;
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
    public void makeButtonActiveIfNotYet() {
        if (!button.isActivated())
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    button.setActivated(true);
                }
            });
    }


    protected void setUpperBoxSelectable(final boolean selectable) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextBoxes.initAndGetUpperBox(getActivity()).setTextIsSelectable(selectable);
                TextBoxes.initAndGetUpperBox(getActivity()).setFocusable(selectable);
            }
        });
    }

    protected void setTextBoxesScrollable() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TextBoxes.setUpperBoxScrollable(getActivity());
                TextBoxes.setBottomBoxScrollable(getActivity());
            }
        });
    }

    public void ifButtonInactiveThenCallOnclick(){
        if (!isActive())
            callOnClick();
    }
}
