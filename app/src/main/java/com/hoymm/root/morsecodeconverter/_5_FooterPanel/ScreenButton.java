package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.PlayButton;
import com.hoymm.root.morsecodeconverter._3_ControlButtons.StopButton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class ScreenButton extends ButtonsTemplate implements FooterButtonsInterface {
    private static ScreenButton instance;
    private static View foregroundView;

    public static ScreenButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new ScreenButton(activity);
        return instance;
    }

    private ScreenButton(Activity activity) {
        super(activity, R.id.screen_button_id);
        foregroundView = getActivity().findViewById(R.id.screen_mode_foreground_layout_id);
        setButtonBehavior();
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setActivated(!button.isActivated());
                isButtonWasDisabledMakeViewTransparent();
            }
        });
    }

    private void isButtonWasDisabledMakeViewTransparent() {
        if (!isActive())
            makeForegroundFullyTransparent();
    }

    @Override
    public void start(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                makeForegroundBright();
                sleepAThread(time);
                makeForegoundDarkIfScreenButtonActiveAndPlayButtonActive();
            }
        }).start();
    }

    private void sleepAThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void makeForegoundDarkIfScreenButtonActiveAndPlayButtonActive(){
        if (isActive() && PlayButton.initAndGetInstance(getActivity()).isActive())
            makeForegoundDark();
    }

    private void makeForegoundDark(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foregroundView.setBackground(ContextCompat.getDrawable(getActivity(), R.color.screenModeGapForeground));
            }
        });
    }

    private void makeForegroundBright(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foregroundView.setBackground(ContextCompat.getDrawable(getActivity(), R.color.screenModeSignalForeground));
            }
        });
    }

    public void makeForegroundFullyTransparent(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                foregroundView.setBackground(ContextCompat.getDrawable(getActivity(), R.color.transparent));
            }
        });
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    public static void setNull() {
        instance = null;
    }
}
