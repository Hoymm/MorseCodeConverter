package com.hoymm.root.morsecodeconverter._2_ApplicationBody.ControlPanelButtons;

import android.app.Activity;
import android.view.View;
import android.widget.ImageButton;

import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_ApplicationBody.PlayPauseStopButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton extends ControlButtonsBehavior {
    private static PlayButton instance = null;

    public static PlayButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PlayButton(activity);
        return instance;
    }

    private PlayButton(Activity activity) {
        super(activity, R.id.playButtonId);
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setActivated(!v.isActivated());
                if(v.isActivated())
                    button.setImageResource(R.drawable.play_white);
                else
                    button.setImageResource(R.drawable.play_purple);
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makeStopButtonNotClicked();
            }
        });
    }
}
