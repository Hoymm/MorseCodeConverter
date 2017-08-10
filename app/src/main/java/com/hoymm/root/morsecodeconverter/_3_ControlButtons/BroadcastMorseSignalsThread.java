package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.util.Log;

import com.hoymm.root.morsecodeconverter.MainActivity;
import com.hoymm.root.morsecodeconverter.MorseToTextConversionProg.MorseCodeCipher;
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
    private boolean threadIsAlive = false;
    private final int ONE_TIME_UNIT = 150;

    BroadcastMorseSignalsThread(Activity activity) {
        this.activity = activity;
        initObjects();
    }

    private void initObjects() {
        thread = new Thread(this);
    }

    @Override
    public void run() {
        broadcastMorseAndChangeCharColors();
        callOnclickStopOrPauseButtonIfPlayButtonIsActive();
        deactivatePlayButton();
        threadIsAlive = false;
    }

    private void deactivatePlayButton() {
        if(!MainActivity.destroyed)
            PlayButton.initAndGetInstance(getActivity()).deactivateIfNotYetInactive();
    }

    private void callOnclickStopOrPauseButtonIfPlayButtonIsActive() {
        if (!MainActivity.destroyed &&
                PlayButton.initAndGetInstance(getActivity()).isActive())
            activatePauseOrStopButton();
    }

    private void activatePauseOrStopButton() {
        if (isThereTextLeftToBroadcast())
            PauseButton.initAndGetInstance(getActivity()).callOnClick();
        else
            StopButton.initAndGetInstance(getActivity()).callOnClick();
    }

    static void setBroadcastingToStartFromTheBeggining(Activity activity) {
        ConvertMorseToSignals.initAndGetInstance(activity).setBroadcastTextIndexToStart();
    }

    private void broadcastMorseAndChangeCharColors() {
        while (!MainActivity.destroyed &&
                isThereTextLeftToBroadcast()
                && PlayButton.initAndGetInstance(getActivity()).isActive()
                && FooterButtons.atLeastOneFooterButtonActive(getActivity())) {
            ChangingTextColors.initAndGetInstance(getActivity()).refreshColors();

            if (!broadcastSignalOrGapSuccesfully())
                break;
            ConvertMorseToSignals.initAndGetInstance(getActivity()).moveBroadcastingPositionForwardIfPlayButtonActive();
        }
    }

    private boolean isThereTextLeftToBroadcast() {
        return ConvertMorseToSignals.initAndGetInstance(getActivity()).isThereStillTextLeftToBroadcast();
    }

    private boolean broadcastSignalOrGapSuccesfully() {
        int signalTime = calculateCurrentPlayTime();
        if (isAGap(signalTime)) {
            signalTime = Math.abs(signalTime);
            Log.i("GapTime", signalTime + "");
        }
        else {
            boolean signalsHasBeenPlayed = broadcastSignal(signalTime);
            if (!signalsHasBeenPlayed) {
                return false;
            }
        }
        pushToSleep(signalTime + getOneUnitMultiplerTime());
        return true;
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
        float spinerSpeedMultiplier = TopBarSpeedSpinner.initAndGetInstance(getActivity()).getLastSpeedFromSharedPreferences();
        switch (charToBroadcast){
            case "·":
                return (int) (getOneUnitMultiplerTime()/spinerSpeedMultiplier);

            case "−":
                return (int) (3* getOneUnitMultiplerTime()/spinerSpeedMultiplier);

            case MorseCodeCipher.SHORT_GAP:
                return (int) (-3* getOneUnitMultiplerTime()/spinerSpeedMultiplier);

            case MorseCodeCipher.MEDIUM_GAP:
                return (int) (-7* getOneUnitMultiplerTime()/spinerSpeedMultiplier);
        }

        return 0;
    }

    private boolean broadcastSignal(int time) {
        boolean allPermissionsGranted = true;

        if (VibrationButton.initAndGetInstance(getActivity()).isActive())
            playVibration(time);

        if (SoundButton.initAndGetInstance(getActivity()).isActive())
            playSound(time);

        if (FlashlightButton.initAndGetInstance(getActivity()).isActive())
            if (FlashlightButton.initAndGetInstance(getActivity()).isPermissionGranted())
                playFlashlight(time);
            else
                allPermissionsGranted = false;


        if (ScreenButton.initAndGetInstance(getActivity()).isActive())
            if (ScreenButton.initAndGetInstance(getActivity()).isPermissionGranted())
                playScreen(time);
            else
                allPermissionsGranted = false;

        return allPermissionsGranted;
    }

    static void makeTextBoxesTextWhiteAgain(final Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                makeUpperBoxTextWhiteAndRestoreSelection(activity);
                makeBottomBoxTextWhite(activity);
            }
        });
    }

    private static void makeBottomBoxTextWhite(Activity activity) {
        String bottomText = TextBoxes.initAndGetBottomBox(activity).getText().toString();
        TextBoxes.initAndGetBottomBox(activity).setText(bottomText);
    }

    private static void makeUpperBoxTextWhiteAndRestoreSelection(Activity activity) {
        int selection = TextBoxes.initAndGetUpperBox(activity).getSelectionStart();
        String upperText = TextBoxes.initAndGetUpperBox(activity).getText().toString();
        TextBoxes.initAndGetUpperBox(activity).setText(upperText);
        TextBoxes.initAndGetUpperBox(activity).setSelection(selection);
    }

    private int getOneUnitMultiplerTime() {
        return ONE_TIME_UNIT;
    }

    private boolean isAGap(int time) {
        return time < 0;
    }

    private void playVibration(int time) {
        VibrationButton.initAndGetInstance(getActivity()).start(time);
    }

    private void playSound(int time) {
        SoundButton.initAndGetInstance(getActivity()).start(time);
    }

    private void playFlashlight(int time) {
        FlashlightButton.initAndGetInstance(getActivity()).start(time);
    }

    private void playScreen(int time) {
        ScreenButton.initAndGetInstance(getActivity()).start(time);
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

    boolean isThreadAlive() {
        return threadIsAlive;
    }

    void startTheThread(){
        threadIsAlive = true;
        thread.start();
    }

    void onPause() {
        threadIsAlive = false;
    }
}
