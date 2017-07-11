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
            unactivePlayButton();
    }

    void makePauseButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactivePauseButton();
    }

    void makeStopButtonNotClicked() {
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
