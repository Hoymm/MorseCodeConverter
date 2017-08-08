package com.hoymm.root.morsecodeconverter._5_FooterPanel;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PermissionInfo;
import android.hardware.Camera;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Build;
import android.util.Log;

import com.hoymm.root.morsecodeconverter.Singleton;

/**
 * File created by Damian Muca - Kaizen on 03.08.17.
 */

class TorchFeature implements Singleton {
    private Activity activity;
    private static TorchFeature instance = null;

    // new way >=21API
    private CameraManager newCamera;
    private String cameraId = null;

    // old way <21API
    private Camera oldCamera = null;
    private Camera.Parameters cameraParameters;


    public static TorchFeature initAndGetInstance(Activity activity){
        if (instance == null)
            instance = new TorchFeature(activity);
        return instance;
    }

    private TorchFeature(Activity activity) {
        this.activity = activity;
        initializateCameraObjects();
    }

    private void initializateCameraObjects() {/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            initializateNewWayCamObjects();
        else*/
            initializateOldWayCamObjects();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void initializateNewWayCamObjects() {
        newCamera = (CameraManager) getActivity().getSystemService(Context.CAMERA_SERVICE);
        try {
            cameraId = newCamera.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private void initializateOldWayCamObjects() {
        oldCamera = Camera.open();
        oldCamera.startPreview();
        cameraParameters = oldCamera.getParameters();
    }

    public void turnOnFlashlight(int time) {/*
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            useFlashlightNewWay(time);
        else*/
            useFlashlightOldWay(time);
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    void useFlashlightNewWay(final int time) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("Flashlight", "turn ON");
                newWayTurnOnTorch();
                Log.i("Flashlight", "sleep for " + time);
                sleepThreadWithTryCatch(time);
                Log.i("Flashlight", "turn OFF");
                newWayTurnOffTorch();
            }
        }).start();
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void newWayTurnOnTorch() {
        try {

            newCamera.setTorchMode(cameraId, true);   //Turn ON
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    private static void sleepThreadWithTryCatch(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @android.support.annotation.RequiresApi(api = Build.VERSION_CODES.M)
    private void newWayTurnOffTorch() {
        try {
            newCamera.setTorchMode(cameraId, false);
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }
    }

    void useFlashlightOldWay(final int time) {
        Log.i("UseFlashlight", " old way");
        new Thread(new Runnable() {
            @Override
            public void run() {
                turnOnOldWay();
                sleepThreadWithTryCatch(time);
                turnOffOldWay();
            }
        }).start();
    }

    private void turnOnOldWay() {
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        oldCamera.setParameters(cameraParameters);
    }

    private void turnOffOldWay() {
        cameraParameters.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        oldCamera.setParameters(cameraParameters);
    }

    public void setNullAndReleaseCamera() {
        releaseCameraUsages();
        setNull();
    }

    @Override
    public void setNull() {
        instance = null;
    }

    private void releaseCameraUsages() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // TODO release (camera2) objects ???
        }
        else
            releaseOldWay();
    }

    private void releaseOldWay() {
        if (oldCamera != null) {
            oldCamera.stopPreview();
            oldCamera.release();
            oldCamera = null;
        }
    }

    private Activity getActivity(){
        return activity;
    }

    public static boolean isNull() {
        return instance == null;
    }
}
