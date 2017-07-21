package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FooterButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton extends ButtonsTemplate{
    private static PlayButton instance = null;
    private BroadcastMorseSignalsThread broadcastMorseSignalsThread;

    public static PlayButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PlayButton(activity);
        return instance;
    }

    private PlayButton(Activity activity) {
        super(activity, R.id.playButtonId);
        broadcastMorseSignalsThread = new BroadcastMorseSignalsThread(getActivity());
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (FooterButtons.atLeastOneFooterButtonActive(getActivity()))
                    changeActiveStatesThenRunBroadcastThread(v);
                else
                    showMessageToTheUserToActivateAtLeastBroadcastOneMode();
            }
        });
    }

    private void changeActiveStatesThenRunBroadcastThread(View button) {
        if (broadcastMorseSignalsThread.isThreadDead()) {
            if (button.isActivated()) {
                deactivateIfNotYetInactive();
            }
            else {
                makeButtonActiveIfNotYet();
                runMorseBroadcastThread();
                PauseButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                StopButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
            }
        }
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
                button.setImageResource(R.drawable.play_purple);
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
                button.setImageResource(R.drawable.play_white);
            }
        });
    }

    private void runMorseBroadcastThread() {
        broadcastMorseSignalsThread = new BroadcastMorseSignalsThread(getActivity());
        broadcastMorseSignalsThread.startTheThread();
    }

    private void showMessageToTheUserToActivateAtLeastBroadcastOneMode() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        R.string.please_activate_at_least_one_broadcast_mode, Toast.LENGTH_SHORT).show();
            }
        });}

    public static void setNull() {
        instance = null;
    }
}
