package com.hoymm.root.morsecodeconverter._3_ControlButtons;

import android.app.Activity;
import android.util.Log;

import com.hoymm.root.morsecodeconverter._1_TopBar.MorseToTextConversion.MorseCodeCipher;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FlashlightButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.FooterButtons;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.ScreenButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.SoundButton;
import com.hoymm.root.morsecodeconverter._5_FooterPanel.VibrationButton;

/**
 * File created by Damian Muca - Kaizen on 12.07.17.
 */

class BroadcastMorseSignals implements Runnable {
    private Thread thread;
    private Activity activity;
    private final int ONE_TIME_UNIT = 50;

    BroadcastMorseSignals(Activity activity) {
        this.activity = activity;
        thread = new Thread(this);
    }

    @Override
    public void run() {
        broadcastMorse();
        StopButton.initAndGetInstance(getActivity()).activateByOnClickIfNotYetActivated();
        joinThread();
    }

    private void broadcastMorse() {
        while (ConvertMorseToSignals.initAndGetInstance(getActivity()).isThereStillTextToBroadcast()
                && FooterButtons.atLeastOneFooterButtonActive(getActivity())
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

    private int getOneUnitMultipliedTime() {
        return ONE_TIME_UNIT;
    }

    private boolean isAGap(int time) {
        return time < 0;
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

    private Activity getActivity(){
        return activity;
    }

    boolean isThreadDead() {
        return !thread.isAlive();
    }

    public void startTheThread(){
        thread.start();
    }

    public void joinThread(){
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
