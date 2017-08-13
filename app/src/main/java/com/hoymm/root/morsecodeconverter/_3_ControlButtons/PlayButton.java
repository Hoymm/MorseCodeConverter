package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.ConvertingMorseTextProgram;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FooterButtons;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton extends ButtonsTemplate {
    private static PlayButton instance = null;
    private BroadcastMorseSignalsThread broadcastMorseSignalsThread;
    private static Toast plaseActivateBroadcastMode, pleaseInsertText;

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
                if (TextBoxes.initAndGetUpperBox(getActivity()).getText().toString().equals(""))
                    showMessageToTheUserToInsertText();
                else if (!FooterButtons.atLeastOneFooterButtonActive(getActivity()))
                    showMessageToTheUserToActivateAtLeastBroadcastOneMode();
                else
                    changeActiveStatesThenRunBroadcastThread(v);
                TextBoxes.setProperTextColor(getActivity());
            }
        });
    }

    private void showMessageToTheUserToInsertText() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (pleaseInsertText != null)
                    pleaseInsertText.cancel();
                pleaseInsertText =
                        Toast.makeText(getActivity(), R.string.please_insert_text, Toast.LENGTH_SHORT);
                pleaseInsertText.show();

            }
        });
    }

    private void changeActiveStatesThenRunBroadcastThread(View button) {
        if (!broadcastMorseSignalsThread.isThreadAlive()) {
            if (!button.isActivated()) {
                setUpperBoxSelectable(false);
                setTextBoxesScrollable();
                makeButtonActiveIfNotYet();
                runMorseBroadcastThread();
                ConvertingMorseTextProgram.initAndGetInstance(getActivity()).disableConversion();
                PauseButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
                StopButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
            }
        }
    }

    @Override
    public void deactivateIfNotYetInactive() {
        super.deactivateIfNotYetInactive();
        broadcastMorseSignalsThread.onPause();
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
                if (plaseActivateBroadcastMode != null)
                    plaseActivateBroadcastMode.cancel();
                plaseActivateBroadcastMode =
                        Toast.makeText(getActivity(), R.string.please_activate_at_least_one_broadcast_mode, Toast.LENGTH_SHORT);
                plaseActivateBroadcastMode.show();

            }
        });
    }


    public void decideSetUpperBoxSelectableDueToControlButtonActive() {
        setUpperBoxSelectable(StopButton.initAndGetInstance(getActivity()).isActive());
    }

    public static void setNull() {
        instance = null;
    }
}
