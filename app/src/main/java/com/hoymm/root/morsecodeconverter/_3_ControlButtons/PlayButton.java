package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class PlayButton extends ButtonsTemplate {
    private static PlayButton instance = null;

    private final int ONE_TIME_UNIT = 50;
    private Thread broadcastMorseCode;

    public static PlayButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new PlayButton(activity);
        return instance;
    }

    private PlayButton(Activity activity) {
        super(activity, R.id.playButtonId);
        initObjects();
        setButtonBehavior();
    }

    private void initObjects() {
        broadcastMorseCode = new Thread(new Runnable() {
            @Override
            public void run() {
                broadcastMorse();
            }
        });
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (atLeastOneFooterButtonActivated())
                    changeActiveStatePlayAndStopButtonsAndIconThenRunBroadcastThread(v);
                else
                    showMessageToTheUserToActivateAtLeastBroadcastOneMode();
            }
        });
    }

    private void changeActiveStatePlayAndStopButtonsAndIconThenRunBroadcastThread(View playButton) {
        if (isBroadcastThreadDead()) {
            playButton.setActivated(!playButton.isActivated());
            if (playButton.isActivated()) {
                deactivateStopButton();
                changeButtonImageToActivatedAndRunBroadcastThread();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makePauseButtonNotClicked();
                PlayPauseStopButtons.initAndGetInstance(getActivity()).makeStopButtonNotClicked();
            }
            else {
                activateStopButton();
                changeButtonImageToDeactivated();
            }

        }
    }

    private boolean isBroadcastThreadDead() {
        return !broadcastMorseCode.isAlive();
    }

    private void changeButtonImageToActivatedAndRunBroadcastThread() {
        button.setImageResource(R.drawable.play_white);
        broadcastMorseCode.start();
    }

    private boolean atLeastOneFooterButtonActivated() {
        return
                VibrationButton.initializateAndGetInstance(getActivity()).isActive() ||
                SoundButton.initializateAndGetInstance(getActivity()).isActive() ||
                FlashlightButton.initializateAndGetInstance(getActivity()).isActive() ||
                ScreenButton.initializateAndGetInstance(getActivity()).isActive();

    }

    private void broadcastMorse() {
        while (ConvertMorseToSignals.initAndGetInstance(getActivity()).isThereStillTextToBroadcast()
                && atLeastOneFooterButtonActivated()
                ) {
            int time = calculateCurrentPlayTime();

            if (isAGap(time))
                time = Math.abs(time);
            else
                if (!playSignalsSuccessfully(time))
                    break;
            pushToSleep(time + getOneUnitMultipliedTime());
            ConvertMorseToSignals.initAndGetInstance(getActivity()).removeNextCharFromBroadcast();
        }
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

    private boolean playSignalsSuccessfully(int time) {
        Log.i("Morse Signal", String.valueOf(time));
        boolean allPermissionsGranted = true;

        if (VibrationButton.initializateAndGetInstance(getActivity()).isActive())
            playVibration(time);

        if (SoundButton.initializateAndGetInstance(getActivity()).isActive())
            if (SoundButton.initializateAndGetInstance(getActivity()).isPermissionGranted())
                playSound(time);
            else
                allPermissionsGranted = false;

        if (FlashlightButton.initializateAndGetInstance(getActivity()).isActive())
            if (FlashlightButton.initializateAndGetInstance(getActivity()).isPermissionGranted())
                playFlashlight(time);
            else
                allPermissionsGranted = false;


        if (ScreenButton.initializateAndGetInstance(getActivity()).isActive())
            if (ScreenButton.initializateAndGetInstance(getActivity()).isPermissionGranted())
                playScreen(time);
            else
                allPermissionsGranted = false;

        return allPermissionsGranted;
    }

    private void playVibration(int time) {
        VibrationButton.initializateAndGetInstance(getActivity()).startIfActiveAndPermissionsGranted(time);
    }

    private void playSound(int time) {
    }

    private void playFlashlight(int time) {
    }

    private void playScreen(int time) {
    }

    private void pushToSleep(int ms) {
        try {
            Log.i("Morse Sleep", String.valueOf(ms));
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private int getOneUnitMultipliedTime() {
        return ONE_TIME_UNIT;
    }

    private void changeButtonImageToDeactivated() {
        button.setImageResource(R.drawable.play_purple);
    }

    private void deactivateStopButton() {
        if(StopButton.initAndGetInstance(getActivity()).isActive())
            StopButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private void activateStopButton() {
        if(!StopButton.initAndGetInstance(getActivity()).isActive())
            StopButton.initAndGetInstance(getActivity()).callOnClick();
    }

    private void showMessageToTheUserToActivateAtLeastBroadcastOneMode() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),
                        R.string.please_activate_at_least_one_broadcast_mode, Toast.LENGTH_SHORT).show();
            }
        });}
}
