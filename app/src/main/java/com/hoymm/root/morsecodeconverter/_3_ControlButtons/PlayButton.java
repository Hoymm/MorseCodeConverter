package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.util.Log;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton extends ButtonsTemplate {
    private static PlayButton instance = null;
    final int ONE_TIME_UNIT = 50;

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
                if (v.isActivated())
                    playButtonActivated();
                else
                    playButtonDeactivated();

                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makeStopButtonNotClicked();
            }
        });
    }

    private void playButtonActivated() {
        button.setImageResource(R.drawable.play_white);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (ConvertMorseToSignals.initAndGetInstance(getActivity()).isThereStillTextToBroadcast()) {
                    int time = calculateCurrentPlayTime();

                    Log.i("Morse", String.valueOf(time));
                    if (isAGap(time)){
                        time = Math.abs(time);
                    }
                    else {
                        playVibrationIfActive(time);
                        playSoundIfActive(time);
                        playFlashlightIfActive(time);
                        playScreenIfActive(time);
                    }

                    ConvertMorseToSignals.initAndGetInstance(getActivity()).removeNextCharFromBroadcast();
                    pushToSleep(time+getOneUnitMultipliedTime());
                }
            }
        }).start();
    }

    private boolean isAGap(int time) {
        return time < 0;
    }

    private int calculateCurrentPlayTime() {
        /*
        International Morse code is composed of five elements:[1]
        short mark, dot or "dit" (▄▄▄▄): "dot duration" is one time unit long
        longer mark, dash or "dah" (▄▄▄▄▄▄): three time units long
        inter-element gap between the dots and dashes within a character: one dot duration or one unit long
        short gap (between letters): three time units long
        medium gap (between words): seven time units long
         */
        String charToBroadcast = ConvertMorseToSignals.initAndGetInstance(getActivity()).getNextMorseSignToBroadcast();
        switch (charToBroadcast){
            case "·":
                return getOneUnitMultipliedTime();

            case "−":
                return 3*getOneUnitMultipliedTime();

            case MorseCodeCipher.SHORT_GAP:
                return -3*getOneUnitMultipliedTime();

            case MorseCodeCipher.MEDIUM_GAP:
                return -7*getOneUnitMultipliedTime();
        }

        return 0;
    }

    private void playVibrationIfActive(int time) {
        VibrationButton.initializateAndGetInstance(getActivity()).start(time);
    }

    private void playSoundIfActive(int time) {
    }

    private void playFlashlightIfActive(int time) {
    }

    private void playScreenIfActive(int time) {
    }

    private void pushToSleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getOneUnitMultipliedTime() {
        return ONE_TIME_UNIT;
    }

    private void playButtonDeactivated() {
        button.setImageResource(R.drawable.play_purple);
    }
}
