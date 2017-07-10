package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton implements ControlPanelButton{
    private static PlayButton instance = null;
    private ImageButton playButton;
    private Activity activity;

    public static PlayButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PlayButton(activity);
        return instance;
    }

    private PlayButton(Activity activity) {
        this.activity = activity;
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        playButton = (ImageButton) getActivity().findViewById(R.id.playButtonId);
    }

    private void setButtonBehavior() {
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    playButton.setImageResource(R.drawable.play_white);
                else
                    playButton.setImageResource(R.drawable.play_purple);
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makeStopButtonNotClicked();
            }
        });
    }

    private Activity getActivity() {
        return activity;
    }

    public boolean isActive() {
        return playButton.isActivated();
    }

    @Override
    public void callOnClick() {
        playButton.callOnClick();
    }
}
