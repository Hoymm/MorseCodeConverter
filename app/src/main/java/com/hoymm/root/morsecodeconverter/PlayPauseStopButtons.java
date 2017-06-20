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
                    ((ImageButton)v).setImageResource(R.drawable.play_white);
                else
                    ((ImageButton)v).setImageResource(R.drawable.play_purple);

            }
        });
    }

    private void setOnClickBehaviorForPause() {
        pauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    ((ImageButton)v).setImageResource(R.drawable.pause_white);
                else
                    ((ImageButton)v).setImageResource(R.drawable.pause_purple);

            }
        });
    }

    private void setOnClickBehaviorForStop() {
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    ((ImageButton)v).setImageResource(R.drawable.stop_white);
                else
                    ((ImageButton)v).setImageResource(R.drawable.stop_purple);

            }
        });
    }

    private Activity getActivity(){
        return (Activity)context;
    }

}
