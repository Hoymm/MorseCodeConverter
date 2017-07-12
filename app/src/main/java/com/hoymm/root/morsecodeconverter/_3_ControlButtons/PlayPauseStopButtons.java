package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;

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

    void makePlayButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            PlayButton.initAndGetInstance(getActivity()).deactivateByOnClickIfNotYetDeactivated();
    }

    void makePauseButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            PauseButton.initAndGetInstance(getActivity()).deactivateByOnClickIfNotYetDeactivated();
    }

    void makeStopButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            StopButton.initAndGetInstance(getActivity()).deactivateByOnClickIfNotYetDeactivated();
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
