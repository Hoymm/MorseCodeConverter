package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Build;
import android.util.Log;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class SoundButton extends ButtonsTemplate implements FooterButtonsInterface, Singleton {
    private static SoundButton instance;
    private MediaPlayer morseSound = null;


    public static SoundButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SoundButton(activity);
        return instance;
    }

    private SoundButton(Activity activity) {
        super(activity, R.id.sound_button_id);
        setButtonBehavior();
        configureMediaPlayerSignalSound(getActivity());
    }

    private void configureMediaPlayerSignalSound(Activity activity) {
        morseSound = MediaPlayer.create(activity, R.raw.beep);
        morseSound.setLooping(true);
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setActivated(!button.isActivated());
            }
        });
    }

    @Override
    public void start(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("BroadcastMorse", " sound play");
                morseSound.start();
                sleepAThread(time);
                morseSound.pause();
            }
        }).run();
    }

    private void sleepAThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
        morseSound.stop();
        morseSound.release();
        morseSound = null;
    }
}
