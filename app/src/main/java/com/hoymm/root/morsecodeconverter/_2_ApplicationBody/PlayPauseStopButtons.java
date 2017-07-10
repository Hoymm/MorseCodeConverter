package com.hoymm.root.morsecodeconverter._2_ApplicationBody;

import android.app.Activity;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.PauseButton;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 20.06.17.
 */

public class PlayPauseStopButtons {
    private static PlayPauseStopButtons instance = null;
    private static Activity activity;

    public static PlayPauseStopButtons initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PlayPauseStopButtons(activity);
        return instance;
    }

    private PlayPauseStopButtons(Activity activity) {
        PlayPauseStopButtons.activity = activity;
    }

    public void makePlayButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactivePlayButton();
    }

    public void makePauseButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactivePauseButton();
    }

    public void makeStopButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactiveStopButton();
    }

    private void unactivePlayButton() {
        if(PlayButton.initAndGetInstance(getActivity()).isActive())
            PlayButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private void unactivePauseButton() {
        if(PauseButton.initAndGetInstance(getActivity()).isActive())
            PauseButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private void unactiveStopButton() {
        if(StopButton.initAndGetInstance(getActivity()).isActive())
            StopButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private static boolean twoOrMoreButtonsActivated() {
        int howManyButtonsActive = 0;

        if (PlayButton.initAndGetInstance(getActivity()).isActive())
            howManyButtonsActive++;

        if (PauseButton.initAndGetInstance(getActivity()).isActive())
            howManyButtonsActive++;

        if(StopButton.initAndGetInstance(getActivity()).isActive())
            howManyButtonsActive++;

        return howManyButtonsActive >= 2;
    }

    private static Activity getActivity(){
        return activity;
    }

}
