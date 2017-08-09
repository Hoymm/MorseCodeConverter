package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;

import com.hoymm.root.morsecodeconverter.R;

/**
 * File created by Damian Muca - Kaizen on 03.08.17.
 */

public class FlashlightPermissions {
    private static FlashlightPermissions instance = null;
    public static final int FLASHLIGHT_BUTTON_PERMISSIONS = 1;
    private Activity activity;

    public static FlashlightPermissions initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new FlashlightPermissions(activity);
        return instance;
    }

    private FlashlightPermissions(Activity activity) {
        this.activity = activity;
    }

    void askForPermissions() {
        if (shouldWeShowAnExplanation())
            showDialogToExplainPermissionNeed();
        else
            requestCameraPermission();
    }

    private boolean shouldWeShowAnExplanation() {
        return ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.CAMERA);
    }

    private void showDialogToExplainPermissionNeed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.permission_is_required);
        builder.setMessage(R.string.camera_use_explanation_text);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                requestCameraPermission();
            }
        });
        builder.show();
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA}, FLASHLIGHT_BUTTON_PERMISSIONS);
    }

    public static void callOnClickIfPermissionGranted(@NonNull int[] grantResults, final Activity activity) {
        if (ifPermissionHasBeenGranted(grantResults)) {
            callOnClickButtonAfter20ms(activity);
        }
    }

    private static boolean ifPermissionHasBeenGranted(int[] grantResults) {
        return grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
    }

    private static void callOnClickButtonAfter20ms(final Activity activity) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                sleepThreadWithTryCatch(20);
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        FlashlightButton.initAndGetInstance(activity).callOnClick();
                    }
                });
            }
        }).start();
    }

    private static void sleepThreadWithTryCatch(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Activity getActivity(){
        return activity;
    }

    public static void setNull() {
        instance = null;
    }
}
