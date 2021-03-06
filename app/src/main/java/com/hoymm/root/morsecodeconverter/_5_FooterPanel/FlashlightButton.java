package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.view.View;

import com.hoymm.root.morsecodeconverter.ButtonsTemplate;
import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 10.07.17.
 */

public class FlashlightButton extends ButtonsTemplate implements FooterButtonsInterface {
    private static FlashlightButton instance = null;

    public static FlashlightButton initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightButton(activity);
        return instance;
    }

    private FlashlightButton(Activity activity) {
        super(activity, R.id.flashlight_button_id);
        disableButtonIfNoFlashlight();
        setButtonBehavior();
    }

    private void disableButtonIfNoFlashlight() {
        if (!hasDeviceAFlashlight()) {
            button.setEnabled(false);
            button.setImageAlpha(100);
        }
    }

    private boolean hasDeviceAFlashlight() {
        return getActivity().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
    }

    private boolean isPermissionsGranted() {
        int flashlightPermissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA);
        return flashlightPermissionCheck == PackageManager.PERMISSION_GRANTED;
    }

    private void setButtonBehavior() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPermissionsGranted())
                    button.setActivated(!button.isActivated());
                else
                    FlashlightPermissions.initAndGetInstance(getActivity()).askForPermissions();
            }
        });
    }

    @Override
    public void start(int time) {
        TorchFeature.initAndGetInstance(getActivity()).turnOnFlashlight(time);
    }

    @Override
    public boolean isPermissionGranted() {
        return true;
    }

    public static void ifNotNullSetNullAndReleaseCamera(Activity activity) {
        setNull();
        releaseCameraIfTorchFeatureNotNull(activity);
    }

    public static void setNull() {
        instance = null;
    }

    private static void releaseCameraIfTorchFeatureNotNull(Activity activity){
        if (!TorchFeature.isNull())
            TorchFeature.initAndGetInstance(activity).setNullAndReleaseCamera();
    }
}
