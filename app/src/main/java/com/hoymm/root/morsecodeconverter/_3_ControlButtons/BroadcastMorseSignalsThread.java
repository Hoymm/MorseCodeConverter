package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._1_TopBar.TopBarSpeedSpinner;
import com.hoymm.root.morsecodeconverter._2_TextBoxes.TextBoxes;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FooterButtons;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;

/**
 * File created by Damian Muca - Kaizen on 12.07.17.
 */

class BroadcastMorseSignalsThread implements Runnable {
    private Thread thread;
    private Activity activity;
    private boolean threadIsDead = true;
    private final int ONE_TIME_UNIT = 100;
    private ChangingTextColors changeTextColors;

    BroadcastMorseSignalsThread(Activity activity) {
        this.activity = activity;
        initObjects();
    }

    private void initObjects() {
        changeTextColors = new ChangingTextColors(getActivity());
        thread = new Thread(this);
    }

    @Override
    public void run() {
        threadIsDead = false;
        setBroadcastingToStartToTheBeggining();
        broadcastMorseAndChangeCharColors();
        StopButton.initAndGetInstance(getActivity()).makeButtonActiveIfNotYet();
        PlayButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
        threadIsDead = true;
    }

    private void setBroadcastingToStartToTheBeggining() {
        ConvertMorseToSignals.initAndGetInstance(getActivity()).setBroadcastTextIndexToStart();
    }

    private void broadcastMorseAndChangeCharColors() {
        while (ifNotEverythingBroadcastedYet() && FooterButtons.atLeastOneFooterButtonActive(getActivity())) {
            int time = calculateCurrentPlayTime();
            changeTextColors.colorCurrentlyTranslatingText();
            if (isAGap(time))
                time = Math.abs(time);
            else {
                boolean signalsHasBeenPlayed = broadcastSignal(time);
                if (!signalsHasBeenPlayed){
                    break;
                }
            }
            pushToSleep(time + getOneUnitMultipliedTime());
            ConvertMorseToSignals.initAndGetInstance(getActivity()).moveBroadcastingPositionForward();
        }
        makeTextBoxesTextWhiteAgain();
    }

    private void makeTextBoxesTextWhiteAgain() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                makeUpperBoxTextWhiteAndRestoreSelection();
                makeBottomBoxTextWhite();
            }
        });
    }

    private void makeBottomBoxTextWhite() {
        String bottomText = TextBoxes.initAndGetBottomBox(getActivity()).getText().toString();
        TextBoxes.initAndGetBottomBox(getActivity()).setText(bottomText);
    }

    private void makeUpperBoxTextWhiteAndRestoreSelection() {
        int selection = TextBoxes.initAndGetUpperBox(getActivity()).getSelectionStart();
        String upperText = TextBoxes.initAndGetUpperBox(getActivity()).getText().toString();
        TextBoxes.initAndGetUpperBox(getActivity()).setText(upperText);
        TextBoxes.initAndGetUpperBox(getActivity()).setSelection(selection);
    }

    private boolean ifNotEverythingBroadcastedYet() {
        return ConvertMorseToSignals.initAndGetInstance(getActivity()).isThereStillTextLeftToBroadcast();
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
        String charToBroadcast =
                ConvertMorseToSignals.initAndGetInstance(getActivity()).getMorseSignToBeBroadcasted();
        float spinerSpeedMultiplier =
                TopBarSpeedSpinner.initAndGetInstance(getActivity()).getLastSpeedFromSharedPreferences();
        switch (charToBroadcast){
            case "·":
                return (int) (getOneUnitMultipliedTime()/spinerSpeedMultiplier);

            case "−":
                return (int) (3*getOneUnitMultipliedTime()/spinerSpeedMultiplier);

            case MorseCodeCipher.SHORT_GAP:
                return (int) (-3*getOneUnitMultipliedTime()/spinerSpeedMultiplier);

            case MorseCodeCipher.MEDIUM_GAP:
                return (int) (-7*getOneUnitMultipliedTime()/spinerSpeedMultiplier);
        }

        return 0;
    }

    private int getOneUnitMultipliedTime() {
        return ONE_TIME_UNIT;
    }

    private boolean isAGap(int time) {
        return time < 0;
    }

    private boolean broadcastSignal(int time) {
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
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Activity getActivity(){
        return activity;
    }

    boolean isThreadDead() {
        return threadIsDead;
    }

    void startTheThread(){
            thread.start();
    }
}
