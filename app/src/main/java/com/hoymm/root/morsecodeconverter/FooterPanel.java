package com.hoymm.root.morsecodeconverter;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;

/**
 * File created by Damian Muca - Kaizen on 21.06.17.
 */

public class FooterPanel {
    private Context context;
    private ImageButton vibrateButton, soundButton, flashlightButton, screenButton;

    public FooterPanel(Context context) {
        this.context = context;
        initObjects();
        setButttonsBehavior();
    }

    private void initObjects() {
        initXMLObjects();
    }

    private void initXMLObjects() {
        vibrateButton = (ImageButton) getActivity().findViewById(R.id.vibration_button_id);
        soundButton = (ImageButton) getActivity().findViewById(R.id.sound_button_id);
        flashlightButton = (ImageButton) getActivity().findViewById(R.id.flashlight_button_id);
        screenButton = (ImageButton) getActivity().findViewById(R.id.screen_button_id);
    }

    private void setButttonsBehavior() {
        toVibrate();
        toSound();
        toFlashlight();
        toScreen();
    }

    private void toVibrate() {
        vibrateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vibrateButton.setActivated(!vibrateButton.isActivated());
            }
        });
    }

    private void toSound() {
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundButton.setActivated(!soundButton.isActivated());
            }
        });

    }

    private void toFlashlight() {
        flashlightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flashlightButton.setActivated(!flashlightButton.isActivated());
            }
        });
    }

    private void toScreen() {
        screenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                screenButton.setActivated(!screenButton.isActivated());
            }
        });
    }

    private Activity getActivity(){
        return ((Activity)context);
    }
}
