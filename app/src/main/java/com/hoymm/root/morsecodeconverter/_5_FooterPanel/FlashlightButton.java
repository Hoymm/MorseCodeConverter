package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;
import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class FlashlightButton extends ButtonsTemplate implements FooterButtonsInterface, Singleton {
    private static FlashlightButton instance;
    private static Toast deviceDoesNotSupportFlash;

    public static FlashlightButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightButton(activity);
        return instance;
    }

    private FlashlightButton(Activity activity) {
        super(activity, R.id.flashlight_button_id);
        ifDeviceHasFlashThenSetButtonBehaviorOtherwiseDisableIt();
    }

    private void ifDeviceHasFlashThenSetButtonBehaviorOtherwiseDisableIt() {
        if (hasDeviceAFlashlight())
            setButtonBehavior();
        else
            button.setEnabled(false);
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "I CANNOT CLICK", Toast.LENGTH_SHORT).show();
                if (hasDeviceAFlashlight())
                    button.setActivated(!button.isActivated());
                else
                    showToastThatYourDeviceDoesNotSupportTorch();
            }
        });
    }

    private boolean hasDeviceAFlashlight() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private void showToastThatYourDeviceDoesNotSupportTorch() {
        if (deviceDoesNotSupportFlash != null)
            deviceDoesNotSupportFlash.cancel();
        deviceDoesNotSupportFlash =
                Toast.makeText(getActivity(), "Sorry, your device does not support that feature.", Toast.LENGTH_SHORT);
        deviceDoesNotSupportFlash.show();
    }

    @Override
    public void start(int time) {

    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    @Override
    public void setNull() {
        instance = null;
    }
}
