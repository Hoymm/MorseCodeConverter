package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.media.AudioManager;
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
    private static SoundButton instance = null;
    private static SoundPool beepSound = null;
    private static int mSoundId = 0;
    private boolean ifBeepSoundStarted = false;


    public static SoundButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new SoundButton(activity);
        return instance;
    }

    private SoundButton(Activity activity) {
        super(activity, R.id.sound_button_id);
        setButtonBehavior();
        configureMediaPlayerSignalSound();
    }

    private void configureMediaPlayerSignalSound() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            beepSound = new SoundPool.Builder().setMaxStreams(1).build();
        else
            beepSound = new SoundPool(1, AudioManager.STREAM_MUSIC, 1);
        mSoundId = beepSound.load(getActivity(), R.raw.beep, 1);

        Log.i("BroadcastMorse", " sound play");
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
                playNewOrResumeBeepSound();
                sleepAThread(time);
                pauseBeepSound();
            }
        }).start();
    }

    private void playNewOrResumeBeepSound() {
        if (ifBeepSoundStarted)
            beepSound.resume(mSoundId);
        else
            startPlayingBeepSound();
    }

    private void startPlayingBeepSound() {
        if (beepSound.play(mSoundId, 1, 1, 1, -1, 1) != 0)
            ifBeepSoundStarted = true;
    }

    private void sleepAThread(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void pauseBeepSound() {
        beepSound.pause(mSoundId);
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
        beepSound.stop(mSoundId);
        beepSound.release();
        beepSound = null;
        mSoundId = 0;
        ifBeepSoundStarted = false;
    }
}
