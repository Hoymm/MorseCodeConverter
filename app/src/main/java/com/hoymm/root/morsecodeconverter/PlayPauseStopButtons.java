package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v4.content.ContextCompat;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

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
            unclickPauseButtonIfActive();
    }

    private void unclickPauseButtonIfActive() {
        if(pauseButton.isActivated())
            pauseButton.callOnClick();
    }

    private void makeStopButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unclickStopButtonIfActive();
    }

    private void unclickStopButtonIfActive() {
        if(stopButton.isActivated())
            stopButton.callOnClick();
    }

    private void setOnClickBehaviorForPause() {
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    pauseButton.setImageResource(R.drawable.pause_white);
                else
                    pauseButton.setImageResource(R.drawable.pause_purple);
                makePlayButtonNotClicked();
                makeStopButtonNotClicked();
            }
        });
    }

    private void makePlayButtonNotClicked() {
        if(twoOrMoreButtonsActivated())
            unclickPlayButtonIfActive();
    }

    private void unclickPlayButtonIfActive() {
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
