package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.ConvertingMorseTextProgram;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextArrowsSwap;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PauseButton extends ButtonsTemplate {
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
                if(!v.isActivated()){
                    setUpperBoxSelectable(false);
                    makeButtonActiveIfNotYet();
                    PlayButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                    StopButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                    TextBoxes.setProperTextColor(getActivity());
                    ScreenButton.initAndGetInstance(getActivity()).makeForegroundFullyTransparent();
                }
            }
        });
    }

    @Override
    public void deactivateIfNotYetInactive() {
        super.deactivateIfNotYetInactive();
        setButtonImageToDeactivated();
    }

    private void setButtonImageToDeactivated() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setImageResource(R.drawable.pause_purple);
            }
        });
    }

    @Override
    public void makeButtonActiveIfNotYet() {
        super.makeButtonActiveIfNotYet();
        setButtonImageActivated();
    }

    private void setButtonImageActivated() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                button.setImageResource(R.drawable.pause_white);
            }
        });
    }

    public static void setNull() {
        instance = null;
    }
}
