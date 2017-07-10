package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

/**
 * File created by Damian Muca - Kaizen on 20.06.17.
 */

public class PlayPauseStopButtons {
    private ImageButton playButton, stopButton, pauseButton;
    private Context context;

    public PlayPauseStopButtons(Context context) {
        this.context = context;
        initObjects();
        setOnTouchBehavior();
    }

    private void initObjects() {
        playButton = (ImageButton) getActivity().findViewById(R.id.playButtonId);
        stopButton = (ImageButton) getActivity().findViewById(R.id.stopButtonId);
        pauseButton = (ImageButton) getActivity().findViewById(R.id.pauseButtonId);
    }

    private void setOnTouchBehavior() {
        setOnClickBehaviorForPlay();
        setOnClickBehaviorForPause();
        setOnClickBehaviorForStop();
    }

    private void setOnClickBehaviorForPlay() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    playButton.setImageResource(R.drawable.play_white);
                else
                    playButton.setImageResource(R.drawable.play_purple);
                makePauseButtonNotClicked();
                makeStopButtonNotClicked();
            }
        });
    }

    private void makePauseButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactivePauseButton();
    }

    private void unactivePauseButton() {
        if(pauseButton.isActivated())
            pauseButton.callOnClick();
    }

    private void makeStopButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactiveStopButton();
    }

    private void unactiveStopButton() {
        if(stopButton.isActivated())
            stopButton.callOnClick();
    }

    private void setOnClickBehaviorForPause() {
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());

                if(v.isActivated()) {
                    onPauseActivatedAction();
                }
                else {
                    onPauseDeactivatedAction();
                }

                makePlayButtonNotClicked();
                makeStopButtonNotClicked();
            }
        });
    }

    private void onPauseDeactivatedAction() {
        pauseButton.setImageResource(R.drawable.pause_purple);
    }

    private void onPauseActivatedAction() {
        pauseButton.setImageResource(R.drawable.pause_white);
    }

    private void makePlayButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unactivePlayButton();
    }

    private void unactivePlayButton() {
        if(playButton.isActivated())
            playButton.callOnClick();
    }

    private void setOnClickBehaviorForStop() {
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    stopButton.setImageResource(R.drawable.stop_white);
                else
                    stopButton.setImageResource(R.drawable.stop_purple);
                makePlayButtonNotClicked();
                makePauseButtonNotClicked();
            }
        });
    }

    private boolean twoOrMoreButtonsActivated() {
        int howManyButtonsActivated = 0;

        if (playButton.isActivated())
            howManyButtonsActivated++;

        if (pauseButton.isActivated())
            howManyButtonsActivated++;

        if(stopButton.isActivated())
            howManyButtonsActivated++;

        return howManyButtonsActivated >= 2;
    }

    private Activity getActivity(){
        return (Activity)context;
    }

}
