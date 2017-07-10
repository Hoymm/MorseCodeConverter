package com.hoymm.root.morsecodeconverter.FooterPanel;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 21.06.17.
 */

public class FooterPanelBehaviorAndFeatures {
    private Context context;
    private ImageButton vibrateButton, soundButton, flashlightButton, screenButton;

    public VibrationFeature vibrationFeature;
    public SoundFeature soundFeature;
    public FlashlightFeature flashlightFeature;
    public ScreenFeature screenFeature;

    public FooterPanelBehaviorAndFeatures(Context context) {
        this.context = context;
        initObjects();
        setButttonsBehavior();
    }

    private void initObjects() {
        initFooterButtonsFeatureObjects();
        initXMLObjects();
    }

    private void initFooterButtonsFeatureObjects() {
        vibrationFeature = new VibrationFeature(getActivity());
        soundFeature = new SoundFeature();
        flashlightFeature = new FlashlightFeature();
        screenFeature = new ScreenFeature();
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
                if (vibrateButton.isActivated())
                    Toast.makeText(getActivity(), "Vibration Activated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(getActivity(), "Vibration Deactivated", Toast.LENGTH_SHORT).show();
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
